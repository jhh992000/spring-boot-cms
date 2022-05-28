package me.hhjeong.springbootcms.security.provider;

import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountRepository;
import me.hhjeong.springbootcms.account.domain.AccountRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));

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
