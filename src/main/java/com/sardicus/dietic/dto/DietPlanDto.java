package com.sardicus.dietic.dto;

import com.sardicus.dietic.entity.DietPlanStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DietPlanDto {

    private Integer plan_id;
    private LocalDate day;
    private Integer meal;
   private Integer food_id;
   private String food_name;
   private Double carb;
   private Double protein;
   private Double fat;
   private Double energy;
    private String details;
    private DietPlanStatus eaten = DietPlanStatus.UNCHECKED;
    private Double portion;

}
