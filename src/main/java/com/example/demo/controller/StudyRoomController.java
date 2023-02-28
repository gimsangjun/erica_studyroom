package com.example.demo.controller;

import com.example.demo.DataNotFoundException;
import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.dto.request.StudyRoomDTO;
import com.example.demo.dto.response.StudyRoomResponseDTO;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}, allowedHeaders = {"authorization", "content-type","ngrok-skip-browser-warning"},exposedHeaders = "authorization",allowCredentials = "true", maxAge = 3000)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/studyRoom")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    /**
     * @return 모든 팀플실을 리스트형태로 반환
     */
    @GetMapping()
    public ResponseEntity<Object> read(){
        // 아래와 같이 쉽게 처리하고싶은데 쉽지가 않음.
//        final StduyRoomListResponseDTO stduyRoomListResponseDTO = StduyRoomListResponseDTO.builder()
//                .studyRoomList(studyRoomService.getAllStudyRoom()).build();
        List<StudyRoom> studyRoomList = studyRoomService.findAll();
        ArrayList<StudyRoomResponseDTO> list = new ArrayList<>();

        // Todo : 조금더 효율적인 방법은?
        for (StudyRoom studyRoom : studyRoomList){
            LinkedHashMap map = new LinkedHashMap();
            StudyRoomResponseDTO studyRoomResponseDTO = modelMapper.map(studyRoom, StudyRoomResponseDTO.class);
            list.add(studyRoomResponseDTO);
        }

        return ResponseEntity.ok(list);
    }
    /**
     *
     * @return 해당 팀플실 detail, 날짜만 넘어오면 해당 날짜의 에약현황 리턴
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Long id ,
                                         @RequestParam()
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date){
        // TODO: 대부분의 로직 나중에 Service부분으로 옮겨야됨
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        StudyRoomDTO studyRoomDTO = modelMapper.map(studyRoom, StudyRoomDTO.class);
        List<Order> orders ;

        // 예약내용 추가 - reservation
        ArrayList<LinkedHashMap> list = new ArrayList<>();

        // date가 param형태로 넘어왔다면
        if (date.isPresent()){
            // 해당 팀플실의 특정날짜의 orders 가져오기
            orders = orderService.getByStudyRoomAndDate(studyRoom, date.get());
            for(Order order : orders){
                list.add(orderToResponse(order));
            }

        } else { // Parameter가 넘어오지 않았을 때
            // 특정 팀플실의 예약현황 모두 가져오기
            orders = orderService.getByStudyRoom(studyRoom);
           // TODO: date가 파라미터형태로 넘어올떄와 다르게 order를 가져옴. 그래서 다르게 정렬해야됨.
            for(Order order : orders){
                list.add(orderToResponse(order));
            }
        }

        LinkedHashMap map = new LinkedHashMap();
        map.put("info",studyRoomDTO);
        map.put("reservation",list);
        return ResponseEntity.ok(map);
    }

    /**
     * 팀플실 생성
     */
    @PostMapping
    public ResponseEntity<Object> createStudyRoom(@Valid @RequestBody StudyRoomDTO studyRoomDTO){
        StudyRoom studyRoom = new StudyRoom();
        studyRoom = modelMapper.map(studyRoomDTO,StudyRoom.class);
        this.studyRoomService.create(studyRoom);
        return ResponseEntity.ok(studyRoom);
    }

    /**
     * 팀플실 수정
     */
    @PutMapping ("/{id}")
    public ResponseEntity modify(@RequestBody StudyRoomDTO studyRoomDTO, @PathVariable("id") Long id){
        // TODO: 조금더 고급적인 방법이 있는지
        try{
            StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
            this.studyRoomService.modify(studyRoom,studyRoomDTO);
            // TODO: studyRoom을 return 하고싶은데 stackoverflow생겨서 이 문제 쉽게 해결하는 방법알면 다시 수정
            return ResponseEntity.ok("수정성공");
        } catch (DataNotFoundException e){ // 다른예외는 어떻게 처리해야하는지 잘모루
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 팀플실입니다.");
        }

    }
    /**
     * 팀플실 삭제.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        // TODO: 조금더 고급적인 방법이 있는지
        try{
            StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
            this.studyRoomService.delete(studyRoom);
            return ResponseEntity.ok("삭제성공");
        } catch (DataNotFoundException e){ // 다른예외는 어떻게 처리해야하는지 잘모루
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 팀플실입니다.");
        }

    }

    /**
     * 팀플실예약
     */
    @PostMapping("/{id}")
    public ResponseEntity reserve(Authentication authentication, @RequestBody OrderDTO orderDTO, @PathVariable("id") Long id){
        // TODO: 조금더 고급적인 방법이 있는지
        try{
            StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
            // 해당 날짜의 예약이 이미 차있는지 확인
            if(!studyRoomService.check(studyRoom,orderDTO)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 예약되어 있습니다.");
            }
            // 현재 접근한 사용자의 정보를 가져옴.
            User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser();
            orderService.reserve(studyRoom,orderDTO,user.getUsername());
            return ResponseEntity.ok("예약완료");
        } catch (DataNotFoundException e){ // 다른예외는 어떻게 처리해야하는지 잘모루
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 팀플실입니다.");
        }
    }

    // order를 리턴할수있게 변경.
    // TODO: 시간대 별로 정렬을 해야하는데 정렬이 아직 안됨.
    public LinkedHashMap<String,String> orderToResponse(Order order){
        LinkedHashMap reservation = new LinkedHashMap<>();
        reservation.put("orderId",order.getId());
        reservation.put("date",order.getDate());
        reservation.put("name",order.getUser().getNickname());
        reservation.put("startTime",order.getStartTime());
        reservation.put("endTime",order.getEndTime());
        return reservation;
    }



}
