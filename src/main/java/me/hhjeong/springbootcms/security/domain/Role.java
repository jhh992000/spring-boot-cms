package me.hhjeong.springbootcms.security.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import me.hhjeong.springbootcms.common.domain.BaseEntity;

@Entity
@Getter
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @Column(name = "list_order", nullable = false)
    private Long listOrder;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<RoleResources> roleResources = new LinkedHashSet<>();

    protected Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String roleName, String roleDesc, Long listOrder) {
        this(null, roleName, roleDesc, listOrder);
    }

    public Role(Long id, String roleName, String roleDesc, Long listOrder) {
        this.id = id;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.listOrder = listOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(Role role) {
        this.roleName = role.getRoleName();
        this.roleDesc = role.getRoleDesc();
        this.listOrder = role.getListOrder();
    }
}
