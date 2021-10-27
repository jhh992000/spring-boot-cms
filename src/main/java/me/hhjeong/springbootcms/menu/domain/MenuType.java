package me.hhjeong.springbootcms.menu.domain;

public enum MenuType {

    EMPTY("E"), PROGRAM("P"), CONTENT("C"), BOARD("B"), INNER_LINK("I"), EXTERNAL_LINK("L");

    private String type;

    MenuType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
