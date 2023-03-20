package com.example.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderState {

    CANCEL("취소"),
    NORMAL("정상"),
    RETURN("반납");

    private final String state;

}
