package com.marti.application.dtos.auth;

public class LogInResponse {

    private final UserInfoDTO userInfo;

    public LogInResponse(String userId, String username, String email) {
        this.userInfo = new UserInfoDTO(userId, username, email);
    }

    public UserInfoDTO getUserInfo() {
        return  userInfo;
    }
}
