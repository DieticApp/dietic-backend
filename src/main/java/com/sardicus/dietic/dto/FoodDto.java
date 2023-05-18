package com.sardicus.dietic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {
    private Integer food_id;
    private String description;
    private Double protein;
    private Double fat;
    private Double carbohydrate;
    private Integer energy;


}
