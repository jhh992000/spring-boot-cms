package me.hhjeong.springbootcms.security.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.security.dto.CreateResourcesRequest;
import me.hhjeong.springbootcms.security.dto.CreateRoleRequest;
import me.hhjeong.springbootcms.security.dto.CreateRoleResourcesRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class RoleResourcesAcceptanceTest extends AcceptancePerClassTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 롤_리소스_인수테스트() {

        CreateRoleRequest createRoleRequest = new CreateRoleRequest("전체관리자", "전체관리자");
        ExtractableResponse<Response> 롤_등록_응답 = 롤_생성_요청(createRoleRequest);
        assertResponseCode(롤_등록_응답, HttpStatus.CREATED);

        Long roleId = Long.parseLong(롤_등록_응답.header("Location").split("/")[3]);

        CreateResourcesRequest createResourcesRequest = new CreateResourcesRequest("/api/admin/**", "", 0, "url");
        ExtractableResponse<Response> 리소스_등록_응답 = 리소스_생성_요청(createResourcesRequest);
        assertResponseCode(리소스_등록_응답, HttpStatus.CREATED);

        Long resourceId = Long.parseLong(리소스_등록_응답.header("Location").split("/")[3]);

        CreateRoleResourcesRequest createRoleResourcesRequest = new CreateRoleResourcesRequest(roleId, resourceId);
        ExtractableResponse<Response> 롤_리소스_등록_응답 = 롤_리소스_생성_요청(createRoleResourcesRequest);
        assertResponseCode(롤_리소스_등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(롤_리소스_등록_응답.header("Location").split("/")[3]);

        ExtractableResponse<Response> 롤_리소스_목록_조회_응답 = 롤_리소스_목록_조회_요청();
        assertThat(롤_리소스_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> 롤_리소스_조회_응답 = 롤_리소스_조회_요청(id);
        assertResponseCode(롤_리소스_조회_응답, HttpStatus.OK);

        ExtractableResponse<Response> 롤_리소스_삭제_응답 = 롤_리소스_삭제_요청(id);
        assertResponseCode(롤_리소스_삭제_응답, HttpStatus.NO_CONTENT);
    }

    private ExtractableResponse<Response> 롤_생성_요청(CreateRoleRequest createRoleRequest) {
        Map params = objectMapper.convertValue(createRoleRequest, Map.class);

        return post(params, "/api/roles");
    }

    private ExtractableResponse<Response> 리소스_생성_요청(CreateResourcesRequest createResourcesRequest) {
        Map params = objectMapper.convertValue(createResourcesRequest, Map.class);

        return post(params, "/api/resources");
    }


    private ExtractableResponse<Response> 롤_리소스_생성_요청(CreateRoleResourcesRequest createRoleResourcesRequest) {
        Map params = objectMapper.convertValue(createRoleResourcesRequest, Map.class);

        return post(params, "/api/roleResources");
    }

    private ExtractableResponse<Response> 롤_리소스_목록_조회_요청() {
        return get("/api/roleResources");
    }

    private ExtractableResponse<Response> 롤_리소스_조회_요청(Long id) {
        return get("/api/roleResources/" + id);
    }

    private ExtractableResponse<Response> 롤_리소스_삭제_요청(Long id) {
        return delete("/api/roleResources/" + id);
    }

}
