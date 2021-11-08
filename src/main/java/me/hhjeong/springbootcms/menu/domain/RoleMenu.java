package me.hhjeong.springbootcms.menu.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.hhjeong.springbootcms.common.domain.BaseEntity;
import me.hhjeong.springbootcms.security.domain.Role;
import me.hhjeong.springbootcms.site.domain.Site;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
public class RoleMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_menu_role"), nullable = false)
    @ToString.Exclude
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_menu_site"), nullable = false)
    @ToString.Exclude
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_menu_menu"), nullable = false)
    @ToString.Exclude
    private Menu menu;

    public RoleMenu(Long roleId, Long siteId, Long menuId) {
        this.role = new Role(roleId);
        this.site = new Site(siteId);
        this.menu = new Menu(menuId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleMenu roleMenu = (RoleMenu) o;
        return Objects.equals(role, roleMenu.role) && Objects.equals(site, roleMenu.site) && Objects.equals(menu, roleMenu.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, site, menu);
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
            "id=" + id +
            ", role=" + role.getId() +
            ", site=" + site.getId() +
            ", menu=" + menu.getId() +
            '}';
    }
}
