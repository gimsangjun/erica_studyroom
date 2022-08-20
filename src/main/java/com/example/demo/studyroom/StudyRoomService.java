package com.example.demo.studyroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }

    public void create(StudyRoom studyRoom){
        this.studyRoomRepository.save(studyRoom);
    }

}
