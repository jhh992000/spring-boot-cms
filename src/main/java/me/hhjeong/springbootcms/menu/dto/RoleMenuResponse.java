package me.hhjeong.springbootcms.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.common.domain.BaseEntity;
import me.hhjeong.springbootcms.menu.domain.RoleMenu;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleMenuResponse extends BaseEntity {

    private Long id;

    private Long roleId;

    private Long siteId;

    private Long menuId;

    public static RoleMenuResponse of(RoleMenu roleMenu) {
        return RoleMenuResponse.builder()
                .id(roleMenu.getId())
                .roleId(roleMenu.getRole().getId())
                .siteId(roleMenu.getSite().getId())
                .menuId(roleMenu.getMenu().getId())
                .build();
    }

    public static List<RoleMenuResponse> ofList(List<RoleMenu> roleMenus) {
        return roleMenus.stream()
                .map(RoleMenuResponse::of)
                .collect(Collectors.toList());
    }
}
