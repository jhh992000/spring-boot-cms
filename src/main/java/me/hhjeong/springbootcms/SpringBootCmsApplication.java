package me.hhjeong.springbootcms;

import java.util.HashSet;
import java.util.Set;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountRepository;
import me.hhjeong.springbootcms.account.domain.AccountRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBootCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCmsApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            Set<AccountRole> roles = new HashSet<>();
            //roles.add(AccountRole.ADMIN);
            roles.add(AccountRole.USER);

            Account account = new Account(1L, "jhh992000@gmail.com", passwordEncoder.encode("1234"), roles);
            accountRepository.save(account);
        };
    }


}
