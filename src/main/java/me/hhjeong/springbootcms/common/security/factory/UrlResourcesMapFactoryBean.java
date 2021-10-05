package me.hhjeong.springbootcms.common.security.factory;

import java.util.LinkedHashMap;
import java.util.List;
import me.hhjeong.springbootcms.common.security.service.SecurityResourceService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private SecurityResourceService securityResourceService;    // 직접 만든 클래스

    private LinkedHashMap<RequestMatcher,List<ConfigAttribute>> resourceMap;

    public void setSecurityResourceService(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
        if(resourceMap == null){
            resourceMap = securityResourceService.getResourceList();
        }
        return resourceMap;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    // 메모리에 단 하나만 존재할 수 있도록
    public boolean isSingleton() {
        return true;
    }
}
