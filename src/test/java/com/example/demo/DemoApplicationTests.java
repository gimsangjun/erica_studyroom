package com.example.demo;

import com.example.demo.domain.StudyRoom;
import com.example.demo.repository.StudyRoomRepository;
import com.example.demo.repository.StudyUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private StudyRoomRepository studyRoomRepository;
	@Autowired
	private StudyUserRepository studyUserRepository;

	@Test
	void testJpa() {

		studyRoomRepository.deleteAll();
		studyRoomRepository.flush();

		StudyRoom studyRoom1 = new StudyRoom();
		studyRoom1.setName("팀플실명1");
		studyRoom1.setCapacity(10);
		studyRoom1.setorder("김상준1");
		this.studyRoomRepository.save(studyRoom1);


		// 로그인 기능 만들면 다시.
//		StudyUser user1 = new StudyUser();
//		user1.setAttending("재학중");
//		user1.setGrade(3);
//		user1.setName("김상준1");
//		this.studyUserRepository.save(user1);
//
//		StudyRoom room1 = new StudyRoom();
//		room1.setName("팀플실이름1");
//		room1.setCapacity(10);
//		room1.setorder(user1);
//		this.studyRoomRepository.save(room1);
//
//		// OneToOne이라 user2를 또 새로 만들어야됨.
//		StudyUser user2 = new StudyUser();
//		user2.setAttending("재학중");
//		user2.setGrade(3);
//		user2.setName("김상준2");
//		this.studyUserRepository.save(user2);
//
//		StudyRoom room2 = new StudyRoom();
//		room2.setName("팀플실이름2");
//		room2.setCapacity(20);
//		room2.setorder(user2);
//		this.studyRoomRepository.save(room2);
	}

}
