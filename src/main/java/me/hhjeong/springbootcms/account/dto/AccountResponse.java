package me.hhjeong.springbootcms.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.account.domain.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private String usename;

    public static AccountResponse of(Account account) {
        return new AccountResponse(account.getId(), account.getUsername());
    }
}
