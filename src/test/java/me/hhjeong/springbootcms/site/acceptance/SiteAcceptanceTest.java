package me.hhjeong.springbootcms.site.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.site.dto.CreateSiteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class SiteAcceptanceTest extends AcceptancePerClassTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 사이트_인수테스트() {
        CreateSiteRequest createSiteRequest = new CreateSiteRequest("기본홈페이지", true, "home", true, 5);
        ExtractableResponse<Response> 사이트_등록_응답 = 사이트_생성_요청(createSiteRequest);
        assertResponseCode(사이트_등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(사이트_등록_응답.header("Location").split("/")[3]);

        ExtractableResponse<Response> 사이트_목록_조회_응답 = 사이트목록_조회_요청();
        assertThat(사이트_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        //TODO - 수정, 삭제 테스트
    }

    public ExtractableResponse<Response> 사이트_생성_요청(CreateSiteRequest createSiteRequest) {
        Map<String, String> params = objectMapper.convertValue(createSiteRequest, Map.class);

        return post(params, "/api/site");
    }


    private ExtractableResponse<Response> 사이트목록_조회_요청() {
        return get("/api/site");
    }
}
