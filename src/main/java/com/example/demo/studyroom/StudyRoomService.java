package com.example.demo.studyroom;

import com.example.demo.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }

    public StudyRoom getStudyRoom(Integer id){
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

    public void modify(StudyRoom studyRoom,String name, int capacity, String client) {
        studyRoom.setName(name);
        studyRoom.setCapacity(capacity);
        studyRoom.setClient(client);
        this.studyRoomRepository.save(studyRoom);
    }

}
