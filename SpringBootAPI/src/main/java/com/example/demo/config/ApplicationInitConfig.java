package com.example.demo.config;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {


    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);

    List<CategoryEntity> categories = List.of(
            new CategoryEntity("Trái cây", "Các loại trái cây tươi ngon", "https://soyte.hatinh.gov.vn/upload/1000030/20171026/7f916ded027eef14543550b385a4306cqua-cam-1.jpg"),
            new CategoryEntity("Đồ uống", "Các loại nước ép và đồ uống dinh dưỡng", "https://www.thuongdo.com/sites/default/files/u165605/do-uong-trung-quoc-3.jpg"),
            new CategoryEntity("Đồ ăn nhanh", "Thức ăn nhanh tiện lợi", "https://www.cet.edu.vn/wp-content/uploads/2019/04/fastfood-la-gi.jpg")
    );

    @Bean
    ApplicationRunner applicationRunner (CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categories.stream().forEach(
                        category -> {
                            categoryRepository.save(category);
                        }
                );
            }
        };
    }
}