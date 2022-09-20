package com.example.demo;

import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
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

	@Test
	void testJpa() {

		studyRoomRepository.deleteAll();
		userRepository.deleteAll();

		StudyRoom room1 = new StudyRoom();
		room1.setName("큐브0");
		room1.setLocation("제5학관");
		room1.setCapacity(10);
		studyRoomRepository.save(room1);
		//studyRoomService.create(room1);

		StudyRoom room2 = new StudyRoom();
		room2.setName("큐브1");
		room2.setLocation("제5학관");
		room2.setCapacity(10);
		studyRoomRepository.save(room2);
		//studyRoomService.create(room2);

		StudyRoom room3 = new StudyRoom();
		room3.setName("큐브2");
		room3.setLocation("제5학관");
		room3.setCapacity(10);
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
		//userService.create(user);
	}


}
