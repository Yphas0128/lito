package com.oltocoder.boot.framework.iot.client.modbus;

import com.oltocoder.boot.framework.iot.concurrent.ContextInternal;
import com.oltocoder.boot.framework.iot.core.consts.IotConsts;
import com.oltocoder.boot.framework.iot.dal.ModBusTcpBaseEntity;
import com.iteaj.iot.ProtocolException;
import com.iteaj.iot.client.ClientConnectProperties;
import com.iteaj.iot.consts.ExecStatus;
import com.iteaj.iot.modbus.ModbusCommonProtocol;
import com.iteaj.iot.modbus.Payload;
import com.iteaj.iot.modbus.ReadPayload;
import com.iteaj.iot.modbus.RealCoilPayload;
import com.iteaj.iot.modbus.client.tcp.ModbusTcpClientCommonProtocol;
import com.iteaj.iot.modbus.consts.ModbusCoilStatus;
import com.iteaj.iot.modbus.server.tcp.ModbusTcpBody;

/**
 * @title: ModbusTcpClientSystem
 * @Author cmw
 * @Date: 2023/8/9 20:14
 * @describe
 */
public class ModBusTcpClientSystem extends AbstractModBusClientSystem<ModBusTcpBaseEntity,String> {


    public ModBusTcpClientSystem(ContextInternal context) {
        super(context);
    }

    @Override
    protected String readInternal(ModBusTcpBaseEntity params) throws Exception {
        Integer type = params.getFieldType();
        Integer startAddress = params.getStartAddress();

        if(startAddress > 40001) {
            startAddress = startAddress - 40001;
        }

        if(type == IotConsts.FIELD_TYPE_BOOLEAN) {
            startAddress = startAddress - 1;
        }

        ModbusTcpClientCommonProtocol syncProtocol = (ModbusTcpClientCommonProtocol)getModbusCommonProtocol(params.getCmd(),type,1,params.getStartAddress(),params.getWriteValue());

        Object value;
        try {
            syncProtocol.sync(3000).request(new ClientConnectProperties(params.getIp(), params.getPort(), params.getDeviceSn()));
        } catch (ProtocolException e) {
            throw new Exception(e.getMessage());
        }

        if(syncProtocol.getExecStatus() == ExecStatus.success) {
            ModbusTcpBody body = syncProtocol.responseMessage().getBody();
            if(body.isSuccess()) {
                Payload payload = syncProtocol.getPayload();
                if(payload instanceof ReadPayload) {
                    value= parseReadResultValue(params.getNum(),type,startAddress, payload).toString();
                } else if(payload instanceof RealCoilPayload) {
                    value= payload.readStatus(0).getBit();
                } else {
                    value= params.getWriteValue();
                }
            } else {
                throw new Exception(body.getErrCode().getDesc());
            }
        } else {
            throw new Exception(syncProtocol.getExecStatus().desc);
        }
        return value.toString();
    }

    @Override
    protected String writeInternal(ModBusTcpBaseEntity params) throws Exception {
        return readInternal(params);
    }



    @Override
    protected ModbusCommonProtocol getModbusCommonProtocol(String cmd,Integer type,Integer slaveAddress,Integer start,String WriteValue) {
        ModbusTcpClientCommonProtocol protocol;
        switch (cmd) {
            case "01":
                protocol = ModbusTcpClientCommonProtocol.buildRead01(
                        slaveAddress,start, this.getTypeCoilNum(type)); break;
            case "02":
                protocol = ModbusTcpClientCommonProtocol.buildRead02(
                        slaveAddress, start, this.getTypeCoilNum(type)); break;
            case "03":
                protocol = ModbusTcpClientCommonProtocol.buildRead03(
                        slaveAddress, start, this.getTypeCoilNum(type)); break;
            case "04":
                protocol = ModbusTcpClientCommonProtocol.buildRead04(
                        slaveAddress, start, this.getTypeCoilNum(type)); break;
            case "05":
                Object writeValue = parseWriteValue(type,WriteValue);
                if(writeValue == null) {
                    throw new IllegalArgumentException("\"写05功能码的值只能是[0 或 1]");
                }
                protocol = ModbusTcpClientCommonProtocol.buildWrite05(
                        slaveAddress,start, (ModbusCoilStatus) writeValue); break;
            case "06":
                protocol = ModbusTcpClientCommonProtocol.buildWrite06(
                        slaveAddress,start, parseWriteValue(type,WriteValue)); break;
            case "10":
                byte[] writeBytes =  parseWriteValue(type,WriteValue);
                protocol = ModbusTcpClientCommonProtocol.buildWrite10(
                        slaveAddress,start, writeBytes.length / 2, writeBytes); break;
            case "0F":
                protocol = ModbusTcpClientCommonProtocol.buildWrite0F(
                        slaveAddress,start, (byte[]) parseWriteValue(type,WriteValue)); break;
            default: throw new IllegalArgumentException("不支持的指令["+cmd+"]");
        }
        return protocol;
    }
}
