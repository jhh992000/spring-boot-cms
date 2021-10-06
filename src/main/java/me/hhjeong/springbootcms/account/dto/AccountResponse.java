package me.hhjeong.springbootcms.account.dto;

import me.hhjeong.springbootcms.account.domain.Account;

public class AccountResponse {

    private Long id;
    private String usename;

    public AccountResponse(Long id, String usename) {
        this.id = id;
        this.usename = usename;
    }

    public static AccountResponse of(Account account) {
        return new AccountResponse(account.getId(), account.getUsername());
    }

    public Long getId() {
        return id;
    }

    public String getUsename() {
        return usename;
    }
}
