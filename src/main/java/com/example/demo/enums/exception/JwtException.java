package com.example.demo.enums.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtException {

    WRONG_TOKEN(0, "Wrong Token"),
    EXPIRED_TOKEN(1, "Expired Token"),
    SIGNATURE_ERROR(2, "Signature Error");

    private final int code;
    private String message;

    JwtException(int code, String message){
        this.code = code;
        this.message = message;
    }

}
