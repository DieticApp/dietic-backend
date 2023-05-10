package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepo extends JpaRepository<Weight, Integer> {
}
