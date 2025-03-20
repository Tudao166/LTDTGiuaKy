package com.example.demo.repository;

import com.example.demo.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    @Query("SELECT c FROM tbl_category c JOIN c.users u WHERE u.email = :username")
    List<CategoryEntity> findCategoriesByUsername(@Param("username") String username);
}
