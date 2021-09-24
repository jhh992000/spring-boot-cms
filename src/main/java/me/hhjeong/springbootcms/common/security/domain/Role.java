package me.hhjeong.springbootcms.common.security.domain;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<RoleResources> roleResources = new LinkedHashSet<>();

    protected Role() {
    }

    public Role(Long id, String roleName, String roleDesc, Set<RoleResources> roleResources) {
        this.id = id;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.roleResources = roleResources;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public Set<RoleResources> getRoleResources() {
        return roleResources;
    }
}
