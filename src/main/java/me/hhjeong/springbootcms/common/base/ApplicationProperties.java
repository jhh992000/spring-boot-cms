package me.hhjeong.springbootcms.common.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:application.properties"})
public class ApplicationProperties {

    static Environment environment;

    @Autowired
    public void setEnvironment(Environment env) {
        environment = env;
    }

    public static String[] getActiveProfiles() {
        return environment.getActiveProfiles();
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = environment.getProperty(key);
        if (StringUtils.isNotEmpty(value)) {
            return value;
        }
        return defaultValue;
    }

    public static Integer getIntProperty(String key) {
        String str = environment.getProperty(key);
        if (StringUtils.isNumeric(str)) {
            return Integer.parseInt(str);
        }
        return null;
    }

    public static Integer getIntProperty(String key, Integer defaultValue) {
        String str = environment.getProperty(key);
        if (StringUtils.isNumeric(str)) {
            return Integer.parseInt(str);
        }
        return null;
    }
}
