package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.*;

@NodeEntity
public class Choice {
    @Id
    @GeneratedValue
    private Long id;

    @Property@Index(unique = true)
    private String key;

    @Property
    private String value;

    public Choice() {
    }

    public Choice(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
