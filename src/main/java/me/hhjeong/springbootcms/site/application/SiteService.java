package me.hhjeong.springbootcms.site.application;

import static me.hhjeong.springbootcms.common.base.BaseConstants.PAGE_SIZE;
import static me.hhjeong.springbootcms.common.base.BaseConstants.START_PAGE_NO;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import me.hhjeong.springbootcms.site.domain.Site;
import me.hhjeong.springbootcms.site.domain.SiteRepository;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import me.hhjeong.springbootcms.site.dto.SiteResponse;
import me.hhjeong.springbootcms.site.dto.UpdateSiteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SiteService {

    private static final Logger logger = LoggerFactory.getLogger(SiteService.class);

    private SiteRepository siteRepository;
    private ObjectMapper objectMapper;

    public SiteService(SiteRepository siteRepository, ObjectMapper objectMapper) {
        this.siteRepository = siteRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public List<SiteResponse> findSites(Long lastSiteId) {
        Pageable pageable = PageRequest.of(START_PAGE_NO, PAGE_SIZE, Sort.by("id").descending());

        if (lastSiteId == null) {
            lastSiteId = siteRepository.findFirstByOrderByIdDesc()
                .map(Site::getId)
                .orElse(0L);
        }

        List<Site> sites = siteRepository.findLatest(lastSiteId, pageable);

        return sites.stream()
            .map(site -> SiteResponse.of(site))
            .collect(Collectors.toList());
    }

    public SiteResponse createSite(CreateSiteRequest request) {
        Site site = siteRepository.save(request.toSite());
        return SiteResponse.of(site);
    }

    public Site findById(Long id) {
        return siteRepository.findById(id)
            .orElseThrow(RuntimeException::new);
    }

    public Site replace(Long id, UpdateSiteRequest request) {
        return siteRepository.findById(id)
            .map(site -> {
                site.update(request.toSite());
                return site;
            })
            .orElseGet(() -> {
                Site newSite = request.toSite(id);
                return siteRepository.save(newSite);
            });
    }

    public void deleteSite(Long id) {
        siteRepository.deleteById(id);
    }

    public Site patch(Long id, JsonPatch patchDocument) {
        Site originalSite = findById(id);
        JsonStructure target = objectMapper.convertValue(originalSite, JsonStructure.class);
        JsonValue patchedSite = patchDocument.apply(target);
        Site modifiedSite = objectMapper.convertValue(patchedSite, Site.class);

        originalSite.update(modifiedSite);

        logger.debug("modified site : {}", modifiedSite);
        return modifiedSite;
    }
}
