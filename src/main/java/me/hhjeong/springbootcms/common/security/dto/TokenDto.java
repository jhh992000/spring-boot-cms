package me.hhjeong.springbootcms.common.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDto {

    @JsonProperty("token")
    private String token;

    public TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
