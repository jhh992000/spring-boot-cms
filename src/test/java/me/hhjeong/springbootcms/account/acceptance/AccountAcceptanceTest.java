package me.hhjeong.springbootcms.account.acceptance;


import static me.hhjeong.springbootcms.common.config.SecurityConfig.CUSTOM_DEFAULT_FILTER_PROCESSES_URL;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import me.hhjeong.springbootcms.AcceptancePerClassTest;
import me.hhjeong.springbootcms.account.domain.Account;
import me.hhjeong.springbootcms.account.domain.AccountRepository;
import me.hhjeong.springbootcms.account.domain.AccountRole;
import me.hhjeong.springbootcms.account.dto.AccountResponse;
import me.hhjeong.springbootcms.common.security.domain.Resources;
import me.hhjeong.springbootcms.common.security.domain.ResourcesRepository;
import me.hhjeong.springbootcms.common.security.domain.Role;
import me.hhjeong.springbootcms.common.security.domain.RoleRepository;
import me.hhjeong.springbootcms.common.security.domain.RoleResources;
import me.hhjeong.springbootcms.common.security.domain.RoleResourcesRepository;
import me.hhjeong.springbootcms.common.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountAcceptanceTest extends AcceptancePerClassTest {

    private static final String username = "admin@gmail.com";
    private static final String password = "1234";


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private RoleResourcesRepository roleResourcesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @BeforeAll
    void setup() {

        //롤 등록
        Role roleAdmin = new Role(1L, "ROLE_ADMIN", "전체관리자", LocalDateTime.now());
        roleRepository.save(roleAdmin);

        //리소스 등록
        Resources resourcesAccount = new Resources(1L, "/api/account", "", 1, "url", LocalDateTime.now());
        resourcesRepository.save(resourcesAccount);

        //롤리소스 등록
        RoleResources roleResources1 = new RoleResources(1L, roleAdmin, resourcesAccount);
        roleResourcesRepository.save(roleResources1);

        //권한정보 리로드
        urlFilterInvocationSecurityMetadataSource.reload();

        //사용자등록
        Set<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.ADMIN);
        Account account = new Account(1L, username, passwordEncoder.encode(password), roles);
        accountRepository.save(account);
    }

    @Test
    void 사용자계정_인수테스트() {
        ExtractableResponse<Response> 로그인_인증_응답 = 관리자_로그인_요청(username, password);
        String token = 로그인_인증_응답.response().jsonPath().getString("accessToken");

        ExtractableResponse<Response> 계정_등록_응답 = 계정_등록_요청(token, "jhh992000@gmail.com", "1234");
        assertResponseCode(계정_등록_응답, HttpStatus.CREATED);

        Long id = Long.parseLong(계정_등록_응답.header("Location").split("/")[3]);

        ExtractableResponse<Response> 계정_수정_응답 = 계정_수정_요청(token, id, "hhjeong@test.com", "1111");
        assertResponseCode(계정_수정_응답, HttpStatus.OK);

        ExtractableResponse<Response> 계정_목록_조회_응답 = 계정목록_조회_요청(token);
        assertThat(계정_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> 계정_조회_응답 = 계정_조회_요청(token, id);
        assertResponseCode(계정_조회_응답, HttpStatus.OK);

        ExtractableResponse<Response> 계정_삭제_응답 = 계정_삭제_요청(token, id);
        assertResponseCode(계정_삭제_응답, HttpStatus.NO_CONTENT);

    }

    private ExtractableResponse<Response> 계정_삭제_요청(String token, Long id) {
        return delete("/api/account/" + id, token);
    }

    private ExtractableResponse<Response> 계정_조회_요청(String token, Long id) {
        return get("/api/account/" + id, token);
    }

    private ExtractableResponse<Response> 계정목록_조회_요청(String token) {
        return get("/api/account", token);
    }

    public static ExtractableResponse<Response> 계정_등록_요청(String token, String username, String password) {
        return 계정_생성_요청(token, username, password);
    }

    public static ExtractableResponse<Response> 계정_생성_요청(String token, String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return post(params, "/api/account", token);
    }

    private ExtractableResponse<Response> 계정_수정_요청(String token, Long id, String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return put(params, "/api/account/" + id, token);
    }

    private ExtractableResponse<Response> 관리자_로그인_요청(String username, String password) {

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return post(params, CUSTOM_DEFAULT_FILTER_PROCESSES_URL);
    }
}
