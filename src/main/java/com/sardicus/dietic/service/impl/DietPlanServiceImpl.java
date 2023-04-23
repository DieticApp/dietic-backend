package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.DietPlanDto;
import com.sardicus.dietic.entity.DietPlan;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Food;
import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.DietPlanRepo;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.FoodRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.service.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietPlanServiceImpl implements DietPlanService {
    private final DietPlanRepo dietPlanRepo;
    private final DietitianRepo dietitianRepo;
    private final PatientRepo patientRepo;
    private final FoodRepo foodRepo;

    private final ModelMapper mapper;
    @Override
    public DietPlanDto saveFood(Integer dietitianId , Integer patientId  , Integer foodId , DietPlanDto dietPlanDto) {
            DietPlan dietPlan = mapToEntity(dietPlanDto);

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id", dietitianId));
        dietPlan.setDietitian(dietitian);

        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        dietPlan.setPatient(patient);

        Food food = foodRepo.findById(foodId).orElseThrow(
                () -> new ResourceNotFoundException("Food", "id", patientId));
        dietPlan.setFood(food);


        DietPlan newDietPlan =  dietPlanRepo.save(dietPlan);

        return mapToDTO(newDietPlan);
    }

    @Override
    public void deletePlanByPatientId(Integer dietitianId , Integer patientId) {

    }

    @Override
    public List<DietPlanDto> getPlanByPatientId(Integer patientId) {
        List<DietPlan> dietPlans = dietPlanRepo.findDietPlansByPatientId(patientId);
        return dietPlans.stream().map(this::mapToDTO).collect(Collectors.toList());

    }

    @Override
    public void deletePlanByMeal(Integer patientId, Integer dayId, Integer mealId) {

    }


    @Override
    public void deleteFood(Integer dietitianId ,Integer patientId, Integer day, Integer meal , Integer food) {

    }

    @Override
    public List<DietPlanDto> getPlanByDay(Integer day, Integer patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        List<DietPlan> dietPlans = dietPlanRepo.findDietPlansByDayAndPatient(day , patient);

        return dietPlans.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DietPlanDto getByMeal(Integer patientId, Integer day, Integer meal) {
        return null;
    }
    private DietPlanDto mapToDTO(DietPlan dietPlan){
        DietPlanDto dietPlanDto = mapper.map(dietPlan, DietPlanDto.class);
        dietPlanDto.setFood_name(foodRepo.findById(dietPlanDto.getFood_id()).get().getDescription());
        dietPlanDto.setFat(foodRepo.findById(dietPlanDto.getFood_id()).get().getFat());
        dietPlanDto.setCarb(foodRepo.findById(dietPlanDto.getFood_id()).get().getCarb());
        dietPlanDto.setProtein(foodRepo.findById(dietPlanDto.getFood_id()).get().getProtein());
        dietPlanDto.setEnergy(foodRepo.findById(dietPlanDto.getFood_id()).get().getEnergy());
        return dietPlanDto;
    }

    // convert DTO to entity
    private DietPlan mapToEntity(DietPlanDto dietPlanDto){
        DietPlan dietPlan = mapper.map(dietPlanDto, DietPlan.class);
        return dietPlan;
    }
}
