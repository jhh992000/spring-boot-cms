package me.hhjeong.springbootcms.security.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class RoleResources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_resource_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_resources_role"), nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_resources_resource"), nullable = false)
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
