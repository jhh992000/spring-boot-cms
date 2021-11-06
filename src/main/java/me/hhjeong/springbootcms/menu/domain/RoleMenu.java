package me.hhjeong.springbootcms.menu.domain;

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
import lombok.EqualsAndHashCode;
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
@ToString(callSuper = true)
@EqualsAndHashCode
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

}
