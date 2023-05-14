package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DietitianRepo dietitianRepo;
    private final PatientRepo patientRepo;
    private final UserRepo userRepo;

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long appointmentId) {
        return new ResponseEntity<>(appointmentService.getAppointmentById(appointmentId) , HttpStatus.OK);
    }
    @GetMapping("/dietitian")
    List<AppointmentDto> getAppointmentsByDietitianId(@AuthenticationPrincipal UserDetails dietitian) {
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        List<AppointmentDto> appointmentDto = appointmentService.getAppointmentsByDietitianId(dietitianId);
        for (AppointmentDto element : appointmentDto) {
            element.setDietitianName(dietitianRepo.findByEmail(dietitian.getUsername()).get().getName() +" "+ dietitianRepo.findByEmail(dietitian.getUsername()).get().getSurname());
            element.setPatientName(patientRepo.findById(element.getPatient_id()).get().getName() +" "+ patientRepo.findById(element.getPatient_id()).get().getSurname());
        }
        return appointmentDto;
    }
    @PostMapping("/dietitian/byDate/{dietitianId}")
    List<AppointmentDto> getAppointmentsByDietitianIdAndDate(@PathVariable Integer dietitianId ,@RequestBody AppointmentDto appointmentDto ) {
        LocalDate date = appointmentDto.getAppointmentDate();
        List<AppointmentDto> appointmentDtoResponse = appointmentService.getAppointmentsByDietitianIdAndDate(dietitianId,date);
        for (AppointmentDto element : appointmentDtoResponse) {
            element.setPicture(userRepo.findByEmail(patientRepo.findById(element.getPatient_id()).get().getEmail()).get().getPicture());
        }
        return appointmentDtoResponse;
    }
    @GetMapping("/patient/{patientId}")
    List<AppointmentDto> getAppointmentsByPatientId(@PathVariable Integer patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }


    @PostMapping("/book/{dietitianId}/{patientId}")
    public ResponseEntity<AppointmentDto> bookAppointment(@PathVariable Integer dietitianId , @PathVariable Integer patientId , @RequestBody AppointmentDto appointment) {
        return new ResponseEntity<>(appointmentService.bookAppointment(dietitianId , patientId ,appointment), HttpStatus.CREATED);
    }


    @PutMapping(path = "/update/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentDto appointment) {
        AppointmentDto updatedAppointment = appointmentService.updateAppointment(appointmentId, appointment);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }


    @PatchMapping(path = "/updateStatus/{appointmentId}")
    public ResponseEntity<AppointmentDto> updateStatusOfAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentDto appointment) {
        AppointmentDto updatedAppointment = appointmentService.updateStatusOfAppointment(appointmentId, appointment);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    /** DELETE request to delete specific appointments **/
    @DeleteMapping(path = "/delete/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteAppointmentById(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
    }



}
