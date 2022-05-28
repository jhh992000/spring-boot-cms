package me.hhjeong.springbootcms.site.ui;

import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.menu.application.MenuService;
import me.hhjeong.springbootcms.menu.dto.MenuResponse;
import me.hhjeong.springbootcms.site.application.SiteService;
import me.hhjeong.springbootcms.site.domain.Site;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import me.hhjeong.springbootcms.site.dto.SiteResponse;
import me.hhjeong.springbootcms.site.dto.UpdateSiteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService siteService;
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<SiteResponse> createSite(@RequestBody @Valid CreateSiteRequest request) {
        SiteResponse siteResponse = siteService.createSite(request);
        return ResponseEntity.created(URI.create("/api/sites/" + siteResponse.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<SiteResponse>> findSites(@RequestParam(name = "id", required = false) Long lastId) {
        List<SiteResponse> sites = siteService.findSites(lastId);
        return ResponseEntity.ok().body(sites);
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<List<MenuResponse>> findSiteMenus(@PathVariable Long id) {
        List<MenuResponse> menus = menuService.findMenus(id);
        return ResponseEntity.ok().body(menus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponse> findSite(@PathVariable Long id) {
        Site site = siteService.findSite(id);
        return ResponseEntity.ok().body(SiteResponse.of(site));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteResponse> replaceSite(@PathVariable Long id, @RequestBody @Valid UpdateSiteRequest request) {
        Site site = siteService.replace(id, request);
        return ResponseEntity.ok().body(SiteResponse.of(site));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<SiteResponse> patchSite(@PathVariable Long id, @RequestBody JsonPatch patchDocument) {
        Site site = siteService.patchSite(id, patchDocument);
        return ResponseEntity.ok().body(SiteResponse.of(site));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }

}
