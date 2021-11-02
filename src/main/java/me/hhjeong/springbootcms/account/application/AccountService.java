package me.hhjeong.springbootcms.account.application;

import static me.hhjeong.springbootcms.common.base.BaseConstants.PAGE_SIZE;
import static me.hhjeong.springbootcms.common.base.BaseConstants.START_PAGE_NO;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountRepository;
import me.hhjeong.springbootcms.account.dto.AccountResponse;
import me.hhjeong.springbootcms.account.dto.CreateAccountRequest;
import me.hhjeong.springbootcms.account.dto.UpdateAccountRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse createAccount(CreateAccountRequest request) {
        Account newAccount = makeNewAccount(request);
        return AccountResponse.of(accountRepository.save(newAccount));
    }

    private Account makeNewAccount(CreateAccountRequest request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        return new Account(request.getUsername(), encodePassword);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> findAccounts(Long lastAccountId) {
        Pageable pageable = PageRequest.of(START_PAGE_NO, PAGE_SIZE, Sort.by("id").descending());

        if (lastAccountId == null) {
            lastAccountId = accountRepository.findFirstByOrderByIdDesc()
                .map(Account::getId)
                .orElse(0L);
        }

        List<Account> accounts = accountRepository.findLatest(lastAccountId, pageable);

        return accounts.stream()
            .map(AccountResponse::of)
            .collect(Collectors.toList());
    }

    public Account findAccount(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(RuntimeException::new);
    }

    public Account replaceAccount(Long id, UpdateAccountRequest request) {
        return accountRepository.findById(id)
            .map(account -> {
                account.update(request.toAccount());
                return account;
            })
            .orElseGet(() -> {
                Account newAccount = request.toAccount(id);
                return accountRepository.save(newAccount);
            });
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
