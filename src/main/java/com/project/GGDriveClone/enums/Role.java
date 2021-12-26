package com.project.GGDriveClone.enums;

public enum Role {

    ADMIN("ADMIN"), USER("USER");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
