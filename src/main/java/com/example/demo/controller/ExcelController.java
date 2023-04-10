package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3000)
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {

    private final OrderService orderService;
    private final StudyRoomService studyRoomService;


    @GetMapping("/order")
    public void downloadExcelOrder(HttpServletResponse response) throws IOException {
        // DB에서 정보 조회
        List<Order> orders = orderService.findByCriteria(null,null,null, null);
        log.info("orders ={}", orders.size());

        // Workbook 객체 생성
        // Workbook workbook = new HSSFWorkbook(); // .xls형식
        XSSFWorkbook workbook = new XSSFWorkbook(); // .xlsx형식

        // 시트 생성
        Sheet sheet = workbook.createSheet("orders");

        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID"); // username
        headerRow.createCell(1).setCellValue("이름"); // name
        headerRow.createCell(2).setCellValue("학과");
        headerRow.createCell(3).setCellValue("팀플실 이름"); // 팀플실 name
        headerRow.createCell(4).setCellValue("대학"); // university
        headerRow.createCell(5).setCellValue("건물"); // building
        headerRow.createCell(6).setCellValue("위치"); // location
        headerRow.createCell(7).setCellValue("예약 인원"); // bookingCapacity
        headerRow.createCell(8).setCellValue("예약 날짜"); // date
        headerRow.createCell(9).setCellValue("예약 시작시간"); // startTime
        headerRow.createCell(10).setCellValue("예약 종료시간"); // endTime

        // 데이터 생성
        int rowNumber = 1;
        for (Order order : orders){
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(order.getUser().getUsername()); // username
            row.createCell(1).setCellValue(order.getUser().getName()); // name
            row.createCell(2).setCellValue(order.getUser().getUniversity()); // 학과
            row.createCell(3).setCellValue(order.getStudyRoom().getName()); // 팀플실 name
            row.createCell(4).setCellValue(order.getStudyRoom().getUniversity()); // university
            row.createCell(5).setCellValue(order.getStudyRoom().getBuilding()); // building
            row.createCell(6).setCellValue(order.getStudyRoom().getLocation()); // location
            row.createCell(7).setCellValue(order.getBookingCapacity()); // bookingCapacity
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            row.createCell(8).setCellValue(formatter.format(order.getDate())); // date
            row.createCell(9).setCellValue(order.getStartTime()); // startTime
            row.createCell(10).setCellValue(order.getEndTime()); // endTime
        }

        // 엑셀 파일생성
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx;");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
