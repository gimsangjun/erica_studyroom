package com.example.demo.service;

import com.example.demo.DataNotFoundException;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.StudyRoomAPI;
import com.example.demo.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ArrayList<StudyRoom> getAllStudyRoom(){
        List<StudyRoom> temps = this.studyRoomRepository.findAll();
        ArrayList<StudyRoom> studyRooms = new ArrayList<StudyRoom>();
        studyRooms.addAll(temps);
        return studyRooms;
    }

    public void create(StudyRoom room){
        Optional<StudyRoom> studyRoom = studyRoomRepository.findByName(room.getName());
        this.studyRoomRepository.save(room);
    }

    public void delete(StudyRoom studyRoom) {this.studyRoomRepository.delete(studyRoom);}

    public void modify(StudyRoom studyRoom, StudyRoomAPI studyRoomAPI) {
        studyRoom.setUniversity(studyRoomAPI.getUniversity());
        studyRoom.setDepartment(studyRoomAPI.getDepartment());
        studyRoom.setLocation(studyRoomAPI.getLocation());
        studyRoom.setName(studyRoomAPI.getStudyroom_name());
        studyRoom.setCapacity(studyRoomAPI.getCapacity());
        this.studyRoomRepository.save(studyRoom);
    }

}
