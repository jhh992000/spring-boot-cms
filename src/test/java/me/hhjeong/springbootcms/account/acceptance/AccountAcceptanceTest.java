package me.hhjeong.springbootcms.account.acceptance;


import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AccountAcceptanceTest extends AcceptancePerClassTest {

    @Test
    void 사용자계정_인수테스트() {
        ExtractableResponse<Response> 계정_등록_응답 = 계정_등록_요청("jhh992000@gmail.com", "P@ssword1");
        assertResponseCode(계정_등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(계정_등록_응답.header("Location").split("/")[3]);

        ExtractableResponse<Response> 계정_수정_응답 = 계정_수정_요청(id, "hhjeong@test.com", "P@ssword2!");
        assertResponseCode(계정_수정_응답, HttpStatus.OK);

        ExtractableResponse<Response> 계정_목록_조회_응답 = 계정목록_조회_요청();
        assertThat(계정_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> 계정_조회_응답 = 계정_조회_요청(id);
        assertResponseCode(계정_조회_응답, HttpStatus.OK);

        ExtractableResponse<Response> 계정_삭제_응답 = 계정_삭제_요청(id);
        assertResponseCode(계정_삭제_응답, HttpStatus.NO_CONTENT);
    }

    private ExtractableResponse<Response> 계정_삭제_요청(Long id) {
        return delete("/api/account/" + id);
    }

    private ExtractableResponse<Response> 계정_조회_요청(Long id) {
        return get("/api/account/" + id);
    }

    private ExtractableResponse<Response> 계정목록_조회_요청() {
        return get("/api/account");
    }

    public static ExtractableResponse<Response> 계정_등록_요청(String username, String password) {
        return 계정_생성_요청(username, password);
    }

    public static ExtractableResponse<Response> 계정_생성_요청(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return post(params, "/api/account");
    }

    private ExtractableResponse<Response> 계정_수정_요청(Long id, String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return put(params, "/api/account/" + id);
    }
}
