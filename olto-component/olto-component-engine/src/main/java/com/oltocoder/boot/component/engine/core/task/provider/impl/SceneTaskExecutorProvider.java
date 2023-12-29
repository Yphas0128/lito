package com.oltocoder.boot.component.engine.core.task.provider.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.component.engine.core.entity.scene.SceneRule;
import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.AbstractTaskExecutor;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProvider;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class SceneTaskExecutorProvider implements TaskExecutorProvider {

    public static final String EXECUTOR =  "scene";

    @Override
    public String getExecutor() {
        return EXECUTOR;
    }

    @Override
    public Mono<TaskExecutor> createTask(ExecutionContext context) {
        return  Mono.just(new SceneTaskExecutor(context));
    }


    class SceneTaskExecutor extends AbstractTaskExecutor {

        private SceneRule rule;

        public SceneTaskExecutor(ExecutionContext context) {
            super(context);
            load();
        }


        private void load() {
            SceneRule sceneRule = createRule();
//            sceneRule.validate();
            this.rule = sceneRule;
        }

        private SceneRule createRule() {
            return BeanUtil.toBean(context.getJob().getConfiguration(), SceneRule.class);
        }


        @Override
        public void reload() {
            load();
            doStart();
        }

        /**
         * todo 未完待续
         * 任务开始
         * @return
         */
        @Override
        protected Disposable doStart() {
            return disposable = init();
        }

        private Disposable init() {
            if (disposable != null){
                disposable.dispose();
            }

            Long ruleId = rule.getId();
            String ruleName = rule.getName();
            boolean useBranch = CollUtil.isNotEmpty(rule.getBranches());

//            SqlRequest request = rule.createSql(!useBranch);
            Flux<Map<String, Object>> source ;
            // 分支条件
            if(useBranch){

//                return rule.createBranchHandler(source, (idx, nodeId,data) -> {
//                    return null;
//                });
            }

            return Mono.empty().subscribe();
        }
    }
}
