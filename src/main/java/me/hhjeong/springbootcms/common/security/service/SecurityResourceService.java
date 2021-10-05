package me.hhjeong.springbootcms.common.security.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.transaction.Transactional;
import me.hhjeong.springbootcms.common.security.domain.Resources;
import me.hhjeong.springbootcms.common.security.domain.ResourcesRepository;
import me.hhjeong.springbootcms.common.security.domain.RoleResources;
import me.hhjeong.springbootcms.common.security.domain.RoleResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SecurityResourceService {

    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    public SecurityResourceService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList(){

        // DB로부터 권한과 자원정보를 가져온다.
        LinkedHashMap<RequestMatcher,List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAll();  // Resources 데이터 전부 가져오기
        List<RoleResources> roleResources = roleResourcesRepository.findAll();  // RoleResources 데이터 전부 가져오기
        resourcesList.forEach(resource ->{  // For문 돌면서~
            List<ConfigAttribute> configAttributeList = new ArrayList<>();  // 권한정보 저장 리스트 생성해주고 여기에 담을꺼다.
            // 자원에 해당되는 권한 목록을 가져온다.
            Long resourceId = resource.getId();   // for문 내부에서 도는 요소. resourceId.
            roleResources.forEach(roleResource ->{  // 이중 For문 시작!
                if(roleResource.getResources().getId() == resourceId)   // Resource의 아이디와 roleResource에서 뽑은 아이디가 같다면
                    configAttributeList.add(new SecurityConfig(roleResource.getRole().getRoleName()));  // 권한정보 이름 더하기!
            });
            result.put(new AntPathRequestMatcher(resource.getResourceName()),configAttributeList);  // 해시로 {이름:[권한들]} 매핑해주기
        });
        return result;
    }
}