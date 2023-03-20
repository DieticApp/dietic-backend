package com.dietic.diet_plan.repository;

import com.dietic.diet_plan.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Query("SELECT p from Food p where" +
            " p.description LIKE CONCAT('%',:query, '%')")
    List<Food> searchFoods(String query);
}
