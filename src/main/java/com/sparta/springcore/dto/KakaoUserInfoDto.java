package com.sparta.springcore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class KakaoUserInfoDto {
    private final Long id;
    private String nickname;
    private String email;

    /* Lombok - @AllargsConstructor 가 생성해준다
    public KakaoUserInfoDto(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

     */
}
