package me.hhjeong.springbootcms.security.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.security.dto.CreateRoleRequest;
import me.hhjeong.springbootcms.security.dto.UpdateRoleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class RoleAcceptanceTest extends AcceptancePerClassTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 롤_인수테스트() {
        CreateRoleRequest createRoleRequest = new CreateRoleRequest("전체관리자", "전체관리자");
        ExtractableResponse<Response> 롤_등록_응답 = 롤_생성_요청(createRoleRequest);
        assertResponseCode(롤_등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(롤_등록_응답.header("Location").split("/")[3]);

        UpdateRoleRequest updateRoleRequest = new UpdateRoleRequest("내부관리자", "내부관리자");
        ExtractableResponse<Response> 롤_수정_응답 = 롤_수정_요청(id, updateRoleRequest);
        assertResponseCode(롤_수정_응답, HttpStatus.OK);

        ExtractableResponse<Response> 롤_목록_조회_응답 = 롤_목록_조회_요청();
        assertThat(롤_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> 롤_조회_응답 = 롤_조회_요청(id);
        assertResponseCode(롤_조회_응답, HttpStatus.OK);

        ExtractableResponse<Response> 롤_삭제_응답 = 롤_삭제_요청(id);
        assertResponseCode(롤_삭제_응답, HttpStatus.NO_CONTENT);
    }

    private ExtractableResponse<Response> 롤_생성_요청(CreateRoleRequest createRoleRequest) {
        Map params = objectMapper.convertValue(createRoleRequest, Map.class);

        return post(params, "/api/roles");
    }

    private ExtractableResponse<Response> 롤_수정_요청(Long id, UpdateRoleRequest updateRoleRequest) {
        Map params = objectMapper.convertValue(updateRoleRequest, Map.class);

        return put(params, "/api/roles/" + id);
    }

    private ExtractableResponse<Response> 롤_목록_조회_요청() {
        return get("/api/roles");
    }

    private ExtractableResponse<Response> 롤_조회_요청(Long id) {
        return get("/api/roles/" + id);
    }

    private ExtractableResponse<Response> 롤_삭제_요청(Long id) {
        return delete("/api/roles/" + id);
    }

}
