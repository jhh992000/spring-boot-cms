package me.hhjeong.springbootcms.common.security.application;

import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.common.security.domain.Role;
import me.hhjeong.springbootcms.common.security.domain.RoleRepository;
import me.hhjeong.springbootcms.common.security.dto.CreateRoleRequest;
import me.hhjeong.springbootcms.common.security.dto.RoleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    //C
    public RoleResponse createRole(CreateRoleRequest createRoleRequest) {
        Role role = roleRepository.save(createRoleRequest.toRole());
        return RoleResponse.of(role);
    }

    //L

    //R

    //U

    //D

}
