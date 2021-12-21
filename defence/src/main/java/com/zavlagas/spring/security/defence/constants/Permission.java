package com.zavlagas.spring.security.defence.constants;

public enum Permission {

    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String value;

    Permission(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
