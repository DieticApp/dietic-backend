package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.NoteDto;
import com.sardicus.dietic.dto.PatientDto;
import com.sardicus.dietic.dto.WeightDto;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Note;
import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.entity.Weight;
import com.sardicus.dietic.exception.APIException;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.NoteRepo;
import com.sardicus.dietic.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final DietitianRepo dietitianRepo;
    private final NoteRepo noteRepo;
    private final ModelMapper mapper;
    @Override
    public List<NoteDto> getDailyNotes(Integer dietitianId, LocalDate date) {
        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id:", dietitianId));

        List<Note> notes= noteRepo.findNotesByDietitianAndDate(dietitian,date);

        return notes.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public NoteDto saveNote(Integer dietitianId, NoteDto noteDto) {
        Note note = mapToEntity(noteDto);
        Dietitian dietitian = dietitianRepo.findById(dietitianId).orElseThrow(
                () -> new ResourceNotFoundException("Dietitian", "id:", dietitianId));
        note.setDietitian(dietitian);
        Note newNote = noteRepo.save(note);
        return mapToDTO(newNote);
    }

    @Override
    public NoteDto updateNote(Integer noteId , NoteDto noteDto) {


        Note note = noteRepo.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id:", noteId));



     note.setNote(noteDto.getNote());
     note.setDone(noteDto.getDone());
     note.setDate(noteDto.getDate());


        Note updatedNote = noteRepo.save(note);

        return mapToDTO(updatedNote);
    }

    @Override
    public void updateNoteStatus(Integer noteId) {
        Note note = noteRepo.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id:", noteId));
        note.setDone(!note.getDone());
        noteRepo.save(note);
    }

    @Override
    public void deleteNote(Integer noteId) {
        Note note = noteRepo.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id:", noteId));
        noteRepo.delete(note);
    }
    private NoteDto mapToDTO(Note note){
        return mapper.map(note, NoteDto.class);
    }
    private Note mapToEntity(NoteDto noteDto){
        return mapper.map(noteDto, Note.class);
    }

}
