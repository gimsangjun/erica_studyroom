package com.example.demo;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.repository.OrderRepsitory;
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

@RunWith(SpringRunner.class) // Junit4를 쓰는듯, 5에서는 어떻게?
@SpringBootTest
@Slf4j
class StudyRoomTests {

	@Autowired StudyRoomService studyRoomService;
	@Autowired StudyRoomRepository studyRoomRepository;
	@Autowired UserService userService;
	@Autowired UserRepository userRepository;
	@Autowired OrderRepsitory orderRepsitory;

	@Test
	void testJpa() {

		studyRoomRepository.deleteAll();
		userRepository.deleteAll();
		orderRepsitory.deleteAll();

		StudyRoom room1 = new StudyRoom();
		room1.setUniversity("소프트웨어융합대학");
		room1.setDepartment("소프트웨어학부");
		room1.setName("큐브0");
		room1.setLocation("제5공학관");
		room1.setCapacity(10);
		studyRoomRepository.save(room1);
		//studyRoomService.create(room1);

		StudyRoom room2 = new StudyRoom();
		room2.setUniversity("소프트웨어융합대학");
		room2.setDepartment("소프트웨어학부");
		room2.setName("큐브1");
		room2.setLocation("제5공학관");
		room2.setCapacity(20);
		studyRoomRepository.save(room2);
		//studyRoomService.create(room2);

		StudyRoom room3 = new StudyRoom();
		room3.setUniversity("소프트웨어융합대학");
		room3.setDepartment("소프트웨어학부");
		room3.setName("큐브2");
		room3.setLocation("제5공학관");
		room3.setCapacity(5);
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

		Order order =new Order();
		order.setUser(user);
		order.setStudyRoom(room1);
		order.setStartTime(1500);
		order.setEndTime(1700);
		orderRepsitory.save(order);

		Order order1 =new Order();
		order1.setUser(user1);
		order1.setStudyRoom(room1);
		order1.setStartTime(1700);
		order1.setEndTime(1900);
		orderRepsitory.save(order1);

	}


}
