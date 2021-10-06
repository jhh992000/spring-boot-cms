package me.hhjeong.springbootcms.account.application;

import javax.transaction.Transactional;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountRepository;
import me.hhjeong.springbootcms.account.dto.AccountRequest;
import me.hhjeong.springbootcms.account.dto.AccountResponse;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public AccountResponse createAccount(AccountRequest request) {
        Account account = accountRepository.save(request.toAccount());
        return AccountResponse.of(account);
    }
}
