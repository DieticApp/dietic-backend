package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeightRepo extends JpaRepository<Weight, Integer> {
    List<Weight> findWeightByPatient(Patient patient);
}
