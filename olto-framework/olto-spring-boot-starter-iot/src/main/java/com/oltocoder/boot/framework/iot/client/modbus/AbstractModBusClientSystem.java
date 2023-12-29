package com.oltocoder.boot.framework.iot.client.modbus;

import com.oltocoder.boot.framework.iot.client.AbstractClientSystem;
import com.oltocoder.boot.framework.iot.concurrent.ContextInternal;
import com.oltocoder.boot.framework.iot.core.consts.IotConsts;
import com.oltocoder.boot.framework.iot.dal.BaseEntity;
import com.iteaj.iot.client.protocol.ClientInitiativeProtocol;
import com.iteaj.iot.modbus.ModbusCommonProtocol;
import com.iteaj.iot.modbus.Payload;
import com.iteaj.iot.modbus.ReadPayload;
import com.iteaj.iot.modbus.RealCoilPayload;
import com.iteaj.iot.modbus.client.tcp.ModbusTcpClientCommonProtocol;
import com.iteaj.iot.modbus.consts.ModbusCoilStatus;
import com.iteaj.iot.utils.ByteUtil;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @title: ModbusTcpClientCollectAction
 * @Author cmw
 * @Date: 2023/8/8 16:51
 * @describe
 */
public abstract class AbstractModBusClientSystem<R extends BaseEntity,T> extends AbstractClientSystem<R,T> {

    protected AbstractModBusClientSystem(ContextInternal context) {
        super(context);
    }


    /**
     * 根据参数获取相应协议容器
     * @param cmd 指令
     * @param type 数据类型
     * @param slaveAddress 从站地址
     * @param start 读取开始位
     * @param WriteValue 需要写入的数据
     * @return
     */
    protected abstract ModbusCommonProtocol getModbusCommonProtocol(String cmd,Integer type,Integer slaveAddress,Integer start,String WriteValue) ;


    /**
     * 解析读取到的值
     * @param num 读取的字符串数量
     * @param type 字段类型
     * @param startAddress 寄存器地址
     * @param payload 读取到的负载
     * @return
     */
    protected Object parseReadResultValue(Integer num, Integer type, Integer startAddress, Payload payload) {
        Object value;
        switch (type) {
            case IotConsts.FIELD_TYPE_BOOLEAN:
                value = payload.readBoolean(startAddress); break;
            case IotConsts.FIELD_TYPE_SHORT:
                value = payload.readShort(startAddress); break;
            case IotConsts.FIELD_TYPE_INT:
                value = payload.readInt(startAddress); break;
            case IotConsts.FIELD_TYPE_FLOAT:
                value = payload.readFloat(startAddress); break;
            case IotConsts.FIELD_TYPE_DOUBLE:
                value = payload.readDouble(startAddress); break;
            case IotConsts.FIELD_TYPE_LONG:
                value = payload.readLong(startAddress); break;
            case IotConsts.FIELD_TYPE_STRING:
                value = payload.readString(startAddress, num); break;
            default:
                value = ByteUtil.bytesToHex(payload.getPayload()); break;
        }
        return value;
    }

    /**
     * 根据type获取相应的字节
     * @param type
     * @return
     */
    protected int getTypeCoilNum(int type){
        switch (type) {
            case IotConsts.FIELD_TYPE_BOOLEAN:
            case IotConsts.FIELD_TYPE_SHORT:
                return 1;
            case IotConsts.FIELD_TYPE_INT:
            case IotConsts.FIELD_TYPE_FLOAT:
                return 2;
            case IotConsts.FIELD_TYPE_DOUBLE:
            case IotConsts.FIELD_TYPE_LONG:
                return 4;
            default: throw new IllegalArgumentException("不支持的值类型["+type+"]");
        }
    }

    /**
     * 将发送的数据转成相应的字节
     * @param type
     * @param writeValue
     * @return
     */
    protected byte[] parseWriteValue(int type,String writeValue) {

        if(!StringUtils.hasText(writeValue)) {
            throw new IllegalArgumentException("未指定要写的值");
        }
        switch (type) {
            case IotConsts.FIELD_TYPE_BOOLEAN:
                return (writeValue.equals("0") ? ModbusCoilStatus.OFF.getCode() :
                        writeValue.equals("1") ? ModbusCoilStatus.ON.getCode() : null);
            case IotConsts.FIELD_TYPE_SHORT:
                return ByteUtil.getBytesOfReverse(Short.valueOf(writeValue));
            case IotConsts.FIELD_TYPE_INT:
                return ByteUtil.getBytes(Integer.valueOf(writeValue));
            case IotConsts.FIELD_TYPE_FLOAT:
                return ByteUtil.getBytes(Float.valueOf(writeValue));
            case IotConsts.FIELD_TYPE_DOUBLE:
                return ByteUtil.getBytes(Double.valueOf(writeValue));
            case IotConsts.FIELD_TYPE_LONG:
                return ByteUtil.getBytes(Long.valueOf(writeValue));
            default: throw new IllegalArgumentException("不支持的值类型["+type+"]");
        }
    }
}
