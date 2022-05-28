package me.hhjeong.springbootcms.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountId;
import me.hhjeong.springbootcms.account.domain.AccountPassword;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {

    @AccountId
    @NotEmpty
    private String username;

    @AccountPassword
    @NotEmpty
    private String password;

    public Account toAccount() {
        return new Account(username, password);
    }

    public Account toAccount(Long id) {
        return new Account(id, username, password);
    }

}
