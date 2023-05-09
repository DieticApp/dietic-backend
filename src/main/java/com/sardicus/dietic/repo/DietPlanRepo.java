package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.DietPlan;
import com.sardicus.dietic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietPlanRepo extends JpaRepository<DietPlan, Integer> {
    @Query( "SELECT e FROM Patient p join p.dietPlans e WHERE p.patient_id = :id")
    List<DietPlan> findDietPlansByPatientId(@Param("id") Integer id);

    List<DietPlan> findDietPlansByDayAndPatient(LocalDate day , Patient patient );
    List<DietPlan> findDietPlansByPatientAndDayAndMeal(Patient patient , LocalDate day , Integer meal);

}
