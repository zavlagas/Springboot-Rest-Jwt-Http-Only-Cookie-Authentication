package com.zavlagas.spring.security.defence.constants;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Sets.newHashSet(Permission.READ)),
    ADMIN(Sets.newHashSet(Permission.CREATE, Permission.READ, Permission.UPDATE, Permission.DELETE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(this.name() + ":" + permission.getValue()))
                .collect(Collectors.toSet());
    }


}
