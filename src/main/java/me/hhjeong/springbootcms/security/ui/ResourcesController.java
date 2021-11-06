package me.hhjeong.springbootcms.security.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.security.application.ResourcesService;
import me.hhjeong.springbootcms.security.domain.Resources;
import me.hhjeong.springbootcms.security.dto.CreateResourcesRequest;
import me.hhjeong.springbootcms.security.dto.ResourcesResponse;
import me.hhjeong.springbootcms.security.dto.UpdateResourcesRequest;
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
@RequestMapping("/api/resources")
public class ResourcesController {

    private final ResourcesService resourcesService;

    @PostMapping
    public ResponseEntity<ResourcesResponse> createResources(@RequestBody @Valid CreateResourcesRequest request) {
        ResourcesResponse resourcesResponse = resourcesService.createResources(request);
        return ResponseEntity.created(URI.create("/api/resources/" + resourcesResponse.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ResourcesResponse>> getResourcesList(@RequestParam(name = "id", required = false) Long id) {
        List<ResourcesResponse> resources = resourcesService.findResourcess(id);
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourcesResponse> getResources(@PathVariable Long id) {
        Resources resources = resourcesService.findById(id);
        return ResponseEntity.ok().body(ResourcesResponse.of(resources));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourcesResponse> replaceResources(@PathVariable Long id, @RequestBody @Valid UpdateResourcesRequest request) {
        Resources resources = resourcesService.replace(id, request);
        return ResponseEntity.ok().body(ResourcesResponse.of(resources));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteResources(@PathVariable Long id) {
        resourcesService.deleteResources(id);
        return ResponseEntity.noContent().build();
    }

}
