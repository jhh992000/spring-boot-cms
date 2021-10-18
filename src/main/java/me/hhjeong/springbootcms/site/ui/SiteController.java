package me.hhjeong.springbootcms.site.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import me.hhjeong.springbootcms.site.application.SiteService;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import me.hhjeong.springbootcms.site.dto.SiteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/site")
public class SiteController {

    private SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping
    public ResponseEntity<SiteResponse> create(@RequestBody @Valid CreateSiteRequest request) {
        SiteResponse siteResponse = siteService.createSite(request);
        return ResponseEntity.created(URI.create("/api/site/" + siteResponse.getId())).build();
    }


    @GetMapping
    public ResponseEntity<List<SiteResponse>> list(@RequestParam(name = "id", required = false) Long id) {
        List<SiteResponse> sites = siteService.findSites(id);
        return ResponseEntity.ok().body(sites);
    }

}
