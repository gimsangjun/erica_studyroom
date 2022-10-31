package com.example.demo.dto;

import lombok.Data;

/**
 * GET /api/studyroom/{id} 에서 request body에서 날짜정보를 가져오기 위함.
 */
@Data
public class RerserveDate {
    private int year ;
    private int month;
    private int date;
}
