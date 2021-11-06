package me.hhjeong.springbootcms.security.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.transaction.Transactional;
import me.hhjeong.springbootcms.security.domain.Resources;
import me.hhjeong.springbootcms.security.domain.ResourcesRepository;
import me.hhjeong.springbootcms.security.domain.RoleResources;
import me.hhjeong.springbootcms.security.domain.RoleResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@Service
public class SecurityResourceService {

    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    public SecurityResourceService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    /**
     * DB에서 권한과 자원정보를 가져와서 맵으로 반환한다.
     * {요청URL:[권한리스트]}
     *
     * @return
     */
    @Transactional
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        List<Resources> resourcesList = resourcesRepository.findAll();
        List<RoleResources> roleResources = roleResourcesRepository.findAll();

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        resourcesList.forEach(resource -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            roleResources.forEach(roleResource -> {
                if (roleResource.getResources().getId() == resource.getId()) {
                    configAttributeList.add(new SecurityConfig(roleResource.getRole().getRoleName()));
                }
            });
            result.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributeList);
        });
        return result;
    }
}