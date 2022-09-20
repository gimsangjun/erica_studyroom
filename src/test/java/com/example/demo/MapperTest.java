package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.dto.SignUpForm;
import com.example.demo.test.Team;
import com.example.demo.test.TeamDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // Junit4를 쓰는듯, 5에서는 어떻게?
@SpringBootTest
@Slf4j
public class MapperTest {


    // 테스트할때는 객체 주입이 안되는거 같은데, 이렇게 하니까 됨.
    //ModelMapper modelMapper = new ModelMapper();

    @Autowired ModelMapper modelMapper;

//    @Test
//    public void modelMapper1() { // 연관관계가 없는 경우
//        Team team = new Team();
//        team.setName("team1");
//        team.setValue(15);
//
//        TeamDto teamDto = modelMapper.map(team,TeamDto.class);
//        log.info("team ={}",team.toString());
//        log.info("teamDto ={}",teamDto.toString());
//
//    }

    @Test
    public void user_to_signUpForm(){
        User user = new User();
        user.setLoginId("aabbcde");
        user.setPassword("1234");
        user.setName("홍길동");
        user.setAge(15);
        user.setGrade(3);
        user.setEmail("ddee@gmail.com");
        user.setUniversity("소프트웨어융합대학");
        user.setDepartment("소프트웨어전공");

        SignUpForm form = new SignUpForm();
        form = modelMapper.map(user,SignUpForm.class);

        log.info("user={}",user);
        log.info("form={}", form);

        Assertions.assertEquals(form.getName(),user.getName());
        Assertions.assertEquals(form.getLoginId(),user.getLoginId());
        Assertions.assertEquals(form.getPassword(),user.getPassword());
        Assertions.assertEquals(form.getAge(),user.getAge());
        Assertions.assertEquals(form.getGrade(),user.getGrade());
        Assertions.assertEquals(form.getEmail(),user.getEmail());
        Assertions.assertEquals(form.getUniversity(),user.getUniversity());
        Assertions.assertEquals(form.getDepartment(),user.getDepartment());
    }


    /**
     * modelMapper를 이용한 컨버터 테스트
     * Id값도 있어서 잘 작동하지 않는듯...
     */
    @Test
    public void signUpForm_to_user(){
        SignUpForm form = new SignUpForm();
        form.setLoginId("aabbcde");
        form.setPassword("1234");
        form.setName("홍길동");
        form.setAge(15);
        form.setGrade(3);
        form.setEmail("ddee@gmail.com");
        form.setUniversity("소프트웨어융합대학");
        form.setDepartment("소프트웨어전공");

        User user = new User();
        user = modelMapper.map(form,User.class);

        log.info("user={}",user);
        log.info("form={}", form);

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
