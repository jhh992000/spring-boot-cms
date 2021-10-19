package me.hhjeong.springbootcms.site.application;

import java.util.List;
import java.util.stream.Collectors;
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

@Service
@Transactional
public class SiteService {

    public static final int START_PAGE_NO = 0;
    public static final int LATEST_ACCOUNT_SIZE = 5;

    private SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Transactional(readOnly = true)
    public List<SiteResponse> findSites(Long lastSiteId) {
        Pageable pageable = PageRequest.of(START_PAGE_NO, LATEST_ACCOUNT_SIZE, Sort.by("id").descending());

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

    public void updateSite(Long id, UpdateSiteRequest request) {
        Site site = siteRepository.findById(id)
            .orElseThrow(RuntimeException::new);

        site.update(request.toSite());

    }

    public void deleteSite(Long id) {
        siteRepository.deleteById(id);
    }
}
