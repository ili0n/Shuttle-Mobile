package com.example.shuttlemobile.user.dto;

import java.io.Serializable;

public class PasswordResetDTO implements Serializable {
    private String new_password;
    private String code;

    public PasswordResetDTO(String new_password, String code) {
        this.new_password = new_password;
        this.code = code;
    }

    public PasswordResetDTO() {
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PasswordResetDTO{" +
                "new_password='" + new_password + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
