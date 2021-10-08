package me.hhjeong.springbootcms.account.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import me.hhjeong.springbootcms.account.application.AccountService;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.dto.AccountResponse;
import me.hhjeong.springbootcms.account.dto.CreateAccountRequest;
import me.hhjeong.springbootcms.account.dto.UpdateAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    public static final String NO_AUTHENTICATION_IN_SECURITY_CONTEXT_FOUND = "no authentication in security context found";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid CreateAccountRequest request) {
        AccountResponse account = accountService.createAccount(request);
        return ResponseEntity.created(URI.create("/api/account/" + account.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> list(@RequestParam(name = "id", required = false) Long id) {
        List<AccountResponse> accounts = accountService.findAccounts(id);
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findAccountById(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(AccountResponse.of(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateAccount(id, request);
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(AccountResponse.of(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
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
