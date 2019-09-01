package org.jnosql.artemis.demo.se;

import java.util.UUID;

public class MyEntity {

    private String id;

    private String name;


    @Deprecated
    public MyEntity() {
    }

    MyEntity(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
