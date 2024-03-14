package com.example.springbootshardingsphereexample.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Gilang Whisperer
 * Created on 05/03/2024
 */

@Getter
@Setter
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "user_test", indexes = {
        @Index(name = "id", unique = true, columnList = "id"),
        @Index(name = "name", unique = false, columnList = "name"),
})
@EntityListeners(AuditingEntityListener.class)
public class UserTest  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
