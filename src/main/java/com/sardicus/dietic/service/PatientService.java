package com.sardicus.dietic.service;



import com.sardicus.dietic.dto.PatientDto;

import java.util.List;

public interface PatientService {
     PatientDto savePatient(int id , PatientDto patient);
     void deletePatient(int dietitianId , int patientId);
     List<PatientDto> getPatientsByDietitianId(Integer dietitianId);
     PatientDto getPatientById(Integer dietitianId , Integer patientId);
     PatientDto updatePatient(Integer dietitianId, int patientId, PatientDto employeeRequest);
}
