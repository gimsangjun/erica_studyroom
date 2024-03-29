package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Getter
@Builder
public class OrderRequest {

    @NotNull
    private LocalDate date;
    // 예약시간.
    @NotNull
    private Double startTime;
    @NotNull
    private Double endTime;
    // 예약인원
    @NotNull
    private Integer bookingCapacity;

}
