package com.sardicus.dietic.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.dto.WeightDto;
import com.sardicus.dietic.entity.*;
import com.sardicus.dietic.exception.APIException;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.repo.WeightRepo;
import com.sardicus.dietic.service.MessageService;
import com.sardicus.dietic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final DietitianRepo dietitianRepo;
    private final ModelMapper mapper;
    private final WeightRepo weightRepo;
    private final UserRepo userRepo;
    private final MessageService messageService;



    public PatientDto updatePatient(Integer dietitianId, int patientId, PatientDto patientRequest) {

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id:", dietitianId));

        Patient patient = patientRepo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Patient", "id:", patientId));

        if(!patient.getDietitian().getDietitian_id().equals(dietitian.getDietitian_id())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Not working together");
        }

        patient.setName(patientRequest.getName());
        patient.setSurname(patientRequest.getSurname());
        patient.setAge(patientRequest.getAge());
        patient.setHeight(patientRequest.getHeight());
        patient.setWeight(patientRequest.getWeight());
        patient.setBodyFat(patientRequest.getBodyFat());
        patient.setAbout(patientRequest.getAbout());


        Patient updatedPatient = patientRepo.save(patient);
        if (patientRequest.getWeight().equals(patient.getWeight())){
            Weight weightData = new Weight();
            weightData.setWeight(patient.getWeight());
            weightData.setDate(LocalDate.now());
            weightData.setPatient(patient);
            weightRepo.save(weightData);
        }
        return mapToDTO(updatedPatient);
    }

    @Override
    public List<WeightDto> getWeightProgress(Integer patientId) {

        Patient patient = patientRepo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Patient", "id", patientId));
        List<Weight> weightList = weightRepo.findWeightByPatient(patient);

        return weightList.stream().map(this::mapWeightToDTO).collect(Collectors.toList());
    }

    public void deletePatient(int dietitianId , int patientId) throws FirebaseAuthException {

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id", dietitianId));


        Patient patient = patientRepo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Patient", "id", patientId));



        if(!patient.getDietitian().getDietitian_id().equals(dietitian.getDietitian_id())){
                throw new APIException(HttpStatus.BAD_REQUEST, "Dietitian does not work with this patient");
        }
        User user = userRepo.findByEmail(patient.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", patientId));
        PatientDto patientDto = mapToDTO(patient);


        userRepo.deleteById(user.getId());
        patientRepo.deleteById(patient.getPatient_id());
        messageService.deletePatient(patientDto);

    }
   public List<PatientDto> getPatientsByDietitianId(Integer dietitianId) {
        List<Patient> patients = patientRepo.findPatientsByDietitianId(dietitianId);

        return patients.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PatientDto getPatientById(Integer patientId) {

        Patient patient = patientRepo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Patient", "id", patientId));


        return mapToDTO(patient);
    }

    private PatientDto mapToDTO(Patient patient){
        return mapper.map(patient, PatientDto.class);
    }
    private WeightDto mapWeightToDTO(Weight weight){return mapper.map(weight, WeightDto.class);}
    private Patient mapToEntity(PatientDto patientDto){
        return mapper.map(patientDto, Patient.class);
    }

}
