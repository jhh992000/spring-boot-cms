package me.hhjeong.springbootcms.common.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(
    properties = {"spring.config.location=classpath:/application-test.properties"}
)
public class BaseConstantsTest {

    @Autowired
    private Environment environment;

    @Test
    void 전역변수_테스트() {
        assertThat(BaseConstants.RUN_ENV).isEqualTo(RunEnv.LOCAL);
        assertThat(BaseConstants.PAGE_SIZE).isEqualTo(Integer.parseInt(environment.getProperty("global.page-size")));
    }

}
