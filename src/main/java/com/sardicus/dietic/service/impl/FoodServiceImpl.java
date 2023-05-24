package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.FoodDto;
import com.sardicus.dietic.entity.Food;
import com.sardicus.dietic.repo.FoodRepo;
import com.sardicus.dietic.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepo foodRepo;
    private final ModelMapper mapper;

    @Override
    public List<FoodDto> searchFoods(String query, int limit) {
        if (query.length() >= 3) {
            List<Food> foods = foodRepo.searchFoods(query);
            List<FoodDto> foodDtos = foods.stream().map(this::mapToDTO).collect(Collectors.toList());
            return foodDtos.subList(0, Math.min(limit, foodDtos.size()));
        }
        return null;
    }

    @Override
    public FoodDto addFood(FoodDto foodDto) {
        Food food = mapToEntity(foodDto);

        Food newFood =  foodRepo.save(food);

        return mapToDTO(newFood);
    }

    private FoodDto mapToDTO(Food food){
        return mapper.map(food, FoodDto.class);
    }
    private Food mapToEntity(FoodDto foodDto){
        return mapper.map(foodDto, Food.class);
    }


}