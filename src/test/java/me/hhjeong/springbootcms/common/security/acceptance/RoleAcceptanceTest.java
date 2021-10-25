package me.hhjeong.springbootcms.common.security.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.common.security.dto.CreateRoleRequest;
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
    }

    private ExtractableResponse<Response> 롤_생성_요청(CreateRoleRequest createRoleRequest) {
        Map<String, String> params = objectMapper.convertValue(createRoleRequest, Map.class);

        return post(params, "/api/roles");
    }

}
