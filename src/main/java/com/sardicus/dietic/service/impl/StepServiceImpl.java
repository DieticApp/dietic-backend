package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.dto.StepDto;
import com.sardicus.dietic.dto.WeightDto;
import com.sardicus.dietic.entity.*;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.repo.StepsRepo;
import com.sardicus.dietic.service.StepService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {
    private final ModelMapper mapper;
    private final PatientRepo patientRepo;
    private final StepsRepo stepsRepo;
    @Override
    public List<StepDto> getSteps(Integer patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        List<Steps> steps = stepsRepo.findStepsByPatient(patient);
        return steps.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public StepDto getDailySteps(Integer patientId, LocalDate date) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        Steps steps = stepsRepo.findStepsByPatientAndDate(patient,date);
        return mapToDTO(steps);
    }

    @Override
    public StepDto saveSteps(Integer patientId, StepDto stepDto) {
        Steps steps = mapToEntity(stepDto);
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        steps.setPatient(patient);
        Steps newSteps = stepsRepo.save(steps);


        return mapToDTO(newSteps);
    }
    private StepDto mapToDTO(Steps steps){
        return mapper.map(steps, StepDto.class);
    }
    private Steps mapToEntity(StepDto stepDto){
        return mapper.map(stepDto, Steps.class);
    }

}
