package com.example.demo;

import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.StudyRoomAPI;
import com.example.demo.dto.UserAPI;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(User.class,UserAPI.class).addMappings(mapper -> {
            mapper.map(src-> src.getLoginId(), UserAPI::setUser_id);
            mapper.map(src-> src.getName(),UserAPI::setUser_name);
        });
        // TODO: addMappings? 사용법
        modelMapper.createTypeMap(StudyRoom.class, StudyRoomAPI.class).addMapping(StudyRoom::getName,StudyRoomAPI::setStudyroom_name);
        modelMapper.createTypeMap(StudyRoomAPI.class, StudyRoom.class).addMapping(StudyRoomAPI::getStudyroom_name,StudyRoom::setName);
        return modelMapper;
    }


}
