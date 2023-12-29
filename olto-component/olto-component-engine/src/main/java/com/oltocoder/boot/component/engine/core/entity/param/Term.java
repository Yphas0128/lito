package com.oltocoder.boot.component.engine.core.entity.param;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Term implements Cloneable {

    /**
     * 字段名
     */
    private String column;

    /**
     * 条件值
     */
    private Object value;

    /**
     * 动态条件类型
     */
    private String termType = TermType.eq;

    /**
     * 嵌套条件
     */
    private Type type = Type.and;

    /**
     * 嵌套条件
     */
    private List<Term> terms = new LinkedList<>();

    /**
     * 拓展选项
     */
//    private List<String> options = new ArrayList<>();

    public Term or(String term, Object value) {
        return or(term, TermType.eq, value);
    }

    public Term and(String term, Object value) {
        return and(term, TermType.eq, value);
    }

    private Term or(String term, String termType, Object value) {
        Term queryTerm = new Term();
        queryTerm.setTermType(termType);
        queryTerm.setColumn(term);
        queryTerm.setValue(value);
        queryTerm.setType(Type.or);
        return this;
    }

    private Term and(String term, String termType, Object value) {
        Term queryTerm = new Term();
        queryTerm.setTermType(termType);
        queryTerm.setColumn(term);
        queryTerm.setValue(value);
        queryTerm.setType(Type.and);
        return this;
    }

    public enum Type {
        or, and;
    }


    @Override
    @SneakyThrows
    public Term clone() {
        Term term = ((Term) super.clone());
        term.setColumn(column);
        term.setValue(value);
        term.setTermType(termType);
        term.setType(type);
        term.setTerms(terms.stream().map(Term::clone).collect(Collectors.toList()));
//        term.setOptions(new ArrayList<>(getOptions()));
        return term;
    }
}
