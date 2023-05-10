package com.sardicus.dietic.service;

import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.dto.FoodDto;
import com.sardicus.dietic.entity.Food;

import java.util.List;

public interface FoodService {
    List<FoodDto> searchFoods(String query , int limit);
    FoodDto addFood(FoodDto foodDto);

}