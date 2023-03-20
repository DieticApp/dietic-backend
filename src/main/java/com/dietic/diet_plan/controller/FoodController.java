package com.dietic.diet_plan.controller;

import com.dietic.diet_plan.entity.Food;
import com.dietic.diet_plan.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/foods")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoods(@RequestParam("query") String query){
        return ResponseEntity.ok(foodService.searchFoods(query));
    }

}
