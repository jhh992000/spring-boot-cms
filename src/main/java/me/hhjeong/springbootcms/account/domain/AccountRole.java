package me.hhjeong.springbootcms.account.domain;

public enum AccountRole {
    ADMIN("ROLE_ADMIN"), MANAGER("ROLE_MANAGER"), USER("ROLE_USER");

    private String roleName;

    AccountRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
