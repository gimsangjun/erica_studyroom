package com.example.demo;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StudyRoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.StudyRoomService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class) // Junit4를 쓰는듯, 5에서는 어떻게?
@SpringBootTest
@Slf4j
class StudyRoomTests {

	@Autowired StudyRoomService studyRoomService;
	@Autowired StudyRoomRepository studyRoomRepository;
	@Autowired UserService userService;
	@Autowired UserRepository userRepository;
	@Autowired OrderRepository orderRepository;

	@Test
	void testJpa() {

		studyRoomRepository.deleteAll();
		userRepository.deleteAll();
		orderRepository.deleteAll();

		StudyRoom room1 = new StudyRoom();
		room1.setUniversity("소프트웨어융합대학");
		room1.setDepartment("소프트웨어학부");
		room1.setName("큐브0");
		room1.setLocation("제5공학관");
		room1.setCapacity(10);

		room1.setDescriptions(new HashSet<String>(Collections.singleton("투명한 벽으로 구분된 팀플실")));

		Set<String> caution = new HashSet<>();
		caution.add("음식물 반입금지");
		caution.add("시끄럽게 떠드는 행위 금지");
		room1.setCautions(caution);

		Set<String> drinks = new HashSet<>();
		drinks.add("커피");
		drinks.add("물");
		room1.setDrinks(drinks);

		Set<String> tags = new HashSet<>();
		tags.add("태그1");
		tags.add("태그2");
		room1.setTags(tags);

		// @ColumnDefault("0") 이게 먹히지 않는듯.
		room1.setCount(0);

		studyRoomRepository.save(room1);
		log.info("room1 ={}",room1);
		//studyRoomService.create(room1);

		StudyRoom room2 = new StudyRoom();
		room2.setUniversity("소프트웨어융합대학");
		room2.setDepartment("소프트웨어학부");
		room2.setName("큐브1");
		room2.setLocation("제5공학관");
		room2.setCapacity(20);
		room2.setCount(0);
		studyRoomRepository.save(room2);
		//studyRoomService.create(room2);

		StudyRoom room3 = new StudyRoom();
		room3.setUniversity("소프트웨어융합대학");
		room3.setDepartment("소프트웨어학부");
		room3.setName("큐브2");
		room3.setLocation("제5공학관");
		room3.setCapacity(5);
		room3.setCount(0);
		studyRoomRepository.save(room3);
		//studyRoomService.create(room3);

		User user = new User();
		user.setLoginId("aabbcde");
		user.setPassword("1234");
		user.setName("홍길동");
		user.setAge(15);
		user.setGrade(3);
		user.setEmail("ddee@gmail.com");
		user.setUniversity("소프트웨어융합대학");
		user.setDepartment("소프트웨어전공");
		userRepository.save(user);

		User user1 = new User();
		user1.setLoginId("aabbcde1");
		user1.setPassword("1234");
		user1.setName("광개토대왕");
		user1.setAge(18);
		user1.setGrade(4);
		user1.setEmail("daewang@gmail.com");
		user1.setUniversity("소프트웨어융합대학");
		user1.setDepartment("소프트웨어전공");
		userRepository.save(user1);

		//userService.create(user);

		LocalDate now = LocalDate.now();
		log.info("출력 year ={} month={} date={}",now.getYear(),now.getMonthValue(),now.getDayOfMonth());

		Order order = new Order();
		order.setUser(user);
		order.setStudyRoom(room1);
		order.setStartTime(15);
		order.setEndTime(17);

		order.setYear(now.getYear());
		order.setMonth(now.getMonthValue());
		order.setDate(now.getDayOfMonth());
		orderRepository.save(order);


		Order order1 = new Order();
		order1.setUser(user1);
		order1.setStudyRoom(room1);
		order1.setStartTime(17);
		order1.setEndTime(19);
		order1.setYear(now.getYear());
		order1.setMonth(now.getMonthValue());
		order1.setDate(now.getDayOfMonth());
		orderRepository.save(order1);

		studyRoomRepository.save(room1);
		//log.info("order ={}",order1);

	}


}
