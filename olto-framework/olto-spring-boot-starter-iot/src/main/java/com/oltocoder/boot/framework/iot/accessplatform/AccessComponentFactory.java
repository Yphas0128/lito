package com.oltocoder.boot.framework.iot.accessplatform;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AccessComponentFactory {

    private final String COMPONENT_BASEPACKAGE="com.oltocoder.boot.framework.iot.accessplatform";

    private Map<String, Class<?>> accessNameAndComponentClassMap;

    private Map<String, AccessComponent> uniqueIdAndAccessComponentMap;

    public AccessComponentFactory() {
        this.uniqueIdAndAccessComponentMap = new HashMap<>();
        this.accessNameAndComponentClassMap = new HashMap<>();

    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider provider=new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AccessComponent.class));
        Set<BeanDefinition> components=provider.findCandidateComponents(COMPONENT_BASEPACKAGE);
        for(BeanDefinition component : components){
            Class cls=Class.forName(component.getBeanClassName());
            this.accessNameAndComponentClassMap.put(cls.getSimpleName().toLowerCase(), cls);
        }
    }

    public synchronized AccessComponent getComponent(String uniqueId, String accessComponentName) {
        if (uniqueId == null || uniqueId.isEmpty())
            throw new RuntimeException("uniqueId参数不能为空");
        if (accessComponentName == null || accessComponentName.isEmpty())
            throw new RuntimeException("accessComponentName参数不能为空");

        if (uniqueIdAndAccessComponentMap.containsKey(uniqueId))
            return uniqueIdAndAccessComponentMap.get(uniqueId);
        else {
            if (!accessNameAndComponentClassMap.containsKey(accessComponentName))
                throw new RuntimeException("AccessComponent[Name=" + accessComponentName + "]无法找到");

            try {
                Class<?> componentClass = accessNameAndComponentClassMap.get(accessComponentName);
                AccessComponent componentInst = (AccessComponent) componentClass.getConstructor().newInstance();
                uniqueIdAndAccessComponentMap.put(uniqueId, componentInst);
                return componentInst;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
