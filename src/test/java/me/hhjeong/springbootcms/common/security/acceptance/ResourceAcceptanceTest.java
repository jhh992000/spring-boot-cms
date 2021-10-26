package me.hhjeong.springbootcms.common.security.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.common.security.dto.CreateResourcesRequest;
import me.hhjeong.springbootcms.common.security.dto.UpdateResourcesRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ResourceAcceptanceTest extends AcceptancePerClassTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 리소스_인수테스트() {
        CreateResourcesRequest createResourcesRequest = new CreateResourcesRequest("/api/admin/**", "", 0, "url");
        ExtractableResponse<Response> 리소스_등록_응답 = 리소스_생성_요청(createResourcesRequest);
        assertResponseCode(리소스_등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(리소스_등록_응답.header("Location").split("/")[3]);

        UpdateResourcesRequest updateResourcesRequest = new UpdateResourcesRequest("/api/home/**", "", 1, "url");
        ExtractableResponse<Response> 리소스_수정_응답 = 리소스_수정_요청(id, updateResourcesRequest);
        assertResponseCode(리소스_수정_응답, HttpStatus.OK);

        ExtractableResponse<Response> 리소스_목록_조회_응답 = 리소스_목록_조회_요청();
        assertThat(리소스_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> 리소스_조회_응답 = 리소스_조회_요청(id);
        assertResponseCode(리소스_조회_응답, HttpStatus.OK);

        ExtractableResponse<Response> 리소스_삭제_응답 = 리소스_삭제_요청(id);
        assertResponseCode(리소스_삭제_응답, HttpStatus.NO_CONTENT);
    }

    private ExtractableResponse<Response> 리소스_생성_요청(CreateResourcesRequest createResourcesRequest) {
        Map params = objectMapper.convertValue(createResourcesRequest, Map.class);

        return post(params, "/api/resources");
    }

    private ExtractableResponse<Response> 리소스_수정_요청(Long id, UpdateResourcesRequest updateResourcesRequest) {
        Map params = objectMapper.convertValue(updateResourcesRequest, Map.class);

        return put(params, "/api/resources/" + id);
    }

    private ExtractableResponse<Response> 리소스_목록_조회_요청() {
        return get("/api/resources");
    }

    private ExtractableResponse<Response> 리소스_조회_요청(Long id) {
        return get("/api/resources/" + id);
    }

    private ExtractableResponse<Response> 리소스_삭제_요청(Long id) {
        return delete("/api/resources/" + id);
    }

}
