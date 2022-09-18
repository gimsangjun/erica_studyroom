package com.example.demo;

import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.SignUpForm;
import com.example.demo.repository.StudyRoomRepository;
import com.example.demo.service.StudyRoomService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
@Slf4j
class StudyRoomTests {

	private static StudyRoomService studyRoomService;
	private static StudyRoomRepository studyRoomRepository;
	private static UserService userService;
	private static ModelMapper modelMapper;


	@Test
	void testJpa() {

		studyRoomRepository.deleteAll();

		StudyRoom room1 = new StudyRoom();
		room1.setName("큐브0");
		room1.setLocation("제5학관");
		room1.setCapacity(10);
		studyRoomService.create(room1);

		StudyRoom room2 = new StudyRoom();
		room2.setName("큐브1");
		room2.setLocation("제5학관");
		room2.setCapacity(10);
		studyRoomService.create(room2);

		StudyRoom room3 = new StudyRoom();
		room3.setName("큐브2");
		room3.setLocation("제5학관");
		room3.setCapacity(10);
		studyRoomService.create(room1);

//		User user = new User();
//		user.setLoginId("aabbcde");
//		user.setPassword("1234");
//		user.setName("홍길동");
//		user.setAge(15);
//		user.setGrade(3);
//		user.setEmail("ddee@gmail.com");
//		user.setUniversity("소프트웨어융합대학");
//		user.setDepartment("소프트웨어전공");
		//userService.create(user);
	}

	/**
	 * modelMapper를 이용한 컨버터 테스트
	 * Id값도 있어서 잘 작동하지 않는듯...
	 */
	@Test
	public void test_convert_signUpForm_to_user(){
//		SignUpForm form = new SignUpForm();
//		form.setLoginId("aabbcde");
//		form.setPassword("1234");
//		form.setName("홍길동");
//		form.setAge(15);
//		form.setGrade(3);
//		form.setEmail("ddee@gmail.com");
//		form.setUniversity("소프트웨어융합대학");
//		form.setDepartment("소프트웨어전공");

//		User user = new User();
//		user = modelMapper.map(form,User.class);

		User user = new User();
		user.setLoginId("aabbcde");
		user.setPassword("1234");
		user.setName("홍길동");
		user.setAge(15);
		user.setGrade(3);
		user.setEmail("ddee@gmail.com");
		user.setUniversity("소프트웨어융합대학");
		user.setDepartment("소프트웨어전공");

		log.info("user={}",user);

		SignUpForm form = new SignUpForm();
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		form = modelMapper.map(user,SignUpForm.class);

		log.debug("form={}", form);


		Assertions.assertEquals(form.getName(),user.getName());
		Assertions.assertEquals(form.getLoginId(),user.getLoginId());
		Assertions.assertEquals(form.getPassword(),user.getPassword());
		Assertions.assertEquals(form.getAge(),user.getAge());
		Assertions.assertEquals(form.getGrade(),user.getGrade());
		Assertions.assertEquals(form.getEmail(),user.getEmail());
		Assertions.assertEquals(form.getUniversity(),user.getUniversity());
		Assertions.assertEquals(form.getDepartment(),user.getDepartment());

	}

}
