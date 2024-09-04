package com.api.domain.users.dto;

import com.api.domain.users.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
     private String username;
     private String email;
     private String introduce;

     public UserResponseDto(User user){
         this.username = user.getUsername();
         this.email = user.getEmail();
         this.introduce = user.getIntroduce();
     }
}
