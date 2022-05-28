package com.equilator.models.user;

public enum Permission {
    PERMISSION_GUEST("access:guest"),
    PERMISSION_USER("access:user"),
    PERMISSION_ADMIN("access:admin"),
    PERMISSION_SUPERADMIN("access:superadmin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
