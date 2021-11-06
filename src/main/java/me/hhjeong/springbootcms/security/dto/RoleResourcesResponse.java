package me.hhjeong.springbootcms.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.security.domain.RoleResources;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResourcesResponse {

    private Long id;
    private Long roleId;
    private Long resourceId;

    public static RoleResourcesResponse of(RoleResources roleResources) {
        return RoleResourcesResponse.builder()
            .id(roleResources.getId())
            .roleId(roleResources.getRole().getId())
            .resourceId(roleResources.getResources().getId())
            .build();
    }

}
