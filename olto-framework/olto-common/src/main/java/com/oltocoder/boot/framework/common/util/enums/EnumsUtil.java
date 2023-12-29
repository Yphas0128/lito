package com.oltocoder.boot.framework.common.util.enums;

import cn.hutool.core.util.ClassUtil;
import com.oltocoder.boot.framework.common.util.enums.dto.EnumDTO;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class EnumsUtil {
    // 枚举类型包路径
    private static final String BASE_PACKAGE = "com.oltocoder.boot.framework.common.enums";
    public static  HashMap<String, List<EnumDTO>> getEnums(String type) throws Exception{
        Set<Class<?>> classes = ClassUtil.scanPackage(BASE_PACKAGE);
        HashMap<String, List<EnumDTO>> enums = new HashMap<>();

        for (Class<?> aClass : classes) {
            boolean equals ;
            if (type != null) {
                equals = aClass.getName().equals(BASE_PACKAGE + "." + type);
            }else {
                equals = true;
            }
            if (ClassUtil.isEnum(aClass) && equals ){
                String simpleName = aClass.getSimpleName();
                Class<Enum> em = (Class<Enum>)Class.forName(BASE_PACKAGE + "." + simpleName);
//                Class<Enum> em = (Class<Enum>) aClass;
//                Class<? extends Enum> cls = aClass.asSubclass(Enum.class);
//                Class<Enum> em = cls.getConstructor().newInstance().getDeclaringClass();

                // 枚举类里面必须是 code 和desc
                Method valueMethod = ClassUtil.getDeclaredMethod(aClass, "getValue");
                Method nameMethod = ClassUtil.getDeclaredMethod(aClass, "getName");
                if (valueMethod == null || nameMethod == null) {
                    continue;
                }
                //得到enum的所有实例
                Object[] objs = em.getEnumConstants();
                EnumDTO dto;
                List<EnumDTO> list = new ArrayList<>();
                for (Object obj : objs) {
                    dto = new EnumDTO();
                    dto.setValue(Integer.valueOf(String.valueOf(valueMethod.invoke(obj))));
                    dto.setName(String.valueOf(nameMethod.invoke(obj)));
                    list.add(dto);
                }
                enums.put(aClass.getName().replace(BASE_PACKAGE + ".", "").replace("$", "."), list);
            }
        }
        return enums;
    }


    public static  List<EnumDTO> getEnumList(String type) throws Exception{
        Set<Class<?>> classes = ClassUtil.scanPackage(BASE_PACKAGE);
        List<EnumDTO> enums = new ArrayList<>();

        for (Class<?> aClass : classes) {
            boolean equals ;
            if (type != null) {
                equals = aClass.getName().equals(BASE_PACKAGE + "." + type);
            }else {
                equals = true;
            }
            if (ClassUtil.isEnum(aClass) && equals) {
                String simpleName = aClass.getSimpleName();
                Class<Enum> em = (Class<Enum>)Class.forName(BASE_PACKAGE + "." + simpleName);
//                Class<? extends Enum> cls = aClass.asSubclass(Enum.class);
//                Class<Enum> em = cls.getConstructor(Integer.class, String.class).newInstance().getDeclaringClass();
                // 枚举类里面必须是 value 和 name
                Method valueMethod = ClassUtil.getDeclaredMethod(aClass, "getValue");
                Method nameMethod = ClassUtil.getDeclaredMethod(aClass, "getName");
                if (valueMethod == null || nameMethod == null) {
                    continue;
                }
                //得到enum的所有实例
                Object[] objs = em.getEnumConstants();
                EnumDTO dto;
                for (Object obj : objs) {
                    dto = new EnumDTO();
                    dto.setValue(Integer.valueOf(String.valueOf(valueMethod.invoke(obj))));
                    dto.setName(String.valueOf(nameMethod.invoke(obj)));
                    enums.add(dto);
                }
            }
        }
        return enums;
    }
}
