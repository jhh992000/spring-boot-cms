package me.hhjeong.springbootcms.common.security.ui;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.common.security.application.RoleService;
import me.hhjeong.springbootcms.common.security.dto.CreateRoleRequest;
import me.hhjeong.springbootcms.common.security.dto.RoleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody @Valid CreateRoleRequest request) {
        RoleResponse roleResponse = roleService.createRole(request);
        return ResponseEntity.created(URI.create("/api/roles/" + roleResponse.getId())).build();
    }

}
