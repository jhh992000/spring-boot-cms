package me.hhjeong.springbootcms.security.application;

import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.security.domain.RoleResources;
import me.hhjeong.springbootcms.security.domain.RoleResourcesRepository;
import me.hhjeong.springbootcms.security.dto.CreateRoleResourcesRequest;
import me.hhjeong.springbootcms.security.dto.RoleResourcesResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static me.hhjeong.springbootcms.common.base.BaseConstants.PAGE_SIZE;
import static me.hhjeong.springbootcms.common.base.BaseConstants.START_PAGE_NO;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleResourcesService {

    private final RoleResourcesRepository roleResourcesRepository;

    public RoleResourcesResponse createRoleResources(CreateRoleResourcesRequest createRoleResourcesRequest) {
        RoleResources roleResources = roleResourcesRepository.save(createRoleResourcesRequest.toRoleResources());
        return RoleResourcesResponse.of(roleResources);
    }

    @Transactional(readOnly = true)
    public List<RoleResourcesResponse> findRoleResourcess(Long lastRoleResourcesId) {
        Pageable pageable = PageRequest.of(START_PAGE_NO, PAGE_SIZE, Sort.by("id").descending());

        if (lastRoleResourcesId == null) {
            lastRoleResourcesId = roleResourcesRepository.findFirstByOrderByIdDesc()
                    .map(RoleResources::getId)
                    .orElse(0L);
        }

        List<RoleResources> roleResourcess = roleResourcesRepository.findLatest(lastRoleResourcesId, pageable);

        return roleResourcess.stream()
                .map(roleResources -> RoleResourcesResponse.of(roleResources))
                .collect(Collectors.toList());
    }


    public RoleResources findById(Long id) {
        return roleResourcesRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteRoleResources(Long id) {
        roleResourcesRepository.deleteById(id);
    }

}
