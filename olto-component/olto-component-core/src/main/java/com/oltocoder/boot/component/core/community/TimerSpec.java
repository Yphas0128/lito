package com.oltocoder.boot.component.core.community;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronConstraintsFactory;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.expression.FieldExpressionFactory;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.reactivestreams.Subscription;
import org.springframework.util.Assert;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@Setter
public class TimerSpec implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 触发方式
     */
    private Trigger trigger;

    /**
     * cron 表达式
     */
    private String cron;

    /**
     *执行的时间.为空则表示每天,触发方式为[week]则为1-7,触发方式为[month]时则为1-31
     */
    private Set<Integer> when;

    /**
     * 执行模式,一次还是周期执行
     */
    private ExecuteMod mod;

    /**
     * 执行模式为[period]时不能为空
     */
    private Period period;

    /**
     * 执行模式为[once]时不能为空
     */
    private Once once;

    public Flux<Long> flux() {
        return flux(Schedulers.parallel());
    }

    private Flux<Long> flux(Scheduler scheduler) {
        return new TimerFlux(nextDurationBuilder(), scheduler);
    }

    private Function<ZonedDateTime, Duration> nextDurationBuilder() {
        return nextDurationBuilder(ZonedDateTime.now());
    }

    private Function<ZonedDateTime, Duration> nextDurationBuilder(ZonedDateTime baseTime) {
        Iterator<ZonedDateTime> iterator =  iterable().iterator(baseTime);
        return (time) -> {
            Duration duration;
            do {
                duration = Duration.between(time, time = iterator.next());
            }
            while (duration.toMillis() < 0);
            return duration;
        };
    }

    private TimerIterable iterable() {
        if((trigger== Trigger.cron || trigger == null) && cron != null)
            return cronIterable();
        return mod == ExecuteMod.period ? periodIterable() : onceIterable();
    }

    /**
     * period
     * @return
     */
    private TimerIterable periodIterable() {
        Assert.notNull(period, "period can not be null");
        Predicate<LocalDateTime> filter = createTimeFilter();

        Duration duration = Duration.of(period.every, period.unit.temporal);

        return baseTime -> new Iterator<ZonedDateTime>() {

            ZonedDateTime current = baseTime;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public ZonedDateTime next() {
                ZonedDateTime dateTime = current;
                int max = MAX_IT_TIMES;

                do {
                    dateTime = dateTime.plus(duration);
                    if (filter.test(dateTime.toLocalDateTime())) {
                        break;
                    }
                    max--;
                } while (max > 0);
                return current = dateTime;
            }
        };
    }

    private TimerIterable onceIterable() {
        Assert.notNull(once, "once can not be null");
        Predicate<LocalDateTime> filter = createTimeFilter();
        LocalTime onceTime = once.localTime();

        return baseTime -> new Iterator<ZonedDateTime>() {

            ZonedDateTime current = baseTime;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public ZonedDateTime next() {
                ZonedDateTime dateTime = current;
                int max = MAX_IT_TIMES;
                if(!dateTime.toLocalDateTime().equals(onceTime)){
                    dateTime = onceTime.atDate(dateTime.toLocalDate()).atZone(dateTime.getZone());
                }
                do {
                    if(filter.test(dateTime.toLocalDateTime()) && current.compareTo(dateTime) <= 0){
                        current = dateTime.plusDays(1);
                        break;
                    }
                    dateTime = dateTime.plusDays(1);
                    max--;
                } while (max > 0);
                return dateTime;
            }
        };

    }

    private Predicate<LocalDateTime> createTimeFilter() {
        Predicate<LocalDateTime> range = createRangeFilter();
        if(mod == ExecuteMod.period){
            Predicate<LocalDateTime> predicate = period.createTimerFilter();
            return predicate.and(range);
        }
        if(mod == ExecuteMod.once){
            Predicate<LocalDateTime> predicate = once.createTimerFilter();
            return predicate.and(range);
        }
        return range;
    }

    private Predicate<LocalDateTime> createRangeFilter() {
        if (trigger == Trigger.week)
            return date -> when.contains(date.getDayOfWeek().getValue());
        else if (trigger == Trigger.month)
            return date -> when.contains(date.getDayOfMonth());
        return ignore -> true;
    }


    static int MAX_IT_TIMES = 10000;
    private TimerIterable cronIterable() {
        Cron cron = this.toCron();
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        Predicate<LocalDateTime> filter = createTimeFilter();

        return baseTime -> new Iterator<ZonedDateTime>() {

            ZonedDateTime current = baseTime;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public ZonedDateTime next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ZonedDateTime dateTime = current;
                int i = 0;
                do {
                    // 下个执行时间点
                    dateTime = executionTime
                            .nextExecution(current)
                            .orElse(null);
                    if(dateTime != null){
                        i++;
                        continue;
                    }
                    if(filter.test(dateTime.toLocalDateTime())){
                        break;
                    }
                } while (i< MAX_IT_TIMES);
                return current = dateTime;
            }
        };
    }

    private Cron toCron() {
        CronDefinition definition = quartz();
        if (trigger == Trigger.cron ||  trigger  == null){
            Assert.hasText(cron, "error.scene_rule_timer_cron_cannot_be_empty");
            return new CronParser(definition).parse(cron).validate();
        }

        CronBuilder builder = CronBuilder.cron(definition);
        builder.withYear(FieldExpression.always());
        builder.withMonth(FieldExpression.always());

        FieldExpression range;
        if(CollUtil.isNotEmpty(when)){
            FieldExpression expr = null;
            for (Integer integer: when){
                if(expr == null){
                    expr = FieldExpressionFactory.on(integer);
                } else {
                    expr = expr.and(FieldExpressionFactory.on(integer));
                }
            }
            range = expr;
        }else{
            range = FieldExpressionFactory.questionMark();
        }


        if(trigger == Trigger.week){
            builder.withDoM(FieldExpressionFactory.questionMark())
                    .withDoM(range);
        }else if(trigger == Trigger.month) {
            builder.withDoM(range)
                    .withDoW(FieldExpressionFactory.questionMark());
        }

        // 执行一次
        if(mod == ExecuteMod.once){
            LocalTime time = once.localTime();
            builder.withHour(FieldExpressionFactory.on(time.getHour()));
            builder.withMinute(FieldExpressionFactory.on(time.getMinute()));
            builder.withSecond(FieldExpressionFactory.on(time.getSecond()));
        }

        // 周期执行
        if(mod == ExecuteMod.period){
            LocalTime time = period.fromLocalTime();
            PeriodUnit unit = period.unit;

            if(unit == PeriodUnit.hours){
                builder.withHour(FieldExpressionFactory.every(FieldExpressionFactory.on(time.getHour()), period.every))
                        .withMinute(FieldExpressionFactory.on(time.getMinute()))
                        .withSecond(FieldExpressionFactory.on(time.getSecond()));
            }else if (unit == PeriodUnit.minutes){
                builder.withHour(FieldExpressionFactory.always())
                        .withMinute(FieldExpressionFactory.every(FieldExpressionFactory.on(time.getMinute()), period.every))
                        .withSecond(FieldExpressionFactory.on(time.getSecond()));
            } else if (unit == PeriodUnit.seconds) {
                builder
                   .withHour(FieldExpressionFactory.always())
                   .withMinute(FieldExpressionFactory.always())
                   .withSecond(FieldExpressionFactory.every(FieldExpressionFactory.on(time.getSecond()), period.every));
            }
        }

        return builder.instance().validate();
    }

    //
    private CronDefinition quartz() {
        return CronDefinitionBuilder
                .defineCron()
                .withSeconds()
                .withValidRange(0, 59)
                .and()
                .withMinutes()
                .withValidRange(0, 59)
                .and()
                .withHours()
                .withValidRange(0, 23)
                .and()
                .withDayOfMonth()
                .withValidRange(1, 31)
                .supportsL()
                .supportsW()
                .supportsLW()
                .supportsQuestionMark()
                .and()
                .withMonth()
                .withValidRange(1, 12)
                .and()
                .withDayOfWeek()
                .withValidRange(1, 7)
                .withMondayDoWValue(1)
                .supportsHash()
                .supportsL()
                .supportsQuestionMark()
                .and()
                .withYear()
                .withValidRange(1970, 2099)
                .withStrictRange()
                .optional()
                .and()
                .withCronValidation(CronConstraintsFactory.ensureEitherDayOfWeekOrDayOfMonth())
                .instance();
    }

    public Map<String, Object> convertToMap() {
        return BeanUtil.beanToMap(this,false,true);
    }

    @AllArgsConstructor
    static class TimerFlux extends Flux<Long> {
        final Function<ZonedDateTime, Duration> spec;
        final Scheduler scheduler;

        @Override
        public void subscribe(CoreSubscriber<? super Long> coreSubscriber) {

            TimerSubscriber subscriber = new TimerSubscriber(spec, scheduler, coreSubscriber);
            coreSubscriber.onSubscribe(subscriber);
        }
    }

    static class TimerSubscriber implements Subscription {
        final Function<ZonedDateTime, Duration> spec;
        final CoreSubscriber<? super Long> subscriber;
        final Scheduler scheduler;
        long count;
        Disposable scheduling;
        public TimerSubscriber(Function<ZonedDateTime, Duration> spec,
                               Scheduler scheduler,
                               CoreSubscriber<? super Long> subscriber) {
            this.scheduler = scheduler;
            this.spec = spec;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            trySchedule();
        }

        private void trySchedule() {
            if (scheduling != null) {
                scheduling.dispose();
            }

            ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochMilli(scheduler.now(TimeUnit.MILLISECONDS)), ZoneId.systemDefault());
            Duration delay = spec.apply(now);


            scheduling = scheduler
                    .schedule(
                            this::onNext,
                            delay.toMillis(),
                            TimeUnit.MILLISECONDS
                    );
        }

        public void onNext() {

            if (canSchedule()) {
                subscriber.onNext(count++);
            }
            trySchedule();
        }


        protected boolean canSchedule() {
            return true;
        }

        @Override
        public void cancel() {
            if (scheduling != null) {
                scheduling.dispose();
            }
        }
    }

    private static LocalTime parsTime(String time) {
        return LocalTime.parse(time);
    }


    public enum Trigger {
        week,
        month,
        cron
    }

    public enum ExecuteMod {
        period,
        once
    }


    @Getter
    @Setter
    public static class Period implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 周期执行的时间区间执行时间范围从.格式:[hh:mm],或者[hh:mm:ss]
         */
        private String from;

        /**
         * 执行时间范围止.格式:[hh:mm],或者[hh:mm:ss]
         */
        private String to;

        /**
         * 周期值，如:每[every][unit]执行一次
         */
        private int every;

        /**
         * 周期执行单位
         */
        private PeriodUnit unit;

        public LocalTime toLocalTime() {
            return parsTime(to);
        }

        public LocalTime fromLocalTime() {
            return parsTime(from);
        }

        public Predicate<LocalDateTime> createTimerFilter() {
            LocalTime to = this.toLocalTime();
            LocalTime from = this.fromLocalTime();
            Predicate<LocalDateTime> predicate = time -> time.toLocalTime().compareTo(from) >= 0;
            if (to != null) {
                predicate = predicate.and(time -> time.toLocalTime().compareTo(to) <= 0);
            }
            return predicate;
        }
    }

    @AllArgsConstructor
    public enum PeriodUnit {
        seconds(ChronoUnit.SECONDS),
        minutes(ChronoUnit.MINUTES),
        hours(ChronoUnit.HOURS);

        private final TemporalUnit temporal;

    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class Once implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 时间点.格式:[hh:mm],或者[hh:mm:ss]
         */
        private String time;

        public LocalTime localTime() {
            return parsTime(time);
        }

        public Predicate<LocalDateTime> createTimerFilter() {
            LocalTime onceTime = localTime();
            return time -> compareOnceTime(time.toLocalTime(), onceTime) == 0;
        }

        private int compareOnceTime(LocalTime time1, LocalTime time2) {
            int cmp  = Integer.compare(time1.getHour(), time2.getHour());
            if (cmp == 0) {
                cmp = Integer.compare(time1.getMinute(), time2.getMinute());
                if (cmp == 0) {
                    cmp = Integer.compare(time1.getSecond(), time2.getSecond());
                    //不比较纳秒
                }
            }
            return cmp;
        }
    }
}
