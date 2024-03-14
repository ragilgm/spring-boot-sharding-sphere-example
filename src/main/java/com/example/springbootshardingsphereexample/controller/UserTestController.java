package com.example.springbootshardingsphereexample.controller;

import com.example.springbootshardingsphereexample.entity.UserTest;
import com.example.springbootshardingsphereexample.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Gilang Whisperer
 * Created on 05/03/2024
 */

@RestController
@RequiredArgsConstructor
public class UserTestController {

    private final UserTestRepository userTestRepository;


    @GetMapping("/test")
    private void testController(){
//        UserTest userTest = new UserTest();
//        userTest.setName("test");
//
//        userTestRepository.saveAndFlush(userTest);

      List<UserTest> use =   userTestRepository.findByCreatedAtGreaterThanAndCreatedAtLessThan(LocalDateTime.now().minusMonths(3),LocalDateTime.now());
        System.out.println(use);


    }

}
