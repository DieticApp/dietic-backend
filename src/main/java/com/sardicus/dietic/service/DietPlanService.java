package com.sardicus.dietic.service;

import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.dto.DietPlanDto;

import java.time.LocalDate;
import java.util.List;

public interface DietPlanService {
    DietPlanDto saveFood(Integer dietitianId , Integer patientId, DietPlanDto dietPlan);
    void deleteFood(Integer dietitianId ,Integer patientId , Integer day , Integer meal , Integer foodId );
    DietPlanDto updateStatusOfFood(Integer planId, DietPlanDto dietPlanDto);
    void deletePlanByPatientId(Integer dietitianId , Integer patientId);
    List<DietPlanDto> getPlanByPatientId(Integer patientId);
    void deletePlanByMeal(Integer patientId , LocalDate dayId , Integer mealId);

    List<DietPlanDto> getPlanByDay(LocalDate day , Integer patientId);
    List<DietPlanDto> getByMeal(Integer patientId , LocalDate day , Integer meal);

}
