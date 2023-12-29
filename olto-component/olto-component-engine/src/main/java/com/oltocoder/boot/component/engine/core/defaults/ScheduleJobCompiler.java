package com.oltocoder.boot.component.engine.core.defaults;

import com.oltocoder.boot.component.engine.core.rule.RuleLink;
import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: 调度任务编译器 将规则模型编译成调度任务
 * @Author Ypier
 * @Date: 2023/9/2 14:49
 */
public class ScheduleJobCompiler {

    private final Map<String, ScheduleJob> jobs = new HashMap<>();

    private final String instanceId;

    private final RuleModel model;
    
    public ScheduleJobCompiler(String instanceId, RuleModel model) {
        this.instanceId = instanceId;
        this.model = model;
    }

    public List<ScheduleJob> compile() {
        for (RuleNodeModel node : model.getNodes()) {
            ScheduleJob job = new ScheduleJob();
            job.setInstanceId(instanceId);
            job.setRuleId(model.getId().toString());
            job.setNodeId(node.getId());
            job.setConfiguration(node.getConfiguration());
//            job.setRuleConfiguration(model.getConfiguration());
            job.setModelType(model.getType());
            job.setExecutor(node.getExecutor());
//            job.setName(node.getName());
//            job.setSchedulingRule(node.getSchedulingRule());
            jobs.put(node.getId(), job);
        }
        for (RuleNodeModel node : model.getNodes()) {
            prepare(node);
        }
        return new ArrayList<>(jobs.values());
    }

    private void prepare(RuleNodeModel node) {
        ScheduleJob job = getJob(node.getId());

        {
            List<String> inputs = new ArrayList<>();

            for (RuleLink input : node.getInputs()) {
                inputs.add(input.getSource().getId());
            }
            job.setInputs(inputs);
        }

//        {
//            for (RuleLink event : node.getEvents()) {
//                //事件输入
//                getJob(event.getTarget().getId())
//                        .getEvents()
//                        .add(new ScheduleJob.Event(event.getType(), node.getId()));
//                //事件输出
//                job.getEventOutputs().add(new ScheduleJob.Event(event.getType(), event.getTarget().getId()));
//            }
//        }

        {
            List<ScheduleJob.Output> outputs = new ArrayList<>();
            for (RuleLink output : node.getOutputs()) {
                outputs.add(new ScheduleJob.Output(output.getTarget().getId(), output.getTermCondition()));
            }
            job.setOutputs(outputs);
        }

    }

    private ScheduleJob getJob(String nodeId) {
        return jobs.get(nodeId);
    }
}
