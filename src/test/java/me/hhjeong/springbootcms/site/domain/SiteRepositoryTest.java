package me.hhjeong.springbootcms.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import me.hhjeong.springbootcms.AcceptancePerMethodTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SiteRepositoryTest extends AcceptancePerMethodTest {

    @Autowired
    private SiteRepository siteRepository;

    @Test
    void querydsl_사이트명으로_조회() {
        //given
        Site site = Site.builder()
            .name("국문홈페이지")
            .alias("ko")
            .enabled(true)
            .useLoginLock(true)
            .countOfLoginFail(5)
            .build();

        siteRepository.save(site);

        //when
        List<Site> sites = siteRepository.findByName("국문홈페이지");

        //then
        assertThat(sites).hasSize(1);
    }

}
