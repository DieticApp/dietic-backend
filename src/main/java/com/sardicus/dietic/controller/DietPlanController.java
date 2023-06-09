package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.DietPlanDto;
import com.sardicus.dietic.service.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dietPlans")
@RequiredArgsConstructor
public class DietPlanController {
    private final DietPlanService dietPlanService;



    @PostMapping("/{dietitianId}/{patientId}")
    public ResponseEntity<DietPlanDto> saveFood(@PathVariable(value = "dietitianId")int dietitianId ,
                                                @PathVariable(value = "patientId") int patientId  ,
                                                    @RequestBody DietPlanDto dietPlanDto){
        return new ResponseEntity<>(dietPlanService.saveFood(dietitianId,patientId , dietPlanDto), HttpStatus.CREATED);
    }
    @GetMapping("/{dietitianId}/{patientId}")
    public List<DietPlanDto> getPlanByPatientId(@PathVariable(value = "dietitianId")int dietitianId,
                                               @PathVariable(value = "patientId") int patientId){
        return dietPlanService.getPlanByPatientId(patientId);
    }
    @PostMapping("patient/{patientId}")
    public List<DietPlanDto> getPlanByDay(@PathVariable(value = "patientId")int patientId,@RequestBody DietPlanDto dietPlanDto){
        LocalDate day = dietPlanDto.getDay();
        return dietPlanService.getPlanByDay(day , patientId);
    }
    @PostMapping("patient/{patientId}/meal/{meal}")
    public List<DietPlanDto> getPlansByDayAndMeal(@PathVariable(value = "patientId")int patientId,
                                                  @RequestBody DietPlanDto dietPlanDto,
                                                 @PathVariable(value = "meal") int meal){
        LocalDate day = dietPlanDto.getDay();
        return dietPlanService.getByMeal(patientId,day,meal);
    }
    @PatchMapping(path = "/updateEaten/{planId}")
    public ResponseEntity<DietPlanDto> updateStatusOfDietPlan(@PathVariable Integer planId, @RequestBody DietPlanDto dietPlan) {
        DietPlanDto updatedDietPlan = dietPlanService.updateStatusOfFood(planId, dietPlan);
        return new ResponseEntity<>(updatedDietPlan, HttpStatus.OK);
    }
    @PostMapping("/deleteDay/{patientId}")
    public ResponseEntity<String> deletePlanByDay(@PathVariable(value = "patientId") Integer patientId ,@RequestBody DietPlanDto dietPlanDto){
        dietPlanService.deletePlanByPatientId( dietPlanDto.getDay() , patientId);
        return new ResponseEntity<>("Diet plan deleted successfully" , HttpStatus.OK);
    }
    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<String> deleteFood(@PathVariable(value = "planId") Integer planId){
        dietPlanService.deleteFood(planId);
        return new ResponseEntity<>("Food deleted successfully" , HttpStatus.OK);
    }
}
