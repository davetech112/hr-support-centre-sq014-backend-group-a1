package com.hrsupportcentresq014.hrsupportcentresq014.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hrsupportcentresq014.hrsupportcentresq014.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_DELETE,
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    ADMIN_CREATE,
                    HR_CREATE,
                    HR_DELETE,
                    HR_UPDATE,
                    HR_READ
            )
    ),
    HR(
            Set.of(
                    HR_CREATE,
                    HR_DELETE,
                    HR_UPDATE,
                    HR_READ
            )
    ),
    STAFF(Collections.emptySet());


    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
