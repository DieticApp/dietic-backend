package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.entity.Steps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StepsRepo extends JpaRepository<Steps, Integer> {
    Steps findStepsByPatientAndDate(Patient patient , LocalDate date);
    List<Steps> findStepsByPatient(Patient patient);
}
