package com.sardicus.dietic.repo;

import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Integer> {
    List<Note> findNotesByDietitianAndDate(Dietitian dietitian , LocalDate due_date);
}
