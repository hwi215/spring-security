package com.spring.security.repository;

import com.spring.security.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    Optional<Student> findById(String id);

    Optional<Student> findByRegistNumber(String registNumber);

}
