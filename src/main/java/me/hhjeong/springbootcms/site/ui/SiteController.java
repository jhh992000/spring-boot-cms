package me.hhjeong.springbootcms.site.ui;

import java.net.URI;
import java.util.List;
import javax.json.JsonPatch;
import javax.validation.Valid;
import me.hhjeong.springbootcms.site.application.SiteService;
import me.hhjeong.springbootcms.site.domain.Site;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import me.hhjeong.springbootcms.site.dto.SiteResponse;
import me.hhjeong.springbootcms.site.dto.UpdateSiteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping
    public ResponseEntity<SiteResponse> createSite(@RequestBody @Valid CreateSiteRequest request) {
        SiteResponse siteResponse = siteService.createSite(request);
        return ResponseEntity.created(URI.create("/api/sites/" + siteResponse.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<SiteResponse>> getSiteList(@RequestParam(name = "id", required = false) Long id) {
        List<SiteResponse> sites = siteService.findSites(id);
        return ResponseEntity.ok().body(sites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponse> getSite(@PathVariable Long id) {
        Site site = siteService.findById(id);
        return ResponseEntity.ok().body(SiteResponse.of(site));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteResponse> replaceSite(@PathVariable Long id, @RequestBody @Valid UpdateSiteRequest request) {
        Site site = siteService.replace(id, request);
        return ResponseEntity.ok().body(SiteResponse.of(site));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<SiteResponse> patchSite(@PathVariable Long id, @RequestBody JsonPatch patchDocument) {
        Site site = siteService.patch(id, patchDocument);
        return ResponseEntity.ok().body(SiteResponse.of(site));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }

}
