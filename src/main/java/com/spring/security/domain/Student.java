package com.spring.security.domain;

import com.spring.security.config.Role;
import com.spring.security.config.UserState;
import com.spring.security.dto.request.JoinDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_seq")
    @SequenceGenerator(name="student_seq", sequenceName = "student_seq", allocationSize = 1)
    @Column(name="student_num")
    private Long studentNum;

    @Column(nullable = false, length = 40, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 60)
    private String email;

    @Column(length = 50)
    private String phone;

    // 생성할 때 default로 0 넣어주기
    //@Column(nullable = false)
    private int state;

    // 추가
    @Enumerated(EnumType.STRING)
    private Role role;

    public void addUserAuthority() {
        this.role = Role.STUDENT;
    }

    @Column(nullable = false, length = 20)
    private String registNumber;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    // password encoding
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public static Student of(JoinDto dto) {
        return Student.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .state(dto.getState())
                .role(Role.STUDENT)
                .registNumber(dto.getRegistNumber())
                .build();
    }

}
