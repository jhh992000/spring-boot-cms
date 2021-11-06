package me.hhjeong.springbootcms;

import io.restassured.RestAssured;
import me.hhjeong.springbootcms.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AcceptancePerClassTest extends AcceptanceTest {

    @Autowired
    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @BeforeAll
    public void init() {
        RestAssured.port = port;
        databaseCleanup.execute();
        urlFilterInvocationSecurityMetadataSource.reload(); //spring security 인가 프로세스 초기화
    }

}
