package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;



@Entity(name = "tbl_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AbstractEntity {

    @Column(name = "password")
    String password;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "email")
    String email;

    @Column(name = "otp")
    String otp;

    @Column(name = "is_active")
    Integer isActive;

    @Column(name = "picture")
    String picture;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_category", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "user_id"), // Khóa ngoại của Student
            inverseJoinColumns = @JoinColumn(name = "category_id") // Khóa ngoại của Course
    )
    private Set<CategoryEntity> categorys = new HashSet<>();
}
