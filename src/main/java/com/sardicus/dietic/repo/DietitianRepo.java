package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietitianRepo extends JpaRepository<Dietitian, Integer> {
}
