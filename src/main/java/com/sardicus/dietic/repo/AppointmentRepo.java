package com.sardicus.dietic.repo;



import com.sardicus.dietic.entity.Appointment;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {


    List<Appointment> findAppointmentsByPatient(Patient patient);
    List<Appointment> findAppointmentsByDietitian(Dietitian dietitian);
}