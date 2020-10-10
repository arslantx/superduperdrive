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

    public Note getNote(Integer noteid) {
        return noteMapper.getNote(noteid);
    }

    public List<Note> getAllNotes(Integer userid) {
        return noteMapper.getAllNotes(userid);
    }

    public void createOrEditNote(Note note) {
        if (isExistingNote(note)) {
            noteMapper.update(note);
        } else {
            noteMapper.insert(note);
        }
    }

    public void deleteNote(Integer noteid) {
        noteMapper.delete(noteid);
    }

    private boolean isExistingNote(Note note) {
        Note existingNote = noteMapper.getNote(note.getNoteid());
        return existingNote != null;
    }
}
