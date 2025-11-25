package com.marti.domain.model;

public class Tag {
    private String  id;
    private String  name;
    private String  user_id;

    public Tag(String id, String name, String user_id) {
        this.id = id;
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
