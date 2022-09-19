package com.example.demo.test;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private int id;
    private String name;
    private int teamId;
}