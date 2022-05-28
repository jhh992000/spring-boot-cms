package me.hhjeong.springbootcms.security.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.security.application.RoleResourcesService;
import me.hhjeong.springbootcms.security.domain.RoleResources;
import me.hhjeong.springbootcms.security.dto.CreateRoleResourcesRequest;
import me.hhjeong.springbootcms.security.dto.RoleResourcesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roleResources")
public class RoleResourcesController {

    private final RoleResourcesService roleResourcesService;

    @PostMapping
    public ResponseEntity<RoleResourcesResponse> createRoleResources(@RequestBody @Valid CreateRoleResourcesRequest request) {
        RoleResourcesResponse roleResourcesResponse = roleResourcesService.createRoleResources(request);
        return ResponseEntity.created(URI.create("/api/roleResourcess/" + roleResourcesResponse.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<RoleResourcesResponse>> getRoleResourcesList(@RequestParam(name = "id", required = false) Long id) {
        List<RoleResourcesResponse> roleResourcess = roleResourcesService.findRoleResourcess(id);
        return ResponseEntity.ok().body(roleResourcess);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResourcesResponse> getRoleResources(@PathVariable Long id) {
        RoleResources roleResources = roleResourcesService.findById(id);
        return ResponseEntity.ok().body(RoleResourcesResponse.of(roleResources));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoleResources(@PathVariable Long id) {
        roleResourcesService.deleteRoleResources(id);
        return ResponseEntity.noContent().build();
    }
}
