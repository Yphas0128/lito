package com.oltocoder.boot.module.iot.service.collectorjob.dto;

import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JobDeviceDTO implements Serializable {

    /**
     * 设备主键
     */
    private Long id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备编号
     */
    private String code;

    /**
     * 组件名称
     */
    private String componentName;

    /**
     * 设备ip
     */
    private String host;

    /**
     * 设备端口
     */
    private Integer port;

    /**
     * 串口
     */
    private String serialCom;

    /**
     * 串口-波特率
     */
    private Integer baudRate;

    /**
     * 串口-数据位
     */
    private Integer dataBits;

    /**
     * 串口-校验位
     */
    private Integer parityBits;

    /**
     * 串口-停止位
     */
    private Integer stopBits;

    /**
     * 具体点位信息
     */
    private List<JobPointDTO> points;

    public void setPoints(List<PointDO> pointDOS){
        this.points = pointDOS.stream().map(pointDO -> {
            JobPointDTO jobPointDTO = new JobPointDTO();
            jobPointDTO.setId(pointDO.getId());
            jobPointDTO.setName(pointDO.getName());
            jobPointDTO.setModbusType(pointDO.getModbusType());
            jobPointDTO.setFieldType(pointDO.getFieldType());
            jobPointDTO.setAddress(pointDO.getAddress());
            jobPointDTO.setNum(pointDO.getNum());
            jobPointDTO.setMessage(pointDO.getMessage());
            jobPointDTO.setProperty(pointDO.getProperty());
            return jobPointDTO;
        }).collect(Collectors.toList());

    }
    
    @Data
    public static class JobPointDTO implements Serializable{
        /**
         * id
         */
        @TableId
        private Long id;
        /**
         * 名称
         */
        private String name;

        /**
         * 信号类型
         */
        private Integer modbusType;

        /**
         * 字段类型
         */
        private Integer fieldType;

        /**
         * modbus地址
         */
        private String address;

        /**
         * 读取的寄存器(点位)数量
         */
        private Integer num;

        /**
         * 自定义报文
         */
        private String message;

        /**
         * 点位字段属性
         */
        private String property;
    }
}
