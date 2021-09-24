package me.hhjeong.springbootcms.common.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RoleResources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_resource_id")
    private Long id;

    // 1:N에서 N쪽을 담당하고 있읍니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_role_resources_role"), nullable = false)
    private Role role;

    // 1:N에서 N쪽을 담당하고 있읍니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "fk_role_resources_resource"), nullable = false)
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
