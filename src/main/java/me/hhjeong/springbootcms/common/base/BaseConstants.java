package me.hhjeong.springbootcms.common.base;

import java.util.Arrays;

public class BaseConstants {

    public static final RunEnv RUN_ENV;
    public static final int START_PAGE_NO;
    public static final int PAGE_SIZE;

    static {
        String[] activeProfiles = ApplicationProperties.getActiveProfiles();
        if (hasProfile(activeProfiles, RunEnv.LOCAL)) {
            RUN_ENV = RunEnv.LOCAL;
        } else if (hasProfile(activeProfiles, RunEnv.DEV)) {
            RUN_ENV = RunEnv.DEV;
        } else if (hasProfile(activeProfiles, RunEnv.PROD)) {
            RUN_ENV = RunEnv.PROD;
        } else {
            RUN_ENV = RunEnv.LOCAL;
        }

        START_PAGE_NO = ApplicationProperties.getIntProperty("global.start-page-no", 0);
        PAGE_SIZE = ApplicationProperties.getIntProperty("global.page-size", 10);
    }

    private static boolean hasProfile(String[] activeProfiles, RunEnv runEnv) {
        return Arrays.stream(activeProfiles)
                .anyMatch(s -> s.equals(runEnv.getEnv()));
    }

}
