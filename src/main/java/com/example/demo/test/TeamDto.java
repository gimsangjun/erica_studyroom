package com.example.demo.test;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private int id;
    private String name;
    private int value;
}