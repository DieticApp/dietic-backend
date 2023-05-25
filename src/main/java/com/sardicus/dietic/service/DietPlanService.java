package com.sardicus.dietic.service;

import com.sardicus.dietic.dto.DietPlanDto;

import java.time.LocalDate;
import java.util.List;

public interface DietPlanService {
    DietPlanDto saveFood(Integer dietitianId , Integer patientId, DietPlanDto dietPlan);
    void deleteFood(Integer planId);
    DietPlanDto updateStatusOfFood(Integer planId, DietPlanDto dietPlanDto);
    void deletePlanByPatientId(LocalDate day, Integer patientId);
    List<DietPlanDto> getPlanByPatientId(Integer patientId);

    List<DietPlanDto> getPlanByDay(LocalDate day , Integer patientId);
    List<DietPlanDto> getByMeal(Integer patientId , LocalDate day , Integer meal);

}
