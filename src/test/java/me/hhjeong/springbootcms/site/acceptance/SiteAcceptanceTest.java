package me.hhjeong.springbootcms.site.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import me.hhjeong.springbootcms.site.dto.UpdateSiteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class SiteAcceptanceTest extends AcceptancePerClassTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 사이트_인수테스트() {
        CreateSiteRequest createSiteRequest = new CreateSiteRequest("국문 홈페이지", true, "kor", true, 5);
        ExtractableResponse<Response> 사이트_등록_응답 = 사이트_생성_요청(createSiteRequest);
        assertResponseCode(사이트_등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(사이트_등록_응답.header("Location").split("/")[3]);

        UpdateSiteRequest updateSiteRequest = new UpdateSiteRequest("영문 홈페이지", true, "eng", false, 10);
        ExtractableResponse<Response> 사이트_수정_응답 = 사이트_수정_요청(id, updateSiteRequest);
        assertResponseCode(사이트_수정_응답, HttpStatus.OK);

        ExtractableResponse<Response> 사이트_목록_조회_응답 = 사이트목록_조회_요청();
        assertThat(사이트_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> 사이트_조회_응답 = 사이트_조회_요청(id);
        assertResponseCode(사이트_조회_응답, HttpStatus.OK);

        ExtractableResponse<Response> 사이트_삭제_응답 = 사이트_삭제_요청(id);
        assertResponseCode(사이트_삭제_응답, HttpStatus.NO_CONTENT);
    }

    private ExtractableResponse<Response> 사이트_삭제_요청(Long id) {
        return delete("/api/site/" + id);
    }

    private ExtractableResponse<Response> 사이트_조회_요청(Long id) {
        return get("/api/site/" + id);
    }

    private ExtractableResponse<Response> 사이트_수정_요청(Long id, UpdateSiteRequest updateSiteRequest) {
        Map<String, String> params = objectMapper.convertValue(updateSiteRequest, Map.class);

        return put(params, "/api/site/" + id);
    }

    public ExtractableResponse<Response> 사이트_생성_요청(CreateSiteRequest createSiteRequest) {
        Map<String, String> params = objectMapper.convertValue(createSiteRequest, Map.class);

        return post(params, "/api/site");
    }


    private ExtractableResponse<Response> 사이트목록_조회_요청() {
        return get("/api/site");
    }
}
