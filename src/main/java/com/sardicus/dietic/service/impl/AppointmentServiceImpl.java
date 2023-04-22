package com.sardicus.dietic.service.impl;


import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.entity.Appointment;
import com.sardicus.dietic.entity.DietPlan;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.exception.APIException;
import com.sardicus.dietic.exception.ResourceNotFoundException;


import com.sardicus.dietic.repo.AppointmentRepo;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

   private final AppointmentRepo appointmentRepo;
   private final DietitianRepo dietitianRepo;
   private final PatientRepo patientRepo;


    private final ModelMapper mapper;


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
        List<Appointment> appointments = appointmentRepo.findAppointmentsByDietitian(dietitian);

        return appointments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPatientId(Integer patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "id", patientId));
        List<Appointment> appointments = appointmentRepo.findAppointmentsByPatient(patient);

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



        appointment.setAppointmentDate(appointment.getAppointmentDate());
        appointment.setAppointmentTime(appointment.getAppointmentTime());
        appointment.setStatus(appointment.getStatus());
        appointment.setPatient(appointment.getPatient());



        Appointment updatedAppointment = appointmentRepo.save(appointment);
        return mapToDTO(updatedAppointment);

    }

    @Override
    public AppointmentDto updateStatusOfAppointment(Long appointmentId, AppointmentDto appointment) {
        Optional<Appointment> appointmentList = appointmentRepo.findById(appointmentId);

        if(appointmentList.isPresent()){
            if(appointment.getStatus() != null){
                appointmentList.get().setStatus(appointment.getStatus());
            }
            Appointment updatedAppointment = appointmentRepo.save(appointmentList.get());
            return mapToDTO(updatedAppointment);
        }
        return null;
    }

    @Override
    public void deleteAppointmentById(Long appointmentId) {
        appointmentRepo.deleteById(appointmentId);
    }
    private AppointmentDto mapToDTO(Appointment appointment){
        AppointmentDto appointmentDto = mapper.map(appointment, AppointmentDto.class);
        return appointmentDto;
    }

    // convert DTO to entity
    private Appointment mapToEntity(AppointmentDto appointmentDto){
        Appointment appointment = mapper.map(appointmentDto, Appointment.class);
        return appointment;
    }

}
