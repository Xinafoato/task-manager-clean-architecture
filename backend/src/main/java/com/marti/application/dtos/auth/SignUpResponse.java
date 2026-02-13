package com.marti.application.dtos.auth;

public class SignUpResponse {

    private final UserInfoDTO userInfo;

    public SignUpResponse(String userId, String username, String email) {
        this.userInfo = new UserInfoDTO(userId, username, email);
    }

    public UserInfoDTO getUserInfo() {
        return  userInfo;
    }
}

