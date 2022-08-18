package com.example.demo.studyuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyUserService {

    private final StudyUserRepository studyUserRepository;

    public List<StudyUser> getList(){ return this.studyUserRepository.findAll();}

}
