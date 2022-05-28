package me.hhjeong.springbootcms.menu.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hhjeong.springbootcms.menu.domain.RoleMenu;
import me.hhjeong.springbootcms.menu.domain.RoleMenuRepository;
import me.hhjeong.springbootcms.menu.dto.RoleMenuResponse;
import me.hhjeong.springbootcms.menu.dto.SaveRoleMenuRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleMenuService {

    private final RoleMenuRepository roleMenuRepository;

    public List<RoleMenuResponse> saveRoleMenus(Long roleId, Long siteId, List<SaveRoleMenuRequest> requests) {
        List<RoleMenu> roleMenus = roleMenuRepository.findByRoleIdAndSiteId(roleId, siteId);

        List<RoleMenu> requestRoleMenus = requests.stream()
                .map(req -> req.toRoleMenu(roleId, siteId))
                .collect(Collectors.toList());

        mergeRoleMenus(roleMenus, requestRoleMenus);

        roleMenuRepository.saveAll(roleMenus);

        return RoleMenuResponse.ofList(roleMenus);
    }

    protected void mergeRoleMenus(List<RoleMenu> roleMenus, List<RoleMenu> requestRoleMenus) {
        requestRoleMenus.removeAll(roleMenus);
        roleMenus.addAll(requestRoleMenus);
    }

    public void deleteByRoleIdAndSiteId(Long roleId, Long siteId) {
        roleMenuRepository.deleteByRoleIdAndSiteId(roleId, siteId);
    }

    public List<RoleMenuResponse> findRoleMenus(Long roleId, Long siteId) {
        List<RoleMenu> roleMenus = roleMenuRepository.findByRoleIdAndSiteId(roleId, siteId);
        return RoleMenuResponse.ofList(roleMenus);
    }
}
