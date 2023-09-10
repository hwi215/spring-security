package com.spring.security.dto.request;

import com.spring.security.config.Role;
import com.spring.security.domain.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class JoinDto {
    private String id;

    private String password;

    private String name;

    private String email;

    private String phone;

    // 생성할 때 default로 0 넣어주기
    private int state;

    private String registNumber;

    private Role role;

    @Builder
    public Student of(){
        return Student.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .state(state)
                .role(Role.STUDENT)
                .registNumber(registNumber)
                .build();
    }
}
