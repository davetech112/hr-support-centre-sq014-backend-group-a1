package com.hrsupportcentresq014.hrsupportcentresq014.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    HR_READ("hr:read"),
    HR_UPDATE("hr:update"),
    HR_CREATE("hr:create"),
    HR_DELETE("hr:delete")

    ;

    @Getter
    private final String permission;
}

