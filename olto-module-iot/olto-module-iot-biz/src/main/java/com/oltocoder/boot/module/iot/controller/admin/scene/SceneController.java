package com.oltocoder.boot.module.iot.controller.admin.scene;

import com.oltocoder.boot.framework.common.pojo.CommonResult;
import com.oltocoder.boot.module.iot.controller.admin.scene.vo.SceneRuleCreateReqVO;
import com.oltocoder.boot.module.iot.service.scene.SceneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.oltocoder.boot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 场景联动")
@RestController
@RequestMapping("/iot/scene")
public class SceneController {

    @Resource(name = "sceneService")
    private SceneService sceneService;

    @PostMapping("/create")
    @Operation(summary = "创建场景")
    public CommonResult<Long> createScene(@Valid @RequestBody SceneRuleCreateReqVO reqVO){
        Long id = sceneService.createScene(reqVO);
        return success(id);
    }

    @PostMapping("/enable")
    @Operation(summary = "创建场景")
    public CommonResult<Boolean> enableScene(@RequestParam("id")Long id){

        sceneService.enableScene(id);
        return success(true);
    }

}
