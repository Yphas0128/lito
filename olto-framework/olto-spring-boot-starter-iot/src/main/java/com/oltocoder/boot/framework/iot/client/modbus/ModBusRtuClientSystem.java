package com.oltocoder.boot.framework.iot.client.modbus;

import com.oltocoder.boot.framework.iot.concurrent.ContextInternal;
import com.oltocoder.boot.framework.iot.core.consts.IotConsts;
import com.oltocoder.boot.framework.iot.dal.ModBusRtuBaseEntity;
import com.iteaj.iot.ProtocolException;
import com.iteaj.iot.consts.ExecStatus;
import com.iteaj.iot.modbus.ModbusCommonProtocol;
import com.iteaj.iot.modbus.Payload;
import com.iteaj.iot.modbus.ReadPayload;
import com.iteaj.iot.modbus.RealCoilPayload;
import com.iteaj.iot.modbus.consts.ModbusCoilStatus;
import com.iteaj.iot.modbus.server.rtu.ModbusRtuBody;
import com.iteaj.iot.serial.SerialConnectProperties;

/**
 * @title: ModbusRtuClientSystem
 * @Author cmw
 * @Date: 2023/8/9 20:17
 * @describe
 */
public class ModBusRtuClientSystem extends AbstractModBusClientSystem<ModBusRtuBaseEntity,String> {

    protected ModBusRtuClientSystem(ContextInternal context) {
        super(context);
    }

    @Override
    protected ModbusRtuClientCommonProtocol getModbusCommonProtocol(String cmd, Integer type, Integer slaveAddress, Integer start, String WriteValue) {
        ModbusRtuClientCommonProtocol protocol = null;
        switch (cmd) {
            case "01":
                protocol = ModbusRtuClientCommonProtocol.buildRead01(slaveAddress
                        , start, this.getTypeCoilNum(type)); break;
            case "02":
                protocol = ModbusRtuClientCommonProtocol.buildRead02(slaveAddress
                        , start, this.getTypeCoilNum(type)); break;
            case "03":
                protocol = ModbusRtuClientCommonProtocol.buildRead03(slaveAddress
                        , start, this.getTypeCoilNum(type)); break;
            case "04":
                protocol = ModbusRtuClientCommonProtocol.buildRead04(slaveAddress
                        , start, this.getTypeCoilNum(type)); break;
            case "05":
                Object writeValue =  parseWriteValue(type,WriteValue);
                if(writeValue == null) {
                    throw new IllegalArgumentException("\"写05功能码的值只能是[0 或 1]");
                }
                protocol = ModbusRtuClientCommonProtocol.buildWrite05(slaveAddress
                        , start, (ModbusCoilStatus) writeValue); break;
            case "06":
                protocol = ModbusRtuClientCommonProtocol.buildWrite06(slaveAddress
                        , start,  parseWriteValue(type,WriteValue)); break;
            case "10":
                byte[] writeBytes =  parseWriteValue(type,WriteValue);
                protocol = ModbusRtuClientCommonProtocol.buildWrite10(slaveAddress
                        , start, writeBytes.length / 2, writeBytes); break;
            case "0F":
                protocol = ModbusRtuClientCommonProtocol.buildWrite0F(
                        slaveAddress, start, (byte[])  parseWriteValue(type,WriteValue)); break;
            default: throw new IllegalArgumentException("不支持的指令["+cmd+"]");

        }
        return protocol;
    }

    @Override
    protected String readInternal(ModBusRtuBaseEntity params) throws Exception {
        Integer type = params.getFieldType();
        Integer startAddress = params.getStartAddress();

        if(startAddress > 40001) {
            startAddress = startAddress - 40001;
        }

        if(type == IotConsts.FIELD_TYPE_BOOLEAN) {
            startAddress = startAddress - 1;
        }

        ModbusRtuClientCommonProtocol syncProtocol = getModbusCommonProtocol(params.getCmd(),type,params.getSlaveAddress(),startAddress,params.getWriteValue());

        try {
            SerialConnectProperties config = new SerialConnectProperties(params.getCom(), params.getBaudRate())
                    .config(params.getDataBits(), params.getStopBits(), params.getParity());
            syncProtocol.sync(3000).request(config);
        } catch (ProtocolException e) {
            throw new Exception(e.getMessage());
        }

        if(syncProtocol.getExecStatus() == ExecStatus.success) {
            ModbusRtuBody body = syncProtocol.responseMessage().getBody();
            if(body.isSuccess()) {
                Payload payload = syncProtocol.getPayload();
                Object value ;
                if(payload instanceof ReadPayload) {
                    value= parseReadResultValue(params.getNum(),type,startAddress, payload).toString();
                } else if(payload instanceof RealCoilPayload) {
                    value= payload.readStatus(0).getBit();
                } else {
                    value= params.getWriteValue();
                }
                return value.toString();
            } else {
                throw new Exception(body.getErrCode().getDesc());
            }
        } else {
            throw new Exception(syncProtocol.getExecStatus().desc);
        }

    }

    @Override
    protected String writeInternal(ModBusRtuBaseEntity params) throws Exception {
        return readInternal(params);
    }






}
