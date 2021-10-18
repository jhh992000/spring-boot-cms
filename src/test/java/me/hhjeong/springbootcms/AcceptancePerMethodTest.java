package me.hhjeong.springbootcms;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class AcceptancePerMethodTest extends AcceptanceTest {

    @BeforeEach
    public void init() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

}
