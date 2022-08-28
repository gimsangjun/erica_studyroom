package com.example.demo.service;

import com.example.demo.dto.StudyUser;
import com.example.demo.repository.StudyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyUserService {

    private final StudyUserRepository studyUserRepository;

    public List<StudyUser> getList(){ return this.studyUserRepository.findAll();}

}
