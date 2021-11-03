package me.hhjeong.springbootcms.menu.domain;

import javax.persistence.EntityNotFoundException;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.site.domain.Site;
import me.hhjeong.springbootcms.site.domain.SiteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuRepositoryTest extends AcceptancePerClassTest {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Test
    void 컨텐츠_메뉴_등록() {
        Site site = Site.builder()
            .name("메인홈페이지")
            .enabled(true)
            .alias("main")
            .useLoginLock(true)
            .countOfLoginFail(5)
            .build();
        siteRepository.save(site);

        ContentMenu contentMenu = new ContentMenu(1L, Menu.builder()
            .site(site)
            .name("메뉴1")
            .listOrder(1L)
            .openType(MenuOpenType.CURRENT)
            .description("")
            .hide(false)
            .enable(true)
            .build());
        menuRepository.save(contentMenu);

        Menu cm = menuRepository.findById(contentMenu.getId()).orElseThrow(EntityNotFoundException::new);
        System.out.println(cm);

    }

}
