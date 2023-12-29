package com.oltocoder.boot.component.engine.core.internal.actions.impl;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.component.core.message.property.ReadPropertyMessage;
import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.metadata.PropertyMetadata;
import com.oltocoder.boot.component.core.metadata.ThingMetadata;
import com.oltocoder.boot.component.core.metadata.types.BooleanType;
import com.oltocoder.boot.component.core.metadata.types.StringType;
import com.oltocoder.boot.component.core.term.TermTypeSupportFactory;
import com.oltocoder.boot.component.engine.core.entity.selector.SelectorValue;
import com.oltocoder.boot.component.engine.core.entity.value.Variable;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.framework.common.util.collection.CollectionUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class DeviceAction {

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 设备指令 转换成 Message
     */
    private Map<String, Object> message;

    /**
     *选择器
     */
    private List<SelectorValue> selectorValues;

    public List<String> parseColumns() {

        if (CollUtil.isEmpty(message)) {
            return Collections.emptyList();
        }

//      DeviceMessage msg = (DeviceMessage) MessageType.convertMessage(message).orElse(null);
        Collection<Object> readyToParse = Collections.emptyList() ;

        return readyToParse.stream().flatMap(val -> parseColumnFromOptions(VariableSource.of(val).getOptions()).stream()).collect(Collectors.toList());
    }

    private List<String> parseColumnFromOptions(Map<String, Object> options) {
        Object columns;
        if (CollUtil.isEmpty(options) || (columns = options.get("columns")) == null) {
            return Collections.emptyList();
        }

        //获取前端设置的columns
        return CollectionUtils.convertToList(columns, String::valueOf);
    }


    public List<Variable> createVariables(ThingMetadata metadata) {

        List<Variable> variables = new ArrayList<>();
        //下发指令是否成功
        variables.add(Variable
                .of("success", "action_execute_success")
                .withType(BooleanType.Identifier)
                .withOption("bool", BooleanType.GLOBAL)
                .withTermType(TermTypeSupportFactory.lookup(BooleanType.GLOBAL))
        );

        //设备ID
        variables.add(Variable
                .of("deviceId", "device_id")
                .withType(StringType.Identifier)
                //标识变量属于哪个产品
                .withOption(Variable.OPTION_PRODUCT_ID, productId)
                .withTermType(TermTypeSupportFactory.lookup(StringType.GLOBAL))
        );

        // 读取属性
        if (message instanceof ReadPropertyMessage) {
            List<String> properties = ((ReadPropertyMessage) message).getProperties();

            for (String property : properties) {
                PropertyMetadata metadata_ = metadata.getPropertyOrNull(property);
                if (null != metadata_) {
                    variables.add(toVariable("properties", metadata_));
                }
            }
        }
        return variables;
    }

    public static Variable toVariable(String prefix,
                                      PropertyMetadata metadata) {
        return toVariable(prefix.concat(".").concat(metadata.getIdentifier()),
                metadata.getName(),
                metadata.getValueType()
        );
    }

    private static Variable toVariable(String id, String metadataName, DataType dataType) {

        Variable variable = Variable.of(id, metadataName);
        variable.setType(dataType.getType());
        variable.setTermTypes(TermTypeSupportFactory.lookup(dataType));

        variable.setColumn(id);

        // object
        return variable;
    }

}
