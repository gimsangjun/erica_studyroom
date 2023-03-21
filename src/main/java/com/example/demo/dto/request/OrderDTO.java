package com.example.demo.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class OrderDTO {

    private LocalDate date;
    // 예약시간.
    private double startTime;
    private double endTime;

}
