package com.oltocoder.boot.component.engine.core.internal;

import com.oltocoder.boot.component.engine.core.internal.triggers.ITriggerConfig;
import com.oltocoder.boot.component.engine.core.internal.actions.SceneActionProvider;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.AlarmActionProvider;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.DelayActionProvider;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.DeviceActionProvider;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.NotifyActionProvider;
import com.oltocoder.boot.component.engine.core.internal.triggers.SceneTriggerProvider;
import com.oltocoder.boot.component.engine.core.internal.triggers.impl.DeviceTriggerProvider;
import com.oltocoder.boot.component.engine.core.internal.triggers.impl.ManualTriggerProvider;
import com.oltocoder.boot.component.engine.core.internal.triggers.impl.TimerTriggerProvider;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: 场景供应工厂
 * @Author Ypier
 * @Date: 2023/9/2 13:37
 */
public class SceneProviderFactory {

    private static final Map<String, SceneActionProvider<?>> actionProviders = new ConcurrentHashMap<>();
    private static final Map<String, SceneTriggerProvider<?>> triggerProviders = new ConcurrentHashMap<>();

    static {
        register(new DeviceActionProvider());
        register(new DelayActionProvider());
        register(new AlarmActionProvider());
        register(new NotifyActionProvider());

        register(new DeviceTriggerProvider());
        register(new TimerTriggerProvider());
        register(new ManualTriggerProvider());
    }

    public static void register(SceneActionProvider<?> provider) {
        actionProviders.put(provider.getProvider(), provider);
    }

    public static void register(SceneTriggerProvider<?> provider) {
        triggerProviders.put(provider.getProvider(), provider);
    }

    @SuppressWarnings("all")
    public static <C> Optional<SceneActionProvider<C>> getActionProvider(String provider) {
        return Optional.ofNullable((SceneActionProvider<C>) actionProviders.get(provider));
    }

    public static <C> SceneActionProvider<C> getActionProviderNow(String provider) {
        return SceneProviderFactory
                .<C>getActionProvider(provider)
                .orElseThrow(() -> new UnsupportedOperationException("unsupported SceneActionProvider:" + provider));
    }

    @SuppressWarnings("all")
    public static <C extends ITriggerConfig> Optional<SceneTriggerProvider<C>> getTriggerProvider(String provider) {
        return Optional.ofNullable((SceneTriggerProvider<C>) triggerProviders.get(provider));
    }

    public static <C extends ITriggerConfig> SceneTriggerProvider<C> getTriggerProviderNow(String provider) {
        return SceneProviderFactory
                .<C>getTriggerProvider(provider)
                .orElseThrow(() -> new UnsupportedOperationException("unsupported SceneTriggerProvider:" + provider));
    }

}
