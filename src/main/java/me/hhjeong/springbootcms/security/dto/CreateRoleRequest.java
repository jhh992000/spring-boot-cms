package me.hhjeong.springbootcms.security.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.security.domain.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateRoleRequest {

    private String roleName;
    private String roleDesc;

    public Role toRole() {
        return new Role(roleName, roleDesc);
    }
}
