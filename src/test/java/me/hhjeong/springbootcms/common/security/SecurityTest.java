package me.hhjeong.springbootcms.common.security;

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
import me.hhjeong.springbootcms.common.security.domain.Resources;
import me.hhjeong.springbootcms.common.security.domain.ResourcesRepository;
import me.hhjeong.springbootcms.common.security.domain.Role;
import me.hhjeong.springbootcms.common.security.domain.RoleRepository;
import me.hhjeong.springbootcms.common.security.domain.RoleResources;
import me.hhjeong.springbootcms.common.security.domain.RoleResourcesRepository;
import me.hhjeong.springbootcms.common.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("인증 및 인가 테스트")
public class SecurityTest extends AcceptancePerClassTest {

    private static final String username = "jhh992000@gmail.com";
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
        Role roleManager = new Role(2L, "ROLE_MANAGER", "내부관리자", LocalDateTime.now());
        Role roleUser = new Role(3L, "ROLE_USER", "일반사용자", LocalDateTime.now());
        roleRepository.save(roleAdmin);
        roleRepository.save(roleManager);
        roleRepository.save(roleUser);

        //리소스 등록
        Resources resourcesAllAdmin = new Resources(1L, "/api/admin/**", "", 0, "url", LocalDateTime.now());
        Resources resourcesAccount = new Resources(2L, "/api/account", "", 1, "url", LocalDateTime.now());
        Resources resourcesUserFeature = new Resources(3L, "/api/account/user-feature", "", 2, "url", LocalDateTime.now());
        Resources resourcesAdminFeature = new Resources(4L, "/api/account/admin-feature", "", 3, "url", LocalDateTime.now());
        resourcesRepository.save(resourcesAllAdmin);
        resourcesRepository.save(resourcesAccount);
        resourcesRepository.save(resourcesUserFeature);
        resourcesRepository.save(resourcesAdminFeature);

        //롤리소스 등록
        RoleResources roleResources1 = new RoleResources(1L, roleAdmin, resourcesAllAdmin);
        RoleResources roleResources2 = new RoleResources(2L, roleAdmin, resourcesAdminFeature);
        RoleResources roleResources3 = new RoleResources(3L, roleManager, resourcesAllAdmin);
        RoleResources roleResources4 = new RoleResources(4L, roleManager, resourcesAdminFeature);
        RoleResources roleResources5 = new RoleResources(5L, roleUser, resourcesAccount);
        RoleResources roleResources6 = new RoleResources(6L, roleUser, resourcesUserFeature);
        roleResourcesRepository.save(roleResources1);
        roleResourcesRepository.save(roleResources2);
        roleResourcesRepository.save(roleResources3);
        roleResourcesRepository.save(roleResources4);
        roleResourcesRepository.save(roleResources5);
        roleResourcesRepository.save(roleResources6);

        //권한정보 리로드
        urlFilterInvocationSecurityMetadataSource.reload();

        //사용자등록
        Set<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.USER);
        Account account = new Account(1L, "jhh992000@gmail.com", passwordEncoder.encode("1234"), roles);
        accountRepository.save(account);
    }

    @Test
    void 로그인시_JWT_토큰_발급() {
        ExtractableResponse<Response> 로그인_인증_응답 = 로그인_요청(username, password);

        String token = 로그인_인증_응답.response().jsonPath().getString("token");
        assertThat(token).isNotEmpty();
    }

    @Test
    void 로그인_후_권한이_있는_리소스_요청() {
        ExtractableResponse<Response> 로그인_인증_응답 = 로그인_요청(username, password);
        String token = 로그인_인증_응답.response().jsonPath().getString("token");

        ExtractableResponse<Response> response = get("/api/account/user-feature", token);
        assertResponseCode(response, HttpStatus.OK);
    }

    @Test
    void 로그인_후_권한이_없는_리소스_요청() {
        ExtractableResponse<Response> 로그인_인증_응답 = 로그인_요청(username, password);
        String token = 로그인_인증_응답.response().jsonPath().getString("token");

        ExtractableResponse<Response> response = get("/api/account/admin-feature", token);
        assertResponseCode(response, HttpStatus.FORBIDDEN);
    }

    private ExtractableResponse<Response> 로그인_요청(String username, String password) {

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return post(params, CUSTOM_DEFAULT_FILTER_PROCESSES_URL);
    }
}
