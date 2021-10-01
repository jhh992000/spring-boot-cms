package me.hhjeong.springbootcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCmsApplication.class, args);
    }

/*

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
*/


}
