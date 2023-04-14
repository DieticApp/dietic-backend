package com.sardicus.dietic.repo;


import com.sardicus.dietic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepo extends JpaRepository<Patient , Integer> {

    @Query( "SELECT e FROM Dietitian d join d.patients e WHERE d.id = :id")
    List<Patient> findPatientsByDietitianId(@Param("id") Integer id);

}
