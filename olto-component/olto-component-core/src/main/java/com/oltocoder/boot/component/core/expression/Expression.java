package com.oltocoder.boot.component.core.expression;

import com.oltocoder.boot.component.core.expression.comparator.IComparator;
import com.oltocoder.boot.component.core.expression.comparator.impl.GtComparator;
import com.oltocoder.boot.component.core.expression.comparator.impl.LtComparator;
import com.oltocoder.boot.component.core.expression.comparator.impl.EqComparator;
import com.oltocoder.boot.component.core.expression.comparator.impl.NeqComparator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.script.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// js表达式
@Slf4j
public class Expression {
    private static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

    private static final Map<String, CompiledScript> compiledScriptMap = new ConcurrentHashMap<>();

    private static final Map<String, IComparator> comparatorMap = new HashMap<>();

    static {
        addComparator(new GtComparator());
        addComparator(new LtComparator());
        addComparator(new EqComparator());
        addComparator(new NeqComparator());
    }

    private static void addComparator(IComparator comparator) {
        comparatorMap.put(comparator.getName(), comparator);
    }

    public static boolean eval(String name, Object left, Object right) {
        IComparator comp = comparatorMap.get(name);
        if (comp == null) {
            return false;
        }
        return eval(comp.getScript(), comp.getData(left, right));
    }


    public static IComparator getComparator(String name){
      return comparatorMap.get(name);
    }

    /**
     * CompiledScript可以认为是对script解析一个缓存
     * @param script
     * @return
     */
    @SneakyThrows
    private static CompiledScript create(String script) {
        return ((Compilable)scriptEngine).compile(script);
    }

    private static boolean eval(String script, Map<String, Object> data) {
        compiledScriptMap.putIfAbsent(script, create(script));
        CompiledScript compiledScript = compiledScriptMap.get(script);
        try {
            Bindings bindings = new SimpleBindings(data);
            Object result = compiledScript.eval(bindings);
            if (result instanceof Boolean) {
                return (boolean) result;
            }
            return false;
        } catch (ScriptException e) {
//            log.error("eval expression error", e);

        }
        return false;

    }
}
