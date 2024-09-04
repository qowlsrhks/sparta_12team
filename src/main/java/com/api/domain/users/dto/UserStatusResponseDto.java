package com.api.domain.users.dto;

import com.api.domain.users.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStatusResponseDto {
     private String username;
     private String email;
     private String introduce;
     private boolean isMember;

     public UserStatusResponseDto(User user){
         this.username = user.getUsername();
         this.email = user.getEmail();
         this.introduce = user.getIntroduce();
         this.isMember = user.isMember();
     }
}
