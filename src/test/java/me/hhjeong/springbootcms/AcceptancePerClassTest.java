package me.hhjeong.springbootcms;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AcceptancePerClassTest extends AcceptanceTest {

    @BeforeAll
    public void init() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

}
