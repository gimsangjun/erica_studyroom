package com.example.demo;

import com.example.demo.studyroom.StudyRoom;
import com.example.demo.studyroom.StudyRoomRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private StudyRoomRepository studyRoomRepository;

	@Before("")
	public void init() {
		studyRoomRepository.deleteAll();
		studyRoomRepository.flush();
	}

	@Test
	void testJpa() {


		StudyRoom room1 = new StudyRoom();
		room1.setName("팀플실이름1");
		room1.setCapacity(10);
		room1.setClient("김상준");

		this.studyRoomRepository.save(room1);

		StudyRoom room2 = new StudyRoom();
		room2.setName("팀플실이름2");
		room2.setCapacity(20);
		room2.setClient("김상준2");

		this.studyRoomRepository.save(room2);
	}

}
