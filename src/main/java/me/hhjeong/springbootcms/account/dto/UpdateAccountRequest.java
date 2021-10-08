package me.hhjeong.springbootcms.account.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.account.domain.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public Account toAccount() {
        return new Account(username, password);
    }

}
