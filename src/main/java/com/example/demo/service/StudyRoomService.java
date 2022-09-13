package com.example.demo.service;

import com.example.demo.DataNotFoundException;
import com.example.demo.domain.StudyRoom;
import com.example.demo.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }

    public StudyRoom getStudyRoom(Long id){
        Optional<StudyRoom> studyRoom = this.studyRoomRepository.findById(id);
        if(studyRoom.isPresent()){
            return studyRoom.get();
        }else {
            throw new DataNotFoundException("StudyRoom not found");
        }
    }

    public void create(StudyRoom studyRoom){
        this.studyRoomRepository.save(studyRoom);
    }

    public void delete(StudyRoom studyRoom) {this.studyRoomRepository.delete(studyRoom);}

//    public void modify(StudyRoom studyRoom,String name, int capacity, Order order) {
//        studyRoom.setName(name);
//        studyRoom.setCapacity(capacity);
//        studyRoom.setorder(order);
//        this.studyRoomRepository.save(studyRoom);
//    }

}
