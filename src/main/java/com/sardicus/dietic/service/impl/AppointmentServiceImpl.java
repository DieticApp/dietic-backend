package com.sardicus.dietic.service.impl;


import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.entity.Appointment;
import com.sardicus.dietic.entity.AppointmentStatus;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.AppointmentRepo;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

   private final AppointmentRepo appointmentRepo;
   private final DietitianRepo dietitianRepo;
   private final PatientRepo patientRepo;


    private final ModelMapper mapper;
    @Override
    public Map<Integer, Integer> getAppointmentCountsByYear(Integer dietitianId) {
        int appointmentCount = 0;
        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id", dietitianId));
        int currentYear = Year.now().getValue();

        Map<Integer, Integer> appointmentCountsByMonth = new HashMap<>();

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(currentYear, month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();

            appointmentCount = appointmentRepo
                    .findAllByAppointmentDateBetweenAndDietitian(startDate, endDate, dietitian)
                    .size();
            appointmentCountsByMonth.put(month, appointmentCount);
        }

        return appointmentCountsByMonth;
    }



    @Override
    public AppointmentDto getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(
                () -> new ResourceNotFoundException("Appointment", "id", appointmentId));
        return mapToDTO(appointment);
    }
    @Override
    public List<AppointmentDto> getAppointmentsByDietitianId(Integer dietitianId) {
        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id", dietitianId));
        AppointmentStatus status = AppointmentStatus.BOOKED;
        List<Appointment> appointments = appointmentRepo.findAppointmentsByStatusAndDietitian(status,dietitian);

        return appointments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDietitianIdAndDate(Integer dietitianId , LocalDate date) {
        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id", dietitianId));
        AppointmentStatus status = AppointmentStatus.BOOKED;
        List<Appointment> appointments = appointmentRepo.findAppointmentsByStatusAndDietitianAndAppointmentDate(status,dietitian,date);
        return appointments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPatientId(Integer patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        AppointmentStatus status = AppointmentStatus.BOOKED;
        List<Appointment> appointments = appointmentRepo.findAppointmentsByStatusAndPatient(status,patient);

        return appointments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AppointmentDto bookAppointment(Integer dietitianId , Integer patientId  ,AppointmentDto appointmentDto) {
        Appointment appointment = mapToEntity(appointmentDto);

        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id", dietitianId));
        appointment.setDietitian(dietitian);

        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        appointment.setPatient(patient);

        Appointment newAppointment =  appointmentRepo.save(appointment);

        return mapToDTO(newAppointment);
    }

    @Override
    public AppointmentDto updateAppointment( Long appointmentId, AppointmentDto appointmentRequest) {

        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() ->
                new ResourceNotFoundException("Appointment", "id:", appointmentId));

        Patient patient = patientRepo.findById(appointmentRequest.getPatient_id()).orElseThrow(() ->
                new ResourceNotFoundException("Patient", "id:", appointmentRequest.getPatient_id()));

        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        appointment.setStatus(appointmentRequest.getStatus());
        appointment.setPatient(patient);



        Appointment updatedAppointment = appointmentRepo.save(appointment);
        return mapToDTO(updatedAppointment);

    }

    @Override
    public AppointmentDto updateStatusOfAppointment(Long appointmentId, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() ->
                new ResourceNotFoundException("Appointment", "id:", appointmentId));

        appointment.setStatus(appointmentDto.getStatus());


        Appointment updatedAppointment = appointmentRepo.save(appointment);
        return mapToDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointmentById(Long appointmentId) {
        appointmentRepo.deleteById(appointmentId);
    }
    private AppointmentDto mapToDTO(Appointment appointment){
        return mapper.map(appointment, AppointmentDto.class);
    }

    // convert DTO to entity
    private Appointment mapToEntity(AppointmentDto appointmentDto){
        return mapper.map(appointmentDto, Appointment.class);
    }

}
