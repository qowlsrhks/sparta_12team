package com.api.domain.users.dto;

import com.api.domain.users.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyProfileResponseDto {
    private Long userId;
     private String username;
     private String email;
     private String introduce;
     private int friendNumber;
     private String password;

     public MyProfileResponseDto(User user){
         this.userId = user.getId();
         this.username = user.getUsername();
         this.email = user.getEmail();
         this.introduce = user.getIntroduce();
         this.friendNumber = user.getFriends().size();
         this.password = user.getPassword();
     }
}
