package me.hhjeong.springbootcms.account.domain;

import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;
import me.hhjeong.springbootcms.common.domain.BaseEntity;

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
        this(null, username, password, null);
    }

    public Account(Long id, String username, String password, Set<AccountRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
