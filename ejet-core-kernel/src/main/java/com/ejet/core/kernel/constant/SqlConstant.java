package com.ejet.core.kernel.constant;

/**
 *  sql常量
 */
public class SqlConstant {

    /**
     * 敏感sql操作
     */
    public static final String REG_SENSITIVE_SQL = "drop\\s|alter\\s|grant\\s|insert\\s|replace\\s|delete\\s|truncate\\s|update\\s|remove\\s";


    /**
     * 匹配多行sql注解正则
     */
    public static final String REG_SQL_ANNOTATE = "(?ms)('(?:''|[^'])*')|--.*?$|/\\*[^+]*?\\*/";

    /**
     * 统计总记录数
     */
    public static final String QUERY_COUNT_SQL = "SELECT COUNT(*) FROM (%s) CT";


}
