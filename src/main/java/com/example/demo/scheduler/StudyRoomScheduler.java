package com.example.demo.scheduler;

import com.example.demo.domain.Order;
import com.example.demo.enums.OrderState;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudyRoomScheduler {

    private final OrderService orderService;

    // 30분마다 실행하여 시간이 지난 예약내용을 '반납' 상태로 바꾸기
    @Scheduled(cron = "* 30 * * * *", zone = "Asia/Seoul")
    public void scheduleStudyRoomReturn() {
        // 오늘 날짜의 state가 정상인 order들을 모두 불러옴.
        List<Order> orders = orderService.getOrderByStateAndDate(OrderState.NORMAL.getState(), LocalDate.now());
        // 현재시간을 시간.분 의 형태로 바꿈.
        double currentTime = (double) LocalDateTime.now().getHour() + (double) LocalDateTime.now().getMinute() * 0.01;
        for(Order order : orders){
            if( Double.compare(order.getEndTime(), currentTime) <= 0 ){
                orderService.return_(order);
            }
        }
    }

}
