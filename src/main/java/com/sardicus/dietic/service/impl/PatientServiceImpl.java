package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.exception.APIException;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final DietitianRepo dietitianRepo;
    private final ModelMapper mapper;

    public PatientDto savePatient(int dietitianId , PatientDto patientDto){
        Patient patient = mapToEntity(patientDto);

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", dietitianId));
        patient.setDietitian(dietitian);
        Patient newPatient =  patientRepo.save(patient);

        return mapToDTO(newPatient);
    }

    public PatientDto updatePatient(Integer dietitianId, int patientId, PatientDto employeeRequest) {

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", dietitianId));


        Patient patient = patientRepo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "id", patientId));

        if(!patient.getDietitian().getDietitian_id().equals(dietitian.getDietitian_id())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        patient.setName(patient.getName());
        patient.setSurname(patient.getSurname());

        Patient updatedEmployee = patientRepo.save(patient);
        return mapToDTO(updatedEmployee);
    }

    public void deletePatient(int dietitianId , int patientId){

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", dietitianId));


        Patient patient = patientRepo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "id", patientId));

        if(!patient.getDietitian().getDietitian_id().equals(dietitian.getDietitian_id())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        patientRepo.delete(patient);
    }
   public List<PatientDto> getPatientsByDietitianId(Integer dietitianId) {
        List<Patient> employees = patientRepo.findPatientsByDietitianId(dietitianId);

        return employees.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PatientDto getPatientById(Integer dietitianId, Integer patientId) {

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", dietitianId));


        Patient patient = patientRepo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "id", patientId));

        if(!patient.getDietitian().getDietitian_id().equals(dietitian.getDietitian_id())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        return mapToDTO(patient);
    }

    private PatientDto mapToDTO(Patient patient){
        return mapper.map(patient, PatientDto.class);
    }
    private Patient mapToEntity(PatientDto patientDto){
        return mapper.map(patientDto, Patient.class);
    }

}
