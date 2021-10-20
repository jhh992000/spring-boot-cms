package me.hhjeong.springbootcms.common.base;

public enum RunEnv {

    LOCAL("local"), DEV("dev"), PROD("prod");

    RunEnv(String env) {
        this.env = env;
    }

    private String env;

    public String getEnv() {
        return env;
    }

    @Override
    public String toString() {
        return this.env;
    }
}
