package com.sardicus.dietic.dto;

import com.sardicus.dietic.entity.DietPlanStatus;
import lombok.Data;
@Data
public class DietPlanDto {

    private Integer plan_id;
    private Integer day;
    private Integer meal;
   private Integer food_id;
   private String food_name;
   private Double carb;
   private Double protein;
   private Double fat;
   private Integer energy;
    private String details;
    private DietPlanStatus eaten = DietPlanStatus.UNCHECKED;

}
