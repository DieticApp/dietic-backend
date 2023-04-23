package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FoodRepo extends JpaRepository<Food, Integer> {
    @Query("SELECT p from Food p where" +
            " p.description LIKE CONCAT('%',:query, '%')")
    List<Food> searchFoods(String query);
}