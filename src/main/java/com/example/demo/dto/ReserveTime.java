package com.example.demo.dto;


import lombok.Data;

@Data
public class ReserveTime {
    // 스터디룸 id
    private long id;
    private int startTime;
    private int endTime;
}
