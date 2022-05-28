package me.hhjeong.springbootcms.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDto {

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("username")
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    @JsonProperty("password")
    private String password;

    public LoginDto() {
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
