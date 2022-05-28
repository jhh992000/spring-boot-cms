package me.hhjeong.springbootcms.site.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hhjeong.springbootcms.site.domain.Site;
import me.hhjeong.springbootcms.site.domain.SiteRepository;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import me.hhjeong.springbootcms.site.dto.SiteResponse;
import me.hhjeong.springbootcms.site.dto.UpdateSiteRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import java.util.List;
import java.util.stream.Collectors;

import static me.hhjeong.springbootcms.common.base.BaseConstants.PAGE_SIZE;
import static me.hhjeong.springbootcms.common.base.BaseConstants.START_PAGE_NO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;
    private final ObjectMapper objectMapper;

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
                .map(SiteResponse::of)
                .collect(Collectors.toList());
    }

    public SiteResponse createSite(CreateSiteRequest request) {
        Site site = siteRepository.save(request.toSite());
        return SiteResponse.of(site);
    }

    public Site findSite(Long id) {
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

    public Site patchSite(Long id, JsonPatch jsonPatch) {
        Site originalSite = findSite(id);
        Site modifiedSite = mergeSite(originalSite, jsonPatch);
        originalSite.update(modifiedSite);
        log.debug("modified site : {}", modifiedSite);
        return modifiedSite;
    }

    private Site mergeSite(Site originalSite, JsonPatch jsonPatch) {
        JsonStructure target = objectMapper.convertValue(originalSite, JsonStructure.class);
        JsonValue patchedSite = jsonPatch.apply(target);
        return objectMapper.convertValue(patchedSite, Site.class);
    }
}
