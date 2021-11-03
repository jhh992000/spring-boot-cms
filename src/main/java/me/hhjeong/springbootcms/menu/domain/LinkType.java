package me.hhjeong.springbootcms.menu.domain;

public enum LinkType {

    EXTERNAL("E"), INTERNAL("I");

    private String type;

    LinkType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
