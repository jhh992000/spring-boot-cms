package me.hhjeong.springbootcms.security.factory;

import java.util.LinkedHashMap;
import java.util.List;
import me.hhjeong.springbootcms.security.service.SecurityResourceService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

    private SecurityResourceService securityResourceService;

    public UrlResourcesMapFactoryBean(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() {
        if (resourceMap == null) {
            resourceMap = securityResourceService.getResourceList();
        }
        return resourceMap;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
