package com.sardicus.dietic.service;

import com.sardicus.dietic.entity.Food;

import java.util.List;

public interface FoodService {
    List<Food> searchFoods(String query);

}