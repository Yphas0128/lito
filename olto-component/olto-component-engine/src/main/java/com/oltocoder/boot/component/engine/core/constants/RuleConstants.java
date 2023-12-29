package com.oltocoder.boot.component.engine.core.constants;

public interface RuleConstants {

    interface Topics {
        static String prefix(String instanceId, String nodeId) {
            return "/rule-engine/" + instanceId + "/" + nodeId;
        }

        static String input(String instanceId, String nodeId) {
            return prefix(instanceId, nodeId) + "/input";
        }

        static String event(String instanceId, String nodeId, String event) {
            return prefix(instanceId, nodeId) + "/event/" + event;
        }
    }
}
