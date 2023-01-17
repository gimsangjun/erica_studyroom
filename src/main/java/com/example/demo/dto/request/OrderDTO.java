package com.example.demo.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDTO {

    private int year ;
    private int month;
    private int date;
    // 예약시간.
    private int startTime;
    private int endTime;

}
