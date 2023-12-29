package com.oltocoder.boot.module.iot.controller.admin.device;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.oltocoder.boot.framework.common.pojo.CommonResult;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.iot.client.modbus.ModBusTcpClientSystem;
import com.oltocoder.boot.framework.iot.concurrent.AsyncCallback;
import com.oltocoder.boot.framework.iot.concurrent.AsyncFuture;
import com.oltocoder.boot.framework.iot.dal.ModBusTcpBaseEntity;
import com.oltocoder.boot.framework.security.core.util.SecurityFrameworkUtils;
import com.oltocoder.boot.module.infra.websocket.WebSocketApi;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.*;
import com.oltocoder.boot.module.iot.convert.device.DeviceConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.device.DeviceDO;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;
import com.oltocoder.boot.module.iot.service.device.DeviceService;
import com.oltocoder.boot.module.iot.service.protocol.ProtocolService;
import com.fazecast.jSerialComm.SerialPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.oltocoder.boot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.oltocoder.boot.framework.common.pojo.CommonResult.success;
import static com.oltocoder.boot.framework.common.util.collection.CollectionUtils.convertList;
import static com.oltocoder.boot.module.system.enums.ErrorCodeConstants.PROTOCOL_EXISTS_ERROR;

@Tag(name = "iot数据采集 - iot设备")
@RestController
@RequestMapping("/iot/device")
public class DeviceController {

    @Resource(name = "deviceService")
    private DeviceService deviceService;

    @Resource(name = "protocolService")
    private ProtocolService protocolService;

    @Resource
    private WebSocketApi webSocketApi;

    // modbusTcp
    @Resource
    private ModBusTcpClientSystem modBusTcpClientSystem;

    @PostMapping("/test")
    @Operation(summary = "获取设备")
    public CommonResult<Boolean> test(@Valid @RequestBody DeviceTestReqVO reqVO){
        DeviceDO device = deviceService.getDevice(reqVO.getId()); // 获取到协议?
        ModBusTcpBaseEntity entity = new ModBusTcpBaseEntity();
        entity.setDeviceSn(device.getCode());
        entity.setPort(device.getPort());
        entity.setIp(device.getHost());
        entity.setCmd(reqVO.getCmd());
        entity.setFieldType(reqVO.getType());
        entity.setWriteValue(reqVO.getWriteValue());
        entity.setStartAddress(reqVO.getStart());
        AsyncFuture<String> stringAsyncFuture = modBusTcpClientSystem.performRead(entity);
        stringAsyncFuture.onComplete(new AsyncCallback<String>() {

            @Override
            public void onSuccess(String result) {
                DeviceMessageVO messageVO = new DeviceMessageVO().setData(result).setNow(LocalDateTime.now()).setStatus(true).setMessageType("modbusTcp");
                webSocketApi.sendMessage(SecurityFrameworkUtils.getLoginUserId(), messageVO.toJsonStr());
            }

            @Override
            public void onFailure(Throwable throwable) {
                DeviceMessageVO messageVO = new DeviceMessageVO().setData(throwable.getMessage()).setNow(LocalDateTime.now()).setStatus(false).setMessageType("modbusTcp");
                webSocketApi.sendMessage(SecurityFrameworkUtils.getLoginUserId(), messageVO.toJsonStr());
            }
        });
        return success(true);
    }

    @PostMapping("/create")
    @Operation(summary = "创建设备")
    public CommonResult<Long> createDevice(@Valid @RequestBody DeviceCreateReqVO reqVO) {
        Long id = deviceService.createDevice(reqVO);
        return success(id);
    }

    @PutMapping("/update")
    @Operation(summary = "修改设备")
    public CommonResult<Boolean> updateDevice(@Valid @RequestBody DeviceUpdateReqVO reqVO) {
        deviceService.updateDevice(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Long id){
        deviceService.deleteDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取设备")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<DeviceRespVO> getDevice(@RequestParam("id") Long id){
        DeviceDO device = deviceService.getDevice(id);
        DeviceRespVO respVO = DeviceConvert.INSTANCE.convert02(device);
        ProtocolDO protocolDO = protocolService.getProtocol(device.getProtocol());
        respVO.setProtocolType(protocolDO.getProtocolType());
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取设备列表")
    public CommonResult<PageResult<DeviceItemRespVO>> getDevicePage(@Valid DevicePageReqVO reqVO){
        PageResult<DeviceDO> pageResult = deviceService.getDevicePage(reqVO);
        if(CollUtil.isEmpty(pageResult.getList()))
            return success(new PageResult<>(pageResult.getTotal()));

        List<Long> protocolIds = convertList(pageResult.getList(), DeviceDO::getProtocol);
        Map<Long, ProtocolDO> protocolMap = protocolService.getProtocolMap(protocolIds);
        List<DeviceItemRespVO> respVOS = new ArrayList<>();
        pageResult.getList().forEach(deviceDO -> {
            DeviceItemRespVO respVO = DeviceConvert.INSTANCE.convert03(deviceDO);
            Optional.ofNullable(protocolMap.get(deviceDO.getProtocol())).ifPresent(protocolDO ->
                    respVO.setMessageProtocol(protocolDO.getName())
                            .setProtocolType(protocolDO.getProtocolType()));
            respVOS.add(respVO);
        });
        return success(new PageResult<>(respVOS, pageResult.getTotal()));
    }

    @GetMapping("/get-simple-list")
    @Operation(summary = "获取设备列表")
    public CommonResult<List<DeviceRespVO>> getDeviceList(){
        List<DeviceDO> devices = deviceService.getDeviceList();
        List<DeviceRespVO> respVOS = DeviceConvert.INSTANCE.convertList(devices);
        return success(respVOS);
    }

    @GetMapping("/get-serial-ports")
    @Operation(summary = "获取设备列表")
    public CommonResult<List<SerialPortVO>> getSerialPorts() {
        SerialPort[] serialPorts = SerialPort.getCommPorts();

        List<SerialPortVO> serialPortVOS = new ArrayList<>(serialPorts.length);
        SerialPortVO serialPortVO;
        for (SerialPort serialPort: serialPorts) {
            serialPortVO = new SerialPortVO( serialPort.getSystemPortName(), serialPort.getSystemPortName());
            serialPortVOS.add(serialPortVO);
        }
        return success(serialPortVOS);
    }

    @PostMapping("/run/{id}")
    @Operation(summary = "连接设备")
    public CommonResult<Boolean> runDevice(@PathVariable("id") Long id){
        deviceService.runDevice(id);
        return success(true);
    }

    @GetMapping("/get-protocol-type")
    @Operation(summary = "获取协议类型")
    @Parameter(name = "id",description = "id",required = true)
    public  CommonResult<Boolean> getProtocolType(@RequestParam("id") Long id){
        ProtocolDO protocolDO =  deviceService.getDeviceProtocol(id);
        if(ObjUtil.isNull(protocolDO))
            throw exception(PROTOCOL_EXISTS_ERROR);
        switch (protocolDO.getProtocolType()){
            case 1 : return success(true);
            case 2:  return success(false);
            default:  throw exception(PROTOCOL_EXISTS_ERROR);
        }
    }
}
