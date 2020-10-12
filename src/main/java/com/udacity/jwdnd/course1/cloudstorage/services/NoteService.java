package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userid) {
        return noteMapper.getAllNotes(userid);
    }

    public void createOrEditNote(Note note) {
        // noteid is null for new notes
        if (note.getNoteid() == null) {
            noteMapper.insert(note);
        } else {
            noteMapper.update(note);
        }
    }

    public void deleteNote(Integer noteid) {
        noteMapper.delete(noteid);
    }
}
