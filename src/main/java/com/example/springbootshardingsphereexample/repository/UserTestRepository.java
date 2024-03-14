package com.example.springbootshardingsphereexample.repository;

import com.example.springbootshardingsphereexample.entity.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 * @Author: Gilang Whisperer
 * Created on 05/03/2024
 */

@Repository
public interface UserTestRepository extends JpaRepository<UserTest,Long> {

    List<UserTest> findByCreatedAtGreaterThanAndCreatedAtLessThan(LocalDateTime localDateTime,LocalDateTime lessTime);

}
