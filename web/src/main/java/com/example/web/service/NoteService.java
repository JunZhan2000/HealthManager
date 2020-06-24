package com.example.web.service;

import com.example.web.entity.Note;
import com.example.web.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;

    public Note insertNote(Note note){
        noteMapper.insertNote(note);

        return note;
    }
}
