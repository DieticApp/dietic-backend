package com.sardicus.dietic.service;

import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.dto.StepDto;
import com.sardicus.dietic.dto.WeightDto;

import java.time.LocalDate;
import java.util.List;

public interface StepService {
    List<StepDto> getSteps(Integer patientId);
    StepDto getDailySteps(Integer patientId, LocalDate date);
    StepDto saveSteps(Integer patientId  , StepDto stepDto);

}
