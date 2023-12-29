package com.oltocoder.boot.module.iot.controller.admin.protocol;

import com.oltocoder.boot.framework.common.pojo.CommonResult;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.*;
import com.oltocoder.boot.module.iot.convert.protocol.ProtocolConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;
import com.oltocoder.boot.module.iot.service.protocol.ProtocolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;

import static com.oltocoder.boot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - iot协议")
@RestController
@RequestMapping("/iot/protocol")
public class ProtocolController {

    @Resource(name = "protocolService")
    private ProtocolService protocolService;

    @PostMapping("/create")
    @Operation(summary = "创建iot协议")
    public CommonResult<Long> createProtocol(@Valid @RequestBody ProtocolCreateReqVO reqVO) {
        Long id = protocolService.createProtocol(reqVO);
        return success(id);
    }

    @PutMapping("/update")
    @Operation(summary = "修改iot协议")
    public CommonResult<Boolean> updateProtocol(@Valid @RequestBody ProtocolUpdateReqVO reqVO) {
        protocolService.updateProtocol(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除iot协议")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<Boolean> deleteProtocol(@RequestParam("id") Long id){
        protocolService.deleteProtocol(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取iot协议")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<ProtocolRespVO> getProtocol(@RequestParam("id") Long id){
        ProtocolDO Protocol = protocolService.getProtocol(id);
        return success(ProtocolConvert.INSTANCE.convert(Protocol));
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取iot协议列表")
    public CommonResult<PageResult<ProtocolRespVO>> getProtocolPage(@Valid ProtocolPageReqVO reqVO){
        PageResult<ProtocolDO> pageResult = protocolService.getProtocolPage(reqVO);
        return success(ProtocolConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/get-simple-list")
    @Operation(summary = "获取iot协议列表")
    public CommonResult<List<ProtocolRespVO>> getProtocolList(){
        List<ProtocolDO> Protocols = protocolService.getProtocolList();
        List<ProtocolRespVO> respVOS = ProtocolConvert.INSTANCE.convertList(Protocols);
        return success(respVOS);
    }
}
