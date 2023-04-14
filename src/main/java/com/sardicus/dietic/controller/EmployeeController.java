package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {
    private final PatientService patientService;


    @PostMapping("/dietitians/{dietitianId}/patients")
    public ResponseEntity<PatientDto> saveEmployee(@PathVariable(value = "dietitianId") int dietitianId,
                                                   @RequestBody PatientDto patientDto){
        return new ResponseEntity<>(patientService.savePatient(dietitianId, patientDto), HttpStatus.CREATED);
    }

    @GetMapping("/dietitians/{dietitianId}/patients")
    public List<PatientDto> getEmployeesByCompanyId(@PathVariable(value = "dietitianId") int dietitianId){
        return patientService.getPatientsByDietitianId(dietitianId);
    }
    @GetMapping("/dietitians/{dietitianId}/patients/{patientId}")
    public ResponseEntity<PatientDto> getEmployeeById(@PathVariable(value = "dietitianId") Integer dietitianId,
                                                     @PathVariable(value = "patientId") Integer patientId){
        PatientDto employeeDto = patientService.getPatientById(dietitianId, patientId);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PutMapping("/dietitians/{dietitianId}/patients/{patientId}")
    public ResponseEntity<PatientDto> updateEmployee(@PathVariable(value = "dietitianId") Integer dietitianId,
                                                    @PathVariable(value = "patientId") Integer patientId,
                                                    @RequestBody PatientDto employeeDto){
        PatientDto updatedPatient = patientService.updatePatient(dietitianId, patientId, employeeDto);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @DeleteMapping("/dietitians/{dietitianId}/patients/{patientId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "dietitianId") Integer dietitianId,
                                                @PathVariable(value = "patientId") Integer patientId){
        patientService.deletePatient(dietitianId, patientId);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }
}
