package com.oltocoder.boot.component.engine.core.entity.scene;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.oltocoder.boot.component.core.consts.RuleDataConstants;
import com.oltocoder.boot.component.engine.core.condition.TermCondition;
import com.oltocoder.boot.component.engine.core.entity.param.Term;
import com.oltocoder.boot.component.engine.core.rule.RuleLink;
import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.actions.SceneAction;
import com.oltocoder.boot.component.engine.core.internal.triggers.Trigger;
import com.oltocoder.boot.component.engine.core.task.provider.impl.SceneTaskExecutorProvider;
import com.oltocoder.boot.framework.common.enums.TriggerTypeEnum;
import lombok.Data;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.Function3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Data
public class SceneRule {
    public static final String ACTION_KEY_BRANCH_INDEX = "_branchIndex";
    public static final String ACTION_KEY_GROUP_INDEX = "_groupIndex";
    public static final String ACTION_KEY_ACTION_INDEX = "_actionIndex";

    public static final String CONTEXT_KEY_SCENE_OUTPUT = "scene";

    /**
     * 主键
     */
    private Long id ;

    /**
     * 场景名称
     */
    private String name;

    /**
     * 触发器
     */
    private Trigger trigger;

    /**
     * 多个触发条件
     */
    private List<Term> terms;

    /**
     * 是否并行执行动作
     */
    private boolean parallel;

    /**
     * 执行动作
     */
    private List<SceneAction> actions;

    /**
     *动作分支
     */
    private List<SceneConditionAction> branches;

    /**
     * 扩展属性
     */
    private Map<String, Object> configuration;

