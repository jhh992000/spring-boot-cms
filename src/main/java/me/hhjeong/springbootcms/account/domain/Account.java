package me.hhjeong.springbootcms.account.domain;

import lombok.Getter;
import me.hhjeong.springbootcms.common.domain.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), foreignKey = @ForeignKey(name = "fk_account_role"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private Set<AccountRole> roles;

    protected Account() {
    }

    public Account(String username, String password) {
        this(null, username, password);
    }

    public Account(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, Set<AccountRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public void update(Account account) {
        this.username = account.username;
        this.password = account.password;
    }
}
