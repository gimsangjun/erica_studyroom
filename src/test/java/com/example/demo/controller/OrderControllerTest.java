package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.enums.OrderState;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("OrderController 클래스의 ")
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private StudyRoomService studyRoomService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    @DisplayName("모든Order조회_성공")
    void  모든Order조회_성공() throws Exception{
        // given(준비) : 어떠한 데이터가 준비되었을 떄
        final String url = "/api/order";

        // TODO: 이걸 BeforeEach로 옮길수는 없을까?
        StudyRoom studyRoom = createStudyRoom("소프트웨어융합대학", "학연산클러스터지원센터", 4);
        User user = createUser("test","홍길동");
        List<Order> orderList = new ArrayList<>();
        LocalDate date = LocalDate.of(2023,4,13);
        for (int i = 0 ; i < 5; i++){
            orderList.add(createOrder(user, studyRoom, date, 10.5 + i, 11.5 + i));
        }

        doReturn(orderList).when(orderService).findByCriteria(null, null,null ,null, OrderState.NORMAL);

        // when(실행) : 어떠한 함수를 실행하면
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        // then(검증) : 어떠한 결과가 나와야 한다.
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정Order조회_성공OR실패")
    void 특정Order조회_성공OR실패() throws Exception {
        // given(준비) : 어떠한 데이터가 준비되었을 떄
        final String url = "/api/order";

        // TODO: 이걸 BeforeEach로 옮길수는 없을까?
        StudyRoom studyRoom = createStudyRoom("소프트웨어융합대학", "학연산클러스터지원센터", 4);
        User user = createUser("test","홍길동");
        List<Order> orderList = new ArrayList<>();
        List<Order> orderList2 = new ArrayList<>();
        LocalDate date = LocalDate.of(2023,4,13);
        LocalDate date2 = LocalDate.of(2023,4,14);
        LocalDate date3 = LocalDate.of(2023,4,15);
        for (int i = 0 ; i < 5; i++){
            orderList.add(createOrder(user, studyRoom, date, 10.5 + i, 11.5 + i));
            orderList2.add(createOrder(user, studyRoom, date2, 10.5 + i, 11.5 + i));
        }
        doReturn(orderList2).when(orderService).findByCriteria(null, null,null ,date2, OrderState.NORMAL);
        doThrow(DataNotFoundException.class).when(orderService).findByCriteria(null, null,null ,date3, OrderState.NORMAL);

        // when(실행) : 어떠한 함수를 실행하면
        // 4월 14일 order조회 성공
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", String.valueOf(date2))
        );
        // 4월 15일 order조회시 no content
        ResultActions resultActions2 = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", String.valueOf(date3))
        );

        // then(검증) : 어떠한 결과가 나와야 한다.
        resultActions.andExpect(status().isOk());
        resultActions2.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Order디테일확인")
    void Order디테일확인() throws Exception {
        // given(준비) : 어떠한 데이터가 준비되었을 떄
        final String url = "/api/order/1";

        StudyRoom studyRoom = createStudyRoom("소프트웨어융합대학", "학연산클러스터지원센터", 4);
        User user = createUser("test","홍길동");
        List<Order> orderList = new ArrayList<>();
        LocalDate date = LocalDate.of(2023,4,13);
        for (int i = 0 ; i < 5; i++){
            orderList.add(createOrder(user, studyRoom, date, 10.5 + i, 11.5 + i));
        }

        doReturn(orderList.get(0)).when(orderService).getOrder(1L);

        // when(실행) : 어떠한 함수를 실행하면
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON));

        // then(검증) : 어떠한 결과가 나와야 한다.
        Order order = orderList.get(0);
        resultActions
                .andExpect(jsonPath("building",order.getStudyRoom().getBuilding()).exists())
                .andExpect(jsonPath("name", order.getUser().getName()).exists())
                .andExpect(jsonPath("date", order.getDate().toString()).exists())
                .andExpect(jsonPath("startTime", order.getStartTime().toString()).exists())
                .andExpect(jsonPath("endTime", order.getEndTime().toString()).exists())
        ;

    }

    // 예약삭제

    // 예약수정

    // 예약반납

    private StudyRoom createStudyRoom(String university, String building, int capacity){
        return StudyRoom.builder()
                .university(university)
                .building(building)
                .capacity(capacity)
                .build();
    }

    private Order createOrder(User user, StudyRoom studyRoom ,LocalDate date, Double startTime, Double endTime){
        return Order.builder()
                .user(user)
                .studyRoom(studyRoom)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .state(OrderState.NORMAL)
                .build();
    }

    private User createUser(final String username, final String name){
        return User.builder()
                .username(username)
                .name(name)
                .build();
    }


}