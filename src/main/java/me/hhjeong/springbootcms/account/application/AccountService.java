package me.hhjeong.springbootcms.account.application;

import java.util.List;
import java.util.stream.Collectors;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountRepository;
import me.hhjeong.springbootcms.account.dto.AccountResponse;
import me.hhjeong.springbootcms.account.dto.CreateAccountRequest;
import me.hhjeong.springbootcms.account.dto.UpdateAccountRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    public static final int START_PAGE_NO = 0;
    public static final int LATEST_ACCOUNT_SIZE = 5;

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = accountRepository.save(request.toAccount());
        return AccountResponse.of(account);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> findAccounts(Long lastAccountId) {
        Pageable pageable = PageRequest.of(START_PAGE_NO, LATEST_ACCOUNT_SIZE, Sort.by("id").descending());

        if (lastAccountId == null) {
            lastAccountId = accountRepository.findFirstByOrderByIdDesc()
                .map(Account::getId)
                .orElse(0L);
        }

        List<Account> accounts = accountRepository.findLatest(lastAccountId, pageable);

        return accounts.stream()
            .map(account -> AccountResponse.of(account))
            .collect(Collectors.toList());
    }

    public void updateAccount(Long id, UpdateAccountRequest request) {
        Account account = accountRepository.findById(id)
            .orElseThrow(RuntimeException::new);

        account.update(request.toAccount());
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(RuntimeException::new);
    }
}