    /**
     * 转换成 ruleModel
     */
    public RuleModel toModel() {

        RuleModel model = new RuleModel();
        model.setId(id.toString());
        model.setName(name);
        model.setType("scene");

        RuleNodeModel sceneNode = new RuleNodeModel();
        sceneNode.setId(id.toString());
//        sceneNode.setName(name);
        sceneNode.setConfiguration(convertToMap());
        sceneNode.setExecutor(SceneTaskExecutorProvider.EXECUTOR);


        trigger.applyModel(model, sceneNode); //

        sceneNode.addConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER, true);
        sceneNode.addConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER_KEY, CONTEXT_KEY_SCENE_OUTPUT);
        model.getNodes().add(sceneNode);

        if (CollUtil.isNotEmpty(actions)) {
            int index = 1;
            RuleNodeModel preNode = null;
            SceneAction preAction = null;
             // 执行动作
            for (SceneAction action : actions) {
                RuleNodeModel actionNode = new RuleNodeModel();
//                actionNode.setName("动作_" + index);
                action.applyNode(actionNode);
                // 并行
                if (parallel) {
                    model.link(sceneNode, actionNode);
                }else{
                    // 串行的时候 标记记录每一个动作的数据到header中，用于进行条件判断或者数据引用
                    actionNode.addConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER, true);
                    actionNode.addConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER_KEY, actionNode.getId());

                    if (preNode == null) {
                        // 场景节点 -> 第一个动作节点
                        model.link(sceneNode, preNode = actionNode);
                    } else {
                        // 上一个节点->当前动作节点
                        RuleLink link = model.link(preNode, actionNode);
                        // 设置上一个节点到此节点的输出条件
                        // 条件
                        if (CollUtil.isNotEmpty(preAction.getTerms())) {

                            //todo 未完待续
                            link.setTermCondition(createCondition(trigger.refactorTerm("this", preAction.getTerms())));
                        }
                        preNode = actionNode;
                    }
                }
                model.getNodes().add(actionNode);
                preAction = action;
                index++;
            }
        }

        // 使用分支条件
        if(CollUtil.isNotEmpty(branches)){
            int branchIndex = 0;
            for (SceneConditionAction branch : branches) {
                branchIndex++;
                List<SceneActions> group = branch.getThen();

                if (CollUtil.isEmpty(group)) continue;
                int groupIndex = 0;
                for (SceneActions actions : group) {
                    groupIndex++;
                    if(actions != null && CollUtil.isNotEmpty(actions.getActions())){
                        int actionIndex = 1;
                        RuleNodeModel preNode = null;
                        SceneAction preAction = null;

                        for (SceneAction action : actions.getActions()) {

                            RuleNodeModel actionNode = new RuleNodeModel();
                            actionNode.setId(createBranchActionId(branchIndex, groupIndex, actionIndex));
//                            actionNode.setName("条件" + branchIndex + "_分组" + groupIndex + "_动作" + actionIndex);

                            action.applyNode(actionNode);
                            if (!actions.isParallel()) {
                                actionNode.addConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER, true);
                                actionNode.addConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER_KEY, actionNode.getId());
                                actionNode.addConfiguration(ACTION_KEY_BRANCH_INDEX, branchIndex);
                                actionNode.addConfiguration(ACTION_KEY_GROUP_INDEX, groupIndex);
                                actionNode.addConfiguration(ACTION_KEY_ACTION_INDEX, actionIndex);
                                if (preNode != null) {
                                    //上一个节点->当前动作节点
                                    RuleLink link = model.link(preNode, actionNode);
                                    //设置上一个节点到此节点的输出条件 todo 未完待续
                                    if (CollUtil.isNotEmpty(preAction.getTerms())) {
                                        link.setTermCondition(createCondition(trigger.refactorTerm("this", preAction.getTerms())));
                                    }
                                } else if (Objects.equals(trigger.getType(), TriggerTypeEnum.manual.name())) {
                                    model.link(sceneNode, actionNode);
                                }

                                preNode = actionNode;
                            }else{

                                if (Objects.equals(trigger.getType(),TriggerTypeEnum.manual.name())) {
                                    model.link(sceneNode, actionNode);
                                }
                            }
                            model.getNodes().add(actionNode);
                            preAction = action;
                            actionIndex++;
                        }
                    }
                }
            }
        }

        return model;
    }

    private Map<String, Object> convertToMap() {
        return BeanUtil.beanToMap(this,false,true);
    }

    private String createBranchActionId(int branchIndex, int groupId, int actionIndex) {
        return "branch_" + branchIndex + "_group_" + groupId + "_action_" + actionIndex;
    }

    public TermCondition createCondition(List<Term> terms) {
        TermCondition condition = new TermCondition();
        condition.setType("terms");
        condition.setTerms(terms);
        return condition;
    }

    public SceneRule parseTrigger(String str) {
        this.trigger  = JSON.parseObject(str, Trigger.class);
        return this;
    }

    public SceneRule ParseBranches(String str) {
        List<SceneConditionAction> branches = JSON.parseArray(str, SceneConditionAction.class);
        this.branches = branches;
        return this;
    }

    public SceneRule parseConfiguration(String str) {
        Map<String,Object> configuration = JSON.parseObject(str, Map.class);
        this.configuration = configuration;
        return this;
    }


    public List<Term> getTerms(){
        List<Term> terms = new ArrayList<>();
        if(CollUtil.isNotEmpty(this.terms)){
            terms.addAll(this.terms);
        }
        if(CollUtil.isNotEmpty(this.branches)){
            for (SceneConditionAction branch : branches) {
                terms.addAll(branch.createContextTerm());
            }
        }

        return terms;
    }


    // todo 未完待续
    public Disposable createBranchHandler(Flux<Map<String, Object>> source,
                                          Function3<Integer, String, Map<String, Object>, Mono<Void>> output) {

        if (CollUtil.isEmpty(branches)) {
            return Disposables.disposed();
        }

        Long id = this.id;
        String name = this.name;

        int branchIndex = 0;
        for (SceneConditionAction branch : branches) {
            int _branchIndex = ++branchIndex;

            // createDefaultFilter(branch.getWhen());
            //满足条件后的输出操作
            List<Function<Map<String, Object>, Mono<Void>>> outs = new ArrayList<>();


            List<SceneActions> groups = branch.getThen();

            if (CollUtil.isNotEmpty(groups)) {
                int thenIndex = 0;
                for (SceneActions then: groups){
                    thenIndex++;

                    Function<Map<String, Object>, Mono<Void>> out;

                    int size = then.getActions().size();
                    if (size == 0) continue;

                    if(!then.isParallel() || size == 1){
                        String nodeId = createBranchActionId(_branchIndex, thenIndex, 1);
                        out = data -> output.apply(_branchIndex, nodeId, data);
                    }else{

                    }
//                    outs.add(out);
                }
            }
        }
        return Mono.empty().subscribe();
    }
}
