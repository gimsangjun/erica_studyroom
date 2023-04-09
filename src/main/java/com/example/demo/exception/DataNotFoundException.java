package com.example.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class DataNotFoundException extends RuntimeException{
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private final String message;
}
