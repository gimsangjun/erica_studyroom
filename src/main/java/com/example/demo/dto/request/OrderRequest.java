package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
@Builder
public class OrderRequest {

    private LocalDate date;
    // 예약시간.
    private double startTime;
    private double endTime;
    // 예약인원
    private int bookingCapacity;

}
