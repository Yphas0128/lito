package com.oltocoder.boot.component.engine.core.entity.param;

/**
 * @title: TermType
 * @Author Ypier
 * @Date: 2023/9/2 14:40
 */
public interface TermType {
    /**
     * ==
     *
     * @since 1.0
     */
    String eq      = "eq";
    /**
     * !=
     *
     * @since 1.0
     */
    String not     = "not";
    /**
     * like
     *
     * @since 1.0
     */
    String like    = "like";
    /**
     * not like
     *
     * @since 1.0
     */
    String nlike   = "nlike";
    /**
     * >
     *
     * @since 1.0
     */
    String gt      = "gt";
    /**
     * <
     *
     * @since 1.0
     */
    String lt      = "lt";
    /**
     * >=
     *
     * @since 1.0
     */
    String gte     = "gte";
    /**
     * <=
     *
     * @since 1.0
     */
    String lte     = "lte";
    /**
     * in
     *
     * @since 1.0
     */
    String in      = "in";
    /**
     * notin
     *
     * @since 1.0
     */
    String nin     = "nin";
    /**
     * =''
     *
     * @since 1.0
     */
    String empty   = "empty";
    /**
     * !=''
     *
     * @since 1.0
     */
    String nempty  = "nempty";
    /**
     * is null
     *
     * @since 1.0
     */
    String isnull  = "isnull";
    /**
     * not null
     *
     * @since 1.0
     */
    String notnull = "notnull";
    /**
     * between
     *
     * @since 1.0
     */
    String btw     = "btw";
    /**
     * not between
     *
     * @since 1.0
     */
    String nbtw    = "nbtw";

}
