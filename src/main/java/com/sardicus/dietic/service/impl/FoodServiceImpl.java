package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.entity.Food;
import com.sardicus.dietic.repo.FoodRepo;
import com.sardicus.dietic.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepo foodRepo;
    @Override
    public List<Food> searchFoods(String query) {
        if (query.length() >= 3){
            List<Food> foods = foodRepo.searchFoods(query);
            return foods;
        }
        return null;
    }
}