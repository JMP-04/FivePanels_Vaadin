package com.fivepanels.application.service;

import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String email;
    private String created;
    private String updated;

    public UUID getID() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
