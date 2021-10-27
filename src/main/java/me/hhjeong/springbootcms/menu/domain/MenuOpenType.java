package me.hhjeong.springbootcms.menu.domain;

public enum MenuOpenType {

    CURRENT("C"), NEW("N");

    private String menuOpenType;

    MenuOpenType(String menuOpenType) {
        this.menuOpenType = menuOpenType;
    }

    public String getMenuOpenType() {
        return menuOpenType;
    }
}
