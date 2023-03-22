package com.dietic.diet_plan.service;

import com.dietic.diet_plan.entity.Food;
import com.dietic.diet_plan.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    @Override
    public List<Food> searchFoods(String query) {
        if (query.length() >= 3){
        List<Food> foods = foodRepository.searchFoods(query);
            return foods;
        }
        return null;
    }
}
