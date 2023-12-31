package com.spring.security.service;

import com.spring.security.domain.Student;
import com.spring.security.dto.TokenDto;
import com.spring.security.dto.request.JoinDto;
import com.spring.security.dto.request.LoginDto;
import com.spring.security.global.jwt.JwtTokenProvider;
import com.spring.security.repository.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private PasswordEncoder encoder;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */
    @Transactional
    public void join(JoinDto joinDto){
        if (studentRepository.findById(joinDto.getId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        if (studentRepository.findByRegistNumber(joinDto.getRegistNumber()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 주민등록번호입니다.");
        }


        Student student = studentRepository.save(Student.of(joinDto));
        student.encodePassword(passwordEncoder);
        student.addUserAuthority();

        System.out.println("회원가입 완료");
    }

    /**
     * 로그인(spring security + jwt)
     */
    public String login(LoginDto loginDto){

        // 가입 여부 확인
        Student student = studentRepository.findById(loginDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 id 입니다."));

        // 비밀번호 일치 확인
        checkPassword(loginDto.getPassword(), student.getPassword());

        List<String> roles = new ArrayList<>();
        roles.add(student.getRole().name());

        System.out.println("로그인 성공");
        // 생성된 토큰 반환
        return jwtTokenProvider.createToken(student.getId(), roles);


    }

    /** 비밀번호 일치 확인 **/
    public boolean checkPassword(String password, String dbPassword) {
        return encoder.matches(password, dbPassword);
    }
}
