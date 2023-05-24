package com.sardicus.dietic.service;

import com.sardicus.dietic.dto.FoodDto;

import java.util.List;

public interface FoodService {
    List<FoodDto> searchFoods(String query , int limit);
    FoodDto addFood(FoodDto foodDto);

}