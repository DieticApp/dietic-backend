package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.AppointmentDto;
import com.sardicus.dietic.dto.NoteDto;
import com.sardicus.dietic.dto.StepDto;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final DietitianRepo dietitianRepo;

    @PostMapping("/saveNote")
    ResponseEntity<NoteDto> saveNote(@AuthenticationPrincipal UserDetails dietitian , @RequestBody NoteDto noteDto ) {
      Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        return new ResponseEntity<>( noteService.saveNote(dietitianId,noteDto), HttpStatus.CREATED);
    }
    @PostMapping("/getDailyNotes")
    ResponseEntity<List<NoteDto>> getDailyNotes(@AuthenticationPrincipal UserDetails dietitian , @RequestBody NoteDto noteDto ) {
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        LocalDate localDate = noteDto.getDate();
        return new ResponseEntity<>( noteService.getDailyNotes(dietitianId,localDate),HttpStatus.OK);
    }
    @GetMapping("/getUpcomingNotes")
    ResponseEntity<List<NoteDto>> getUpcomingNotes(@AuthenticationPrincipal UserDetails dietitian ) {
        Integer dietitianId = dietitianRepo.findByEmail(dietitian.getUsername()).get().getDietitian_id();
        return new ResponseEntity<>( noteService.getUpcomingNotes(dietitianId),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteNoteById(@PathVariable Integer noteId) {
        noteService.deleteNote(noteId);
    }
    @PatchMapping("/updateStatus/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    void updateNoteStatus(@PathVariable Integer noteId) {
        noteService.updateNoteStatus(noteId);
    }
    @PutMapping(path = "/update/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NoteDto> updateNote(@PathVariable Integer noteId, @RequestBody NoteDto noteDto) {
        NoteDto updatedNote = noteService.updateNote(noteId, noteDto);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }
}
