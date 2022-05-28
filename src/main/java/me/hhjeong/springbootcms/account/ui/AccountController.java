package me.hhjeong.springbootcms.account.ui;

import me.hhjeong.springbootcms.account.application.AccountService;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.dto.AccountResponse;
import me.hhjeong.springbootcms.account.dto.CreateAccountRequest;
import me.hhjeong.springbootcms.account.dto.UpdateAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    public static final String NO_AUTHENTICATION_IN_SECURITY_CONTEXT_FOUND = "no authentication in security context found";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest request) {
        AccountResponse account = accountService.createAccount(request);
        return ResponseEntity.created(URI.create("/api/account/" + account.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAccounts(@RequestParam(name = "id", required = false) Long lastId) {
        List<AccountResponse> accounts = accountService.findAccounts(lastId);
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findAccount(@PathVariable Long id) {
        Account account = accountService.findAccount(id);
        return ResponseEntity.ok().body(AccountResponse.of(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> replaceAccount(@PathVariable Long id, @RequestBody @Valid UpdateAccountRequest request) {
        Account account = accountService.replaceAccount(id, request);
        return ResponseEntity.ok().body(AccountResponse.of(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myinfo")
    public String myinfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return NO_AUTHENTICATION_IN_SECURITY_CONTEXT_FOUND;
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        }

        if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return NO_AUTHENTICATION_IN_SECURITY_CONTEXT_FOUND;
    }

    @GetMapping("/user-feature")
    public String user_feature() {
        return "this is user feature.";
    }

    @GetMapping("/admin-feature")
    public String admin_feature() {
        return "this is admin feature.";
    }

}
