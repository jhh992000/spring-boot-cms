package me.hhjeong.springbootcms.common.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RoleResources {

    @Id
    @GeneratedValue
    @Column(name = "role_resource_id")
    private Long id;

    // 1:N에서 N쪽을 담당하고 있읍니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    // 1:N에서 N쪽을 담당하고 있읍니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resources resources;

    protected RoleResources() {
    }

    public RoleResources(Long id, Role role, Resources resources) {
        this.id = id;
        this.role = role;
        this.resources = resources;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public Resources getResources() {
        return resources;
    }
}
