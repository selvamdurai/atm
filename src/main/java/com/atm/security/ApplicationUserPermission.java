package com.atm.security;

public enum ApplicationUserPermission {
    ACCOUNT_BALANCE("account:balance"),
    ACCOUNT_WITHDRAW("account:withdraw"),
    ACCOUNT_DEPOSIT("account:deposit"),
    ACCOUNT_RESET("reset:account"),
    ATM_RELOAD("atm:reload");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
