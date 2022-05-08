package com.equilator.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    GUEST(Set.of(Permission.PERMISSION_GUEST)),
    USER(Set.of(Permission.PERMISSION_GUEST, Permission.PERMISSION_USER)),
    ADMIN(Set.of(Permission.PERMISSION_GUEST, Permission.PERMISSION_USER, Permission.PERMISSION_ADMIN));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
