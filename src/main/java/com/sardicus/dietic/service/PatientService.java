package com.sardicus.dietic.service;



import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.dto.WeightDto;

import java.util.List;

public interface PatientService {

     void deletePatient(int dietitianId , int patientId);
     List<PatientDto> getPatientsByDietitianId(Integer dietitianId);
     PatientDto getPatientById(Integer patientId);
     PatientDto updatePatient(Integer dietitianId, int patientId, PatientDto employeeRequest);
     List<WeightDto> getWeightProgress(Integer patientId);
}
