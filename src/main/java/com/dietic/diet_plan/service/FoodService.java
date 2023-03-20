package com.dietic.diet_plan.service;

import com.dietic.diet_plan.entity.Food;

import java.util.List;

public interface FoodService {
    List<Food> searchFoods(String query);

}
