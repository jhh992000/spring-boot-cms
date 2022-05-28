package me.hhjeong.springbootcms.security.application;

import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.security.domain.Role;
import me.hhjeong.springbootcms.security.domain.RoleRepository;
import me.hhjeong.springbootcms.security.dto.CreateRoleRequest;
import me.hhjeong.springbootcms.security.dto.RoleResponse;
import me.hhjeong.springbootcms.security.dto.UpdateRoleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleResponse createRole(CreateRoleRequest createRoleRequest) {
        Role role = roleRepository.save(createRoleRequest.toRole());
        return RoleResponse.of(role);
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> findRoles(Long lastRoleId) {
        Pageable pageable = PageRequest.of(START_PAGE_NO, PAGE_SIZE, Sort.by("id").descending());

        if (lastRoleId == null) {
            lastRoleId = roleRepository.findFirstByOrderByIdDesc()
                    .map(Role::getId)
                    .orElse(0L);
        }

        List<Role> roles = roleRepository.findLatest(lastRoleId, pageable);

        return roles.stream()
                .map(role -> RoleResponse.of(role))
                .collect(Collectors.toList());
    }


    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public Role replace(Long id, UpdateRoleRequest request) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.update(request.toRole());
                    return role;
                })
                .orElseGet(() -> {
                    Role newRole = request.toRole(id);
                    return roleRepository.save(newRole);
                });
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

}
