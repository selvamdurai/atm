package com.atm.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.atm.security.ApplicationUserPermission.*;



public enum ApplicationUserRole {
    ACCOUNT_USER(Sets.newHashSet(ACCOUNT_BALANCE,ACCOUNT_WITHDRAW)),
    ADMIN_USER(Sets.newHashSet(ACCOUNT_RESET,ATM_RELOAD));


    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
