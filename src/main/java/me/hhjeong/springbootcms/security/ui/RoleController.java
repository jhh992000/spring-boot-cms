package me.hhjeong.springbootcms.security.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.security.application.RoleService;
import me.hhjeong.springbootcms.security.domain.Role;
import me.hhjeong.springbootcms.security.dto.CreateRoleRequest;
import me.hhjeong.springbootcms.security.dto.RoleResponse;
import me.hhjeong.springbootcms.security.dto.UpdateRoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoleList(@RequestParam(name = "id", required = false) Long id) {
        List<RoleResponse> roles = roleService.findRoles(id);
        return ResponseEntity.ok().body(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResponseEntity.ok().body(RoleResponse.of(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> replaceRole(@PathVariable Long id, @RequestBody @Valid UpdateRoleRequest request) {
        Role role = roleService.replace(id, request);
        return ResponseEntity.ok().body(RoleResponse.of(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

}
