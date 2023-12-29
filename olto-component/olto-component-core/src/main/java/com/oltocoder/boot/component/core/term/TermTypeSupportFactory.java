package com.oltocoder.boot.component.core.term;

import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.term.support.TermTypeSupport;
import com.oltocoder.boot.component.core.term.support.impl.FixedTermTypeSupport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TermTypeSupportFactory {

    private static final Map<String, TermTypeSupport> supports = new LinkedHashMap<>();

    static {
        for (FixedTermTypeSupport value : FixedTermTypeSupport.values()) {
            register(value);
        }
    }

    public static void register(TermTypeSupport support){
        supports.put(support.getType(),support);
    }

    public static List<TermType> lookup(DataType dataType) {

        return supports
                .values()
                .stream()
                .filter(support -> support.isSupported(dataType))
                .map(TermTypeSupport::type)
                .collect(Collectors.toList());
    }
}
