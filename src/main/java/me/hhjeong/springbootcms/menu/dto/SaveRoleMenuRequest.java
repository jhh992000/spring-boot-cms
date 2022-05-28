package me.hhjeong.springbootcms.menu.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.menu.domain.Menu;
import me.hhjeong.springbootcms.menu.domain.RoleMenu;
import me.hhjeong.springbootcms.security.domain.Role;
import me.hhjeong.springbootcms.site.domain.Site;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveRoleMenuRequest {

    @NotNull
    private Long menuId;

    public RoleMenu toRoleMenu(Long roleId, Long siteId) {
        return RoleMenu.builder()
                .role(new Role(roleId))
                .site(new Site(siteId))
                .menu(new Menu(menuId))
                .build();
    }

}
