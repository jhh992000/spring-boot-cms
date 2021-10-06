package me.hhjeong.springbootcms.common.security.metadatasource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import me.hhjeong.springbootcms.common.security.service.SecurityResourceService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 권한정보를 조회하는 클래스 (URL 방식의 인가 프로세스)
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * key : 요청URL, value : 권한리스트
     */
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap;
    private SecurityResourceService securityResourceService;

    /**
     * 생성자를 통해 매핑정보를 주입받는다.
     *
     * @param requestMap
     */
    public UrlFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap, SecurityResourceService securityResourceService) {
        this.requestMap = requestMap;
        this.securityResourceService = securityResourceService;
    }

    /**
     * 사용자가 접근하고자 하는 URL 자원에 대한 권한 정보 추출
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        if (requestMap != null) {
            HttpServletRequest request = ((FilterInvocation) object).getRequest();
            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
                RequestMatcher matcher = entry.getKey();

                //권한목록이 존재할 경우
                if (matcher.matches(request)) {
                    return entry.getValue(); // AccessDecisionManager 에 권한목록이 전달된다.
                }
            }
        }

        //요청URL에 대한 권한설정이 없는경우 권한심사를 하지 않는다. (접근허용)
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();

        for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void reload() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedMap = securityResourceService.getResourceList();
        Iterator<Entry<RequestMatcher, List<ConfigAttribute>>> iterator = reloadedMap.entrySet().iterator();

        requestMap.clear();

        while (iterator.hasNext()) {
            Entry<RequestMatcher, List<ConfigAttribute>> entry = iterator.next();
            requestMap.put(entry.getKey(), entry.getValue());
        }
    }

}