package com.oltocoder.boot.component.engine.core.task.executor;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.core.consts.RuleConstants;
import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import lombok.Getter;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// todo 替换成
public abstract class FunctionTaskExecutor extends AbstractTaskExecutor {

    @Getter
    private final String name;

    public FunctionTaskExecutor(String name, ExecutionContext context) {
        super(context);
        this.name = name;
    }

    @Override
    protected Disposable doStart() {
       return context.getInput()
               .accept(message-> doApply(message))
               .onErrorResume(error -> context.onError(error, null))
               .subscribe();
    }

    private Mono<Void> doApply(RuleData input) {
        return context
                .getOutput()
                .write(Flux.from(this.apply(input))
                        .flatMap(output -> context
                                .fireEvent(RuleConstants.Event.result, output)
                                .thenReturn(output)))
                .then(context.fireEvent(RuleConstants.Event.complete, input))
//                .as(tracer())
                .onErrorResume(error -> context.onError(error, input))
//                .contextWrite(TraceHolder.readToContext(Context.empty(), input.getHeaders()))
                .then();
    }

    protected abstract Publisher<RuleData> apply(RuleData input);

}
