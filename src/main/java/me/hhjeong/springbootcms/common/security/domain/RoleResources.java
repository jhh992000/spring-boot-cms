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
import lombok.Getter;

@Entity
@Getter
public class RoleResources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_resource_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_role_resources_role"), nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "fk_role_resources_resource"), nullable = false)
    private Resources resources;

    protected RoleResources() {
    }

    public RoleResources(Long roleId, Long resourceId) {
        this.role = new Role(roleId);
        this.resources = new Resources(resourceId);
    }

    public RoleResources(Role role, Resources resources) {
        this.role = role;
        this.resources = resources;
    }
}
