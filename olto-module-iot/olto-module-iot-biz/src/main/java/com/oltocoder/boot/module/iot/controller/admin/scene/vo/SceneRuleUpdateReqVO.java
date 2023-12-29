package com.oltocoder.boot.module.iot.controller.admin.scene.vo;

import com.oltocoder.boot.module.iot.controller.admin.scene.vo.item.DeviceTriggerReqVO;
import com.oltocoder.boot.module.iot.controller.admin.scene.vo.item.SceneActionReqVO;
import com.oltocoder.boot.module.iot.controller.admin.scene.vo.item.SceneTermReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SceneRuleUpdateReqVO extends SceneRuleBaseVO {

    @Schema(description = "主键")
    private Long id ;

    @Schema(description = "[type]为[device]时不能为空")
    private DeviceTriggerReqVO deviceTriggerReqVO;

    @Schema(description = "触发条件")
    private List<SceneTermReqVO> terms; // 多个触发条件

    @Schema(description = "是否并行执行动作")
    private boolean parallel;

    @Schema(description = "执行动作")
    private List<SceneActionReqVO> actions;
}
