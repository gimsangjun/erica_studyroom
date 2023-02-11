package com.example.demo.controller;

import com.example.demo.dto.request.OrderDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
public class TestController {

    @Data
    @Getter
    @Setter
    // TODO: error내용 non-static inner classes like this can only by instantiated using default
    // https://boomrabbit.tistory.com/212
    // 자바 내부 클래스 , 외부 클래스 관련 정리
    public static class OrderTestDTO {

        //@JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        // 예약시간.
        private float startTime;
        private float endTime;

    }

    @PostMapping("/test")
    public ResponseEntity localDate(@RequestBody OrderTestDTO dto){
        log.info("dto={}", dto);
        return ResponseEntity.ok("test");
    }

}


