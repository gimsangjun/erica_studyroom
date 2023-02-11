package com.example.demo;


import com.example.demo.domain.Order;

import java.time.LocalDate;

public class Test {

     public static void main(String[] args){
//          LocalDate currentTime = LocalDate.now();
//          System.out.println(currentTime.getDayOfMonth());
//          System.out.println(currentTime.getMonthValue());
          // TODO : 실수형 처리할때 조심.
          for (float i = 0.5F; i <= 1.5 ; i = (float) (i + 0.5)){
               System.out.println(i*2);
               System.out.println((int)i*2);
          }
          System.out.println((int)(0.5F *2));
     }
}
