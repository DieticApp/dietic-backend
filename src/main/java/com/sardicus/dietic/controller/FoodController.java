package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.FoodDto;
import com.sardicus.dietic.entity.Food;
import com.sardicus.dietic.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<FoodDto>> searchFoods(@RequestParam("query") String query){
        return ResponseEntity.ok(foodService.searchFoods(query,15));
    }
    @PostMapping("/add")
    public ResponseEntity<FoodDto> addFood(@RequestBody FoodDto foodDto){
        return ResponseEntity.ok(foodService.addFood(foodDto));
    }
}
