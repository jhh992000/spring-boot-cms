package me.hhjeong.springbootcms.common.security.provider;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountRepository;
import me.hhjeong.springbootcms.account.domain.AccountRole;
import me.hhjeong.springbootcms.account.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
            .orElseThrow(AccountNotFoundException::new);

        return User.withUsername(username)
            .password(account.getPassword())
            .authorities(parseAuthorities(account.getRoles()))
            .build();
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(Set<AccountRole> accountRoles) {
        return accountRoles.stream()
            .map(accountRole -> new SimpleGrantedAuthority(accountRole.getRoleName()))
            .collect(Collectors.toList());
    }

}
