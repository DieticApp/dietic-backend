package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DietitianRepo extends JpaRepository<Dietitian, Integer> {
    Optional<Dietitian> findByEmail(String email);
}
