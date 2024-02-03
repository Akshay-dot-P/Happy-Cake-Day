package com.akshay.HappyCakeDay.repository;

import com.akshay.HappyCakeDay.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
