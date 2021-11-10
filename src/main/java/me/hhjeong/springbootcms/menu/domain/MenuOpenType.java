package me.hhjeong.springbootcms.menu.domain;

public enum MenuOpenType {

    CURRENT("C"), NEW("N");

    private final String typeCode;

    MenuOpenType(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }
}
