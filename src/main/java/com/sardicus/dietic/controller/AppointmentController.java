package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long appointmentId) {
        return new ResponseEntity<>(appointmentService.getAppointmentById(appointmentId) , HttpStatus.OK);
    }
    @GetMapping("/dietitian/{dietitianId}")
    List<AppointmentDto> getAppointmentsByDietitianId(@PathVariable Integer dietitianId) {
        return appointmentService.getAppointmentsByDietitianId(dietitianId);
    }
    @GetMapping("/dietitian/byDate/{dietitianId}")
    List<AppointmentDto> getAppointmentsByDietitianIdAndDate(@PathVariable Integer dietitianId ,@RequestBody AppointmentDto appointmentDto ) {
        LocalDate date = appointmentDto.getAppointmentDate();
        return appointmentService.getAppointmentsByDietitianIdAndDate(dietitianId , date);
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
