package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.DietPlanDto;
import com.sardicus.dietic.entity.*;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.DietPlanRepo;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.FoodRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.service.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
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
    public DietPlanDto saveFood(Integer dietitianId , Integer patientId , DietPlanDto dietPlanDto) {
        DietPlan dietPlan = mapToEntity(dietPlanDto);


        Food food = foodRepo.findById(dietPlanDto.getFood_id()).orElseThrow(
                () -> new ResourceNotFoundException("Food", "id", patientId));

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id", dietitianId));
        dietPlan.setDietitian(dietitian);

        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        dietPlan.setPatient(patient);


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
    public void deletePlanByMeal(Integer patientId, LocalDate dayId, Integer mealId) {

    }


    @Override
    public void deleteFood(Integer dietitianId ,Integer patientId, Integer day, Integer meal , Integer food) {

    }

    @Override
    public DietPlanDto updateStatusOfFood(Integer planId, DietPlanDto dietPlanDto) {
        DietPlan dietPlan = dietPlanRepo.findById(planId).orElseThrow(() ->
                new ResourceNotFoundException("Plan", "id:", planId));

        dietPlan.setEaten(dietPlanDto.getEaten());


        DietPlan updatedPlan = dietPlanRepo.save(dietPlan);
        return mapToDTO(updatedPlan);
    }

    @Override
    public List<DietPlanDto> getPlanByDay(LocalDate day, Integer patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        List<DietPlan> dietPlans = dietPlanRepo.findDietPlansByDayAndPatient(day , patient);

        return dietPlans.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DietPlanDto> getByMeal(Integer patientId, LocalDate day, Integer meal) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        List<DietPlan> dietPlans = dietPlanRepo.findDietPlansByPatientAndDayAndMeal(patient , day , meal);

        return dietPlans.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private DietPlanDto mapToDTO(DietPlan dietPlan){
        DietPlanDto dietPlanDto = mapper.map(dietPlan, DietPlanDto.class);
        double portion = dietPlanDto.getPortion();


        dietPlanDto.setFood_name(foodRepo.findById(dietPlanDto.getFood_id()).get().getDescription());
        dietPlanDto.setFat(round(foodRepo.findById(dietPlanDto.getFood_id()).get().getFat()*portion));
        dietPlanDto.setCarb(round(foodRepo.findById(dietPlanDto.getFood_id()).get().getCarb()*portion));
        dietPlanDto.setProtein(round(foodRepo.findById(dietPlanDto.getFood_id()).get().getProtein()*portion));
        dietPlanDto.setEnergy(round(foodRepo.findById(dietPlanDto.getFood_id()).get().getEnergy()*portion));
        return dietPlanDto;
    }
    public static double round(double number) {
      double roundedNumber = (double) (Math.round(number*1000.0)/1000.0);
        return roundedNumber;
    }

    // convert DTO to entity
    private DietPlan mapToEntity(DietPlanDto dietPlanDto){
        DietPlan dietPlan = mapper.map(dietPlanDto, DietPlan.class);
        return dietPlan;
    }
}
