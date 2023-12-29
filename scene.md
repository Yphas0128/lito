
# scene 模块难点在于 1事件总线  2. term 条件判断


```mermaid
classDiagram
	class SceneRule {
		<<场景>>
		-Trigger trigger
		-List~SceneAction~ actions
		-List~Term~ terms
		-boolean parallel 
		+toModel() RuleModel
	}
	class Trigger {
		<<触发器>>
		-DeviceTrigger device
		-TimerTrigger timer
	}
	
	class SceneAction {
		<<执行动作>>
		- String executor
		- NotifyAction notifyAction
		- DelayAction delayAction
		- DeviceAction deviceAction
		- AlarmAction alarmAction
		
		+applyNode(RuleNodeModel) 
	}
```



## 触发器Trigger

```mermaid
classDiagram
	 ITriggerConfig <|.. DeviceTrigger
	 ITriggerConfig <|.. TimerTrigger
	 ITriggerConfig <|.. ManualTrigger
	class ITriggerConfig{
		<<Interface>>
		+ applyModel(RuleModel,RuleNodeModel)
		+triggerConfig(): ITriggerConfig
		-provider(): SceneTriggerProvider 
	}
	class DeviceTrigger{
		-Integer deviceOperator
		-String cron
		-String eventId
		-List~String~ readProperties
		-Map~Object~ readProperties
		-String functionId
		+applyModel(RuleModel,RuleNodeModel)
	}
	class TimerTrigger{
		-String cron	
	}
```



### 执行工厂Action 

```mermaid
classDiagram
	class NotifyAction{
	
	}
	class DelayAction{
	
	}
	class DeviceAction{
		-Long productId
		-Map<String, Object> message
		+applyRuleNode(RuleNodeModel)
	}
	class AlarmAction{
	
	}
```





## 场景提供工厂

```mermaid
classDiagram 
	SceneActionProvider <|.. DelayActionProvider
	SceneActionProvider <|.. DeviceActionProvider
	SceneActionProvider <|.. AlarmActionProvider
	SceneActionProvider <|.. NotifyActionProvider
	class SceneActionProvider~C~{
		<<interface>>
		+getProvider(): String

    	applyRuleNode(C,RuleNodeModel)
	}
	
	SceneTriggerProvider <|.. DeviceTriggerProvider
	SceneTriggerProvider <|.. ManualTriggerProvider
	SceneTriggerProvider <|.. TimerTriggerProvider
	class SceneTriggerProvider~C extends ITriggerConfig ~{
			<<interface>>
     		+getProvider(): String
     		+applyRuleNode(C, RuleModel, RuleNodeModel);
            +newConfig():C
	}
```

```mermaid
classDiagram 
	class SceneProviderFactory{
	 	- Map~SceneActionProvider~ actionProviders
	 	- Map~SceneTriggerProvider~ triggerProviders
	 	+register(SceneActionProvider)
	 	+register(SceneTriggerProvide)
	 	getActionProviderNow(String provider): SceneActionProvider
	 	+getTriggerProviderNow(String provider): SceneTriggerProvider
	}
```

## RuleModel

```mermaid
classDiagram
    class RuleModel{
		-String name
		-String type
		-List~RuleLink~ events
		-List~RuleNodeModel~ nodes
		
		+link(RuleNodeModel,RuleNodeModel)  RuleLink
    }
    
    class RuleNodeModel{
    	-String ruleId
    	-String name
    	-String executor
    	- Map<String, Object> configuration
    	-List~RuleLink~ inputs
    	-List~RuleLink~ outputs
    	
    	+addConfiguration(String, Object) RuleNodeModel
    }
    
    class RuleLink{
     	-String name
     	-RuleNodeModel soruce
     	-RuleNodeModel target
     	-TermCondition condition
    }
```

## 任务执行TaskExecutorProvider

```mermaid
classDiagram
  TaskExecutorProvider <|.. DeviceMessageSendTaskExecutorProvider
  TaskExecutorProvider <|.. SceneTaskExecutorProvider
  TaskExecutorProvider <|.. TimerTaskExecutorProvider
  class TaskExecutorProvider {
  	<<interface>>
  	 +String getExecutor()
  	 +createTask(ExecutionContext) TaskExecutor
  }
  class DeviceMessageSendTaskExecutorProvider{
 	+createTask(ExecutionContext) TaskExecutor
  }
```

```mermaid
classDiagram
  	TaskExecutor <|.. TimerTaskExecutor
    class TaskExecutor {
		<<interface>>
    }
```

## 调度ScheduleJob

```mermaid
classDiagram
	class ScheduleJob {
	  - String instanceId
	  - String ruleId
	  - String nodeId
	  - String name
	  - String modelType
	  - String executor
	  - List~String~ inputs 
	  - List~Output~ outputs
	}
	
	class ScheduleJobCompiler{
		- String instanceId
		- RuleModel ruleModel
		
		+compile() List~ScheduleJob~
	}
```



## 调度scheduler

```mermaid
classDiagram
  	IScheduler <|.. DefaultScheduler
	class IScheduler {
		<<interface>>
		+schedule(ScheduleJob)：Task
	}
	class DefaultScheduler{
		-WorkerSelector workerSelector
		-Map executors
		-Map tasks
		
		+schedule(ScheduleJob) List~Task~
		+createExecutor(ScheduleJob) List~Task~
		-addTask(Task)
		-getExecutor(instanceId) Map~Task~
     }
```

## 工作器Worker

```mermaid
classDiagram
  	Worker <|.. DefaultWorker
	class Worker {
		<<interface>>
		 +createTask(ScheduleJob job) List~Task~
	}
	class DefaultWorker{
		-IConditionEvaluator conditionEvaluator
		MqEvent eventBus
		+createTask(ScheduleJob)  List~Task~
		createContext(ScheduleJob) DefaultExecutionContext
	}
```

## 具体任务Task 

```mermaid
classDiagram
	class Task{ 
		<<interface>>
		+String getId()	
		ScheduleJob getJob()
	}
	
	class DefaultTask{
		-String id
		-AbstractExecutionContext context
	}
```

## 执行上下文ExecutionContext

```mermaid
classDiagram
	 ExecutionContext <|.. AbstractExecutionContext
	 AbstractExecutionContext <|-- DefaultExecutionContext
	class ExecutionContext{
	    <<interface>>
		+ String getInstanceId()
		+  ScheduleJob getJob()
	}
	class AbstractExecutionContext {
		- ScheduleJob job
		- MqEvent eventBus
	}
	class DefaultExecutionContext{
	
	}
```

# 未完待续