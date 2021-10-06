package me.hhjeong.springbootcms.account.acceptance;


import static me.hhjeong.springbootcms.common.config.SecurityConfig.CUSTOM_DEFAULT_FILTER_PROCESSES_URL;

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
    void 회원_등록되어_있음() {
        ExtractableResponse<Response> 로그인_인증_응답 = 관리자_로그인_요청(username, password);
        String token = 로그인_인증_응답.response().jsonPath().getString("token");

        ExtractableResponse<Response> 회원등록_응답 = 회원_등록되어_있음(token, "jhh992000@gmail.com", "1234");
        assertResponseCode(회원등록_응답, HttpStatus.CREATED);
    }

    public static ExtractableResponse<Response> 회원_등록되어_있음(String token, String username, String password) {
        return 회원_생성을_요청(token, username, password);
    }

    public static ExtractableResponse<Response> 회원_생성을_요청(String token, String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return post(params, "/api/account", token);
    }

    private ExtractableResponse<Response> 관리자_로그인_요청(String username, String password) {

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return post(params, CUSTOM_DEFAULT_FILTER_PROCESSES_URL);
    }
}
