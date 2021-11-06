package me.hhjeong.springbootcms.security.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.security.domain.Resources;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateResourcesRequest {

    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;

    public Resources toResources() {
        return new Resources(resourceName, httpMethod, orderNum, resourceType);
    }
}
