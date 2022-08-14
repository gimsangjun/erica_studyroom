package com.example.demo;

import lombok.Data;


@Data
public class StudyRoom {

    private int id;

    private String name;

    private int capacity;

    public StudyRoom(int id ,String name , int capacity){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

}
