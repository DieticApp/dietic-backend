package com.sardicus.dietic.service;

import com.sardicus.dietic.dto.AppointmentDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AppointmentService {
    AppointmentDto getAppointmentById(Long appointmentId);
   Map<Integer, Integer> getAppointmentCountsByYear(Integer dietitianId);

    List<AppointmentDto> getAppointmentsByDietitianId(Integer dietitianId);
    List<AppointmentDto> getAppointmentsByDietitianIdAndDate(Integer dietitianId , LocalDate date);

    List<AppointmentDto> getAppointmentsByPatientId(Integer dietitianId);

    AppointmentDto bookAppointment(Integer dietitianId , Integer patientId  ,AppointmentDto appointment);

    AppointmentDto updateAppointment(Long appointmentId , AppointmentDto appointment);

    AppointmentDto updateStatusOfAppointment(Long appointmentId, AppointmentDto appointment);

    void deleteAppointmentById(Long appointmentId);

}