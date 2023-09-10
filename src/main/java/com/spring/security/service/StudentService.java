package com.spring.security.service;

import com.spring.security.domain.Student;
import com.spring.security.dto.request.JoinDto;
import com.spring.security.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
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


        Student student = studentRepository.save(joinDto.of());
        student.encodePassword(passwordEncoder);
        student.addUserAuthority();

        System.out.println("회원가입 완료");
    }
}
