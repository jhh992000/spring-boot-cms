package me.hhjeong.springbootcms.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.account.domain.AccountId;
import me.hhjeong.springbootcms.account.domain.AccountPassword;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @AccountId
    @NotEmpty
    private String username;

    @AccountPassword
    @NotEmpty
    private String password;
}
