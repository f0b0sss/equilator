package com.equilator.model;

public enum Permission {
    PERMISSION_GUEST("access:guest"),
    PERMISSION_USER("access:user"),
    PERMISSION_ADMIN("access:admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
