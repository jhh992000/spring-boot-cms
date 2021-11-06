package me.hhjeong.springbootcms.security.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.security.domain.RoleResources;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateRoleResourcesRequest {

    private Long roleId;
    private Long resourceId;

    public RoleResources toRoleResources() {
        return new RoleResources(roleId, resourceId);
    }
}
