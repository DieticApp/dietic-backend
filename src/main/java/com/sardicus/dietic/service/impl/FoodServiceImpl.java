package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.FoodDto;
import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.entity.Food;
import com.sardicus.dietic.entity.Patient;
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
    public List<FoodDto> searchFoods(String query) {
        if (query.length() >= 3){
            List<Food> foods = foodRepo.searchFoods(query);
            return foods.stream().map(this::mapToDTO).collect(Collectors.toList());
        }
        return null;
    }
    private FoodDto mapToDTO(Food food){
        return mapper.map(food, FoodDto.class);
    }

}