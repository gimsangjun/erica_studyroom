package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // TODO : 무슨뜻일까?
// 아직 toString떄문에 오류가 나서 제대로 활용못함.
public class UserListResponse {

    private final List<UserResponse> userList;
}
