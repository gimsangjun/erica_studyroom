package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudyRoomForm {

    @NotBlank
    private String name;

    @NotBlank
    private int capacity;

    @NotBlank
    private String location;
}
