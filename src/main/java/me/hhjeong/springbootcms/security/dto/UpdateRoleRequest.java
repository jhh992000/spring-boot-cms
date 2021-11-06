package me.hhjeong.springbootcms.security.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.security.domain.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateRoleRequest {

    private String roleName;
    private String roleDesc;
    private Long listOrder;

    public Role toRole() {
        return new Role(roleName, roleDesc, listOrder);
    }

    public Role toRole(Long id) {
        return new Role(id, roleName, roleDesc, listOrder);
    }
}
