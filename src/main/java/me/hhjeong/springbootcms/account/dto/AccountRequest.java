package me.hhjeong.springbootcms.account.dto;

import javax.validation.constraints.NotEmpty;
import me.hhjeong.springbootcms.account.domain.Account;

public class AccountRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public AccountRequest() {
    }

    public AccountRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account toAccount() {
        return new Account(username, password);
    }
}
