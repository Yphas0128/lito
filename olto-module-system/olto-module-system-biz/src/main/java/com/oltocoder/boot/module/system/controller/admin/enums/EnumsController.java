package com.oltocoder.boot.module.system.controller.admin.enums;

import com.oltocoder.boot.framework.common.pojo.CommonResult;
import com.oltocoder.boot.framework.common.util.enums.EnumsUtil;
import com.oltocoder.boot.framework.common.util.enums.dto.EnumDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/enums")
@Tag( name = "枚举")
public class EnumsController {

    @Operation(summary = "根据枚举的类名查询该类下的全部枚举值")
    @GetMapping({"/selectEnumsByType","/selectEnumsByType/{type}"})
    public CommonResult<HashMap<String, List<EnumDTO>>> selectEnumsByType(@PathVariable(value = "type", required = false)String type) throws Exception {
        HashMap<String, List<EnumDTO>> enums = EnumsUtil.getEnums(type);
        return CommonResult.success(enums);
    }

    @Operation(summary = "根据枚举的类名查询该类下的全部枚举值")
    @GetMapping({"/getByType/{type}"})
    public CommonResult<List<EnumDTO>> getEnumListByType(@PathVariable(value = "type", required = false)String type) throws Exception {
        List<EnumDTO> enums = EnumsUtil.getEnumList(type);
        return CommonResult.success(enums);
    }
}
