package com.marti.domain.model;

import java.util.UUID;

public class Tag {
    private String  id;
    private String  name;
    private String  user_id;

    public Tag(String id, String name, String user_id) {
        this.id = id != null ? id: UUID.randomUUID().toString();
        this.name = name;
        this.user_id = user_id;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUser_id() {
        return user_id;
    }

}
