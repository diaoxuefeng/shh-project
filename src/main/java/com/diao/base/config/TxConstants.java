package com.diao.base.config;

public interface TxConstants {
    String DEFAULT_TX_AOP_EXPRESSION = "(execution(* com.diao..impl..*.*(..)) && !@target(org.springframework.transaction.annotation.Transactional) && !@annotation(org.springframework.transaction.annotation.Transactional))";
    int DEFAULT_TX_AOP_ORDER = 2147483647;
    String READ_ONLY = "readOnly";
    String LEVEL_PROPAGATION_REQUIRED = "PROPAGATION_REQUIRED,-Exception";
    String LEVEL_PROPAGATION_SUPPORTS = "PROPAGATION_SUPPORTS,readOnly,-Exception";
    String FUNC_PREFIX_ALL = "*";
    String FUNC_PREFIX_SAVE = "save*";
    String FUNC_PREFIX_INSERT = "insert*";
    String FUNC_PREFIX_UPDATE = "update*";
    String FUNC_PREFIX_MODIFY = "modify*";
    String FUNC_PREFIX_REMOVE = "remove*";
    String FUNC_PREFIX_DELETE = "delete*";
    String FUNC_PREFIX_LOAD = "load*";
    String FUNC_PREFIX_LIST = "list*";
    String FUNC_PREFIX_GET = "get*";
    String FUNC_PREFIX_COUNT = "count*";
    String DEFAULT_DRUID_AOP_EXPRESSION = "(execution(* com.diao..impl..*.*(..))";
    int DEFAULT_DRUID_AOP_ORDER = 2147483647;
}
