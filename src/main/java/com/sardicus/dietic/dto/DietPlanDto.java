package com.sardicus.dietic.dto;

import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Food;
import com.sardicus.dietic.entity.Patient;
import lombok.Data;

import java.util.Set;
@Data
public class DietPlanDto {

    private Integer plan_id;
    private Integer day;
    private Integer meal;
   private Integer food_id;
   private String food_name;


}
