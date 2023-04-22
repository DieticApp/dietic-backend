package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.entity.Food;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.security.CustomUserDetailsService;
import com.sardicus.dietic.service.FoodService;
import com.sardicus.dietic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dietitians/patients")
@RequiredArgsConstructor
public class PatientController {
    private final DietitianRepo dietitianRepo;
    private final PatientService patientService;
    private final FoodService foodService;


    @GetMapping()
    public List<PatientDto> getPatientsByDietitianId(@AuthenticationPrincipal UserDetails dietitian){
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        return patientService.getPatientsByDietitianId(dietitianId);
    }
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@AuthenticationPrincipal UserDetails dietitian,
                                                     @PathVariable(value = "patientId") Integer patientId){
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        PatientDto patient = patientService.getPatientById(dietitianId, patientId);
        return new ResponseEntity<>(patient , HttpStatus.OK);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDto> updatePatient(@AuthenticationPrincipal UserDetails dietitian,
                                                    @PathVariable(value = "patientId") Integer patientId,
                                                    @RequestBody PatientDto patientDto){
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        PatientDto updatedPatient = patientService.updatePatient(dietitianId, patientId, patientDto);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<String> deletePatient(@AuthenticationPrincipal UserDetails dietitian,
                                                @PathVariable(value = "patientId") Integer patientId){
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        patientService.deletePatient(dietitianId, patientId);
        return new ResponseEntity<>("Employee deleted successfully" , HttpStatus.OK);
    }
}
