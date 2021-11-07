package me.hhjeong.springbootcms.menu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.menu.dto.CreateMenuRequest;
import me.hhjeong.springbootcms.menu.dto.SaveRoleMenuRequest;
import me.hhjeong.springbootcms.security.dto.CreateRoleRequest;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class RoleMenuAcceptanceTest extends AcceptancePerClassTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 롤메뉴_인수테스트() throws JsonProcessingException {
        CreateRoleRequest createRoleRequest = new CreateRoleRequest("전체관리자", "전체관리자", 1L);
        ExtractableResponse<Response> 롤_등록_응답 = 롤_생성_요청(createRoleRequest);
        assertResponseCode(롤_등록_응답, HttpStatus.CREATED);

        Long roleId = Long.parseLong(롤_등록_응답.header("Location").split("/")[3]);

        CreateSiteRequest createSiteRequest = new CreateSiteRequest("국문 홈페이지", true, "kor", true, 5);
        ExtractableResponse<Response> 사이트_등록_응답 = 사이트_생성_요청(createSiteRequest);
        assertResponseCode(사이트_등록_응답, HttpStatus.CREATED);

        Long siteId = Long.parseLong(사이트_등록_응답.header("Location").split("/")[3]);

        ExtractableResponse<Response> 첫번째_메뉴_등록_응답 = 메뉴_등록_요청(new CreateMenuRequest(siteId, null, 1L, "1. 메뉴", "", MenuOpenType.CURRENT, false, true));
        assertResponseCode(첫번째_메뉴_등록_응답, HttpStatus.CREATED);
        Long 첫번째_메뉴_ID = Long.parseLong(첫번째_메뉴_등록_응답.header("Location").split("/")[3]);

        ExtractableResponse<Response> 두번째_메뉴_등록_응답 = 메뉴_등록_요청(new CreateMenuRequest(siteId, null, 2L, "2. 메뉴", "", MenuOpenType.CURRENT, false, true));
        assertResponseCode(두번째_메뉴_등록_응답, HttpStatus.CREATED);
        Long 두번째_메뉴_ID = Long.parseLong(두번째_메뉴_등록_응답.header("Location").split("/")[3]);

        List<SaveRoleMenuRequest> requests = Arrays.asList(new SaveRoleMenuRequest(첫번째_메뉴_ID), new SaveRoleMenuRequest(두번째_메뉴_ID));
        ExtractableResponse<Response> 롤메뉴_등록_응답 = 롤메뉴_등록_요청(roleId, siteId, requests);
        assertResponseCode(롤메뉴_등록_응답, HttpStatus.OK);

        ExtractableResponse<Response> 목록_조회_응답 = 롤메뉴_목록_조회_요청(roleId, siteId);
        assertThat(목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> 삭제_응답 = 롤메뉴_삭제_요청(roleId, siteId);
        assertResponseCode(삭제_응답, HttpStatus.NO_CONTENT);
    }

    private ExtractableResponse<Response> 롤_생성_요청(CreateRoleRequest createRoleRequest) {
        Map params = objectMapper.convertValue(createRoleRequest, Map.class);

        return post(params, "/api/roles");
    }

    public ExtractableResponse<Response> 사이트_생성_요청(CreateSiteRequest createSiteRequest) {
        Map params = objectMapper.convertValue(createSiteRequest, Map.class);

        return post(params, "/api/sites");
    }

    public ExtractableResponse<Response> 메뉴_등록_요청(CreateMenuRequest createMenuRequest) {
        Map params = objectMapper.convertValue(createMenuRequest, Map.class);
        return post(params, "/api/menus");
    }

    public ExtractableResponse<Response> 롤메뉴_등록_요청(Long roleId, Long siteId, List<SaveRoleMenuRequest> requests) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(requests);
        return post(jsonString, String.format("/api/roles/%d/sites/%d/roleMenus", roleId, siteId));
    }

    private ExtractableResponse<Response> 롤메뉴_목록_조회_요청(Long roleId, Long siteId) {
        return get(String.format("/api/roles/%d/sites/%d/roleMenus", roleId, siteId));
    }

    private ExtractableResponse<Response> 롤메뉴_삭제_요청(Long roleId, Long siteId) {
        return delete(String.format("/api/roles/%d/sites/%d/roleMenus", roleId, siteId));
    }
}
