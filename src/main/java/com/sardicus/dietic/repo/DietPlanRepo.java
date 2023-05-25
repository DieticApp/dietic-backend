package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.DietPlan;
import com.sardicus.dietic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DietPlanRepo extends JpaRepository<DietPlan, Integer> {

    List<DietPlan> findDietPlansByPatient(Patient patient);

    List<DietPlan> findDietPlansByDayAndPatient(LocalDate day , Patient patient );
    List<DietPlan> findDietPlansByPatientAndDayAndMeal(Patient patient , LocalDate day , Integer meal);


}
