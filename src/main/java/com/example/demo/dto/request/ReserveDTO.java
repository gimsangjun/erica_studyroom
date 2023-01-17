package com.example.demo.dto.request;


import lombok.Data;

@Data
public class ReserveDTO {
    // 스터디룸 id
    private long id;
    private int startTime;
    private int endTime;
}
