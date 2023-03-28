package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Builder
public class OrderDTO {

    private LocalDate date;
    // 예약시간.
    private double startTime;
    private double endTime;
    // 예약인원
    private int bookingCapacity;

}
