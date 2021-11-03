package me.hhjeong.springbootcms.menu.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.menu.application.MenuService;
import me.hhjeong.springbootcms.menu.domain.Menu;
import me.hhjeong.springbootcms.menu.domain.MenuOpenType;
import me.hhjeong.springbootcms.menu.domain.MenuRepository;
import me.hhjeong.springbootcms.menu.dto.CreateMenuRequest;
import me.hhjeong.springbootcms.menu.dto.MenuResponse;
import me.hhjeong.springbootcms.menu.dto.UpdateMenuRequest;
import me.hhjeong.springbootcms.site.domain.Site;
import me.hhjeong.springbootcms.site.domain.SiteRepository;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class MenuAcceptanceTest extends AcceptancePerClassTest {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 메뉴_계층형_테스트() {
        Site site = Site.builder()
            .name("메인홈페이지")
            .enabled(true)
            .alias("main")
            .useLoginLock(true)
            .countOfLoginFail(5)
            .build();
        siteRepository.save(site);

        Menu menu1 = new Menu(site, null, 1L, "1. 메뉴", "", MenuOpenType.CURRENT, false, true);
        Menu menu1_1 = new Menu(site, null, 1L, "1-1. 메뉴", "", MenuOpenType.CURRENT, false, true);
        Menu menu1_2 = new Menu(site, null, 2L, "1-2. 메뉴", "", MenuOpenType.CURRENT, false, true);
        menu1.addChildren(menu1_1);
        menu1.addChildren(menu1_2);
        menuRepository.save(menu1);

        Menu menu2 = new Menu(site, null, 2L, "2. 메뉴", "", MenuOpenType.CURRENT, false, true);
        Menu menu2_1 = new Menu(site, null, 1L, "2-1. 메뉴", "", MenuOpenType.CURRENT, false, true);
        menu2.addChildren(menu2_1);
        menuRepository.save(menu2);

        Menu menu3 = new Menu(site, null, 3L, "3. 메뉴", "", MenuOpenType.CURRENT, false, true);
        menuRepository.save(menu3);

        List<MenuResponse> menuResponses = menuService.findMenus(site.getId());
        assertThat(menuResponses).hasSize(3);
        assertThat(menuResponses.get(0).getChildren()).hasSize(2);
    }

    @Test
    void 메뉴_인수테스트() {
        CreateSiteRequest createSiteRequest = new CreateSiteRequest("국문 홈페이지", true, "kor", true, 5);
        ExtractableResponse<Response> 사이트_등록_응답 = 사이트_생성_요청(createSiteRequest);
        assertResponseCode(사이트_등록_응답, HttpStatus.CREATED);

        Long siteId = Long.parseLong(사이트_등록_응답.header("Location").split("/")[3]);

        CreateMenuRequest createMenuRequest = new CreateMenuRequest(siteId, null, 1L, "1. 메뉴", "", MenuOpenType.CURRENT, false, true);
        ExtractableResponse<Response> 등록_응답 = 등록_요청(createMenuRequest);
        assertResponseCode(등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(등록_응답.header("Location").split("/")[3]);

        UpdateMenuRequest updateMenuRequest = new UpdateMenuRequest(siteId, null, 1L, "1. 수정메뉴", "asdf", MenuOpenType.NEW, false, true);
        ExtractableResponse<Response> 수정_응답 = 수정_요청(id, updateMenuRequest);
        assertResponseCode(수정_응답, HttpStatus.OK);

        ExtractableResponse<Response> 사이트_메뉴_목록_조회_응답 = 사이트_메뉴_목록_조회_요청(siteId);
        assertThat(사이트_메뉴_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<MenuResponse> 목록_조회_응답 = new ArrayList<>(사이트_메뉴_목록_조회_응답.jsonPath().getList(".", MenuResponse.class));
        assertThat(목록_조회_응답).hasSize(1);

        ExtractableResponse<Response> 조회_응답 = 조회_요청(id);
        assertResponseCode(조회_응답, HttpStatus.OK);

        ExtractableResponse<Response> 삭제_응답 = 삭제_요청(id);
        assertResponseCode(삭제_응답, HttpStatus.NO_CONTENT);
    }

    public ExtractableResponse<Response> 사이트_생성_요청(CreateSiteRequest createSiteRequest) {
        Map params = objectMapper.convertValue(createSiteRequest, Map.class);

        return post(params, "/api/sites");
    }

    private ExtractableResponse<Response> 사이트_메뉴_목록_조회_요청(long siteId) {
        return get("/api/sites/" + siteId + "/menus");
    }

    public ExtractableResponse<Response> 등록_요청(CreateMenuRequest createMenuRequest) {
        Map params = objectMapper.convertValue(createMenuRequest, Map.class);
        return post(params, "/api/menus");
    }

    private ExtractableResponse<Response> 수정_요청(Long id, UpdateMenuRequest updateMenuRequest) {
        Map params = objectMapper.convertValue(updateMenuRequest, Map.class);
        return put(params, "/api/menus/" + id);
    }

    private ExtractableResponse<Response> 목록_조회_요청() {
        return get("/api/menus");
    }

    private ExtractableResponse<Response> 조회_요청(Long id) {
        return get("/api/menus/" + id);
    }

    private ExtractableResponse<Response> 삭제_요청(Long id) {
        return delete("/api/menus/" + id);
    }

}
