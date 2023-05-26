package com.sardicus.dietic.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.dto.StepDto;
import com.sardicus.dietic.dto.WeightDto;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.service.FoodService;
import com.sardicus.dietic.service.PatientService;
import com.sardicus.dietic.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dietitians/patients")
@RequiredArgsConstructor
public class PatientController {
    private final DietitianRepo dietitianRepo;
    private final PatientRepo patientRepo;
    private final PatientService patientService;
    private final UserRepo userRepo;
    private final StepService stepService;

    @PostMapping("/{patientId}/saveSteps")
    ResponseEntity<StepDto> saveStepNumber(@PathVariable Integer patientId , @RequestBody StepDto stepDto ) {
        return new ResponseEntity<>( stepService.saveSteps(patientId,stepDto),HttpStatus.CREATED);
    }
    @PostMapping("/{patientId}/getDailySteps")
    ResponseEntity<StepDto> getDailySteps(@PathVariable Integer patientId , @RequestBody StepDto stepDto ) {
       LocalDate localDate = stepDto.getDate();
        return new ResponseEntity<>( stepService.getDailySteps(patientId,localDate),HttpStatus.OK);
    }
    @PostMapping("/{patientId}/getAllSteps")
    ResponseEntity<List<StepDto>> getAllSteps(@PathVariable Integer patientId ) {
        return new ResponseEntity<>( stepService.getSteps(patientId),HttpStatus.OK);
    }

    @GetMapping()
    public List<PatientDto> getPatientsByDietitianId(@AuthenticationPrincipal UserDetails dietitian){
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        List<PatientDto> patientDto = patientService.getPatientsByDietitianId(dietitianId);
        for (PatientDto element : patientDto) {
            element.setPicture(userRepo.findByEmail(element.getEmail()).get().getPicture());
        }
        return patientDto;
    }
    @GetMapping("/details")
    public ResponseEntity<PatientDto> getPatientDetails(@AuthenticationPrincipal UserDetails patient){
        Integer patientId = patientRepo.findByEmail(patient.getUsername()).get().getPatient_id();
        return new ResponseEntity<>(patientService.getPatientById(patientId) , HttpStatus.OK);
    }
    @GetMapping("/{patientId}/details")
    public ResponseEntity<PatientDto> getPatientDetailsById( @PathVariable(value = "patientId") Integer patientId){
        return new ResponseEntity<>(patientService.getPatientById(patientId) , HttpStatus.OK);
    }
    @GetMapping("/progress")
    public ResponseEntity<List<WeightDto>> getWeightProgress(@AuthenticationPrincipal UserDetails patient){
        Integer patientId = patientRepo.findByEmail(patient.getUsername()).get().getPatient_id();
        return new ResponseEntity<>(patientService.getWeightProgress(patientId) , HttpStatus.OK);
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
                                                @PathVariable(value = "patientId") Integer patientId) throws FirebaseAuthException {
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        patientService.deletePatient(dietitianId, patientId);
        return new ResponseEntity<>("Patient deleted successfully" , HttpStatus.OK);
    }
}
