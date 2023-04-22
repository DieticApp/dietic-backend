package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.DietPlanDto;
import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.service.DietPlanService;
import com.sardicus.dietic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dietPlans")
@RequiredArgsConstructor
public class DietPlanController {
    private final DietPlanService dietPlanService;



    @PostMapping("/{dietitianId}/{patientId}/{foodId}")
    public ResponseEntity<DietPlanDto> saveFood(@PathVariable(value = "dietitianId")int dietitianId ,
                                                @PathVariable(value = "patientId") int patientId  ,
                                                @PathVariable(value = "foodId")int foodId,
                                                    @RequestBody DietPlanDto dietPlanDto){
        return new ResponseEntity<>(dietPlanService.saveFood(dietitianId,patientId,foodId , dietPlanDto), HttpStatus.CREATED);
    }
    @GetMapping("/{dietitianId}/{patientId}")
    public List<DietPlanDto> getPlanByPatientId(@PathVariable(value = "dietitianId")int dietitianId,
                                               @PathVariable(value = "patientId") int patientId){
        return dietPlanService.getPlanByPatientId(patientId);
    }

}
