package com.sardicus.dietic.service;

import com.sardicus.dietic.dto.NoteDto;
import com.sardicus.dietic.dto.StepDto;

import java.time.LocalDate;
import java.util.List;

public interface NoteService {
    List<NoteDto> getDailyNotes(Integer dietitianId, LocalDate date);
    NoteDto saveNote(Integer dietitianId  , NoteDto noteDto);
    NoteDto updateNote(Integer noteId ,  NoteDto noteDto);
    void updateNoteStatus(Integer noteId);
    void deleteNote(Integer noteId);
    List<NoteDto> getUpcomingNotes(Integer dietitianId);
}
