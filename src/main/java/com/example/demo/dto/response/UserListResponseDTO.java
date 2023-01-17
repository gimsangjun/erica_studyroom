package com.example.demo.dto.response;

import com.example.demo.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // TODO : 무슨뜻일까?
public class UserListResponseDTO {

    private final List<User> userList;
}
