package com.example.web.controller;

import com.example.web.entity.Note;
import com.example.web.service.NoteService;
import com.example.web.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;


    @PostMapping("/POST/note")
    public Response postNote(@RequestBody() Note note){

        Integer uid = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        note.setId(uid);

        noteService.insertNote(note);

        return Response.success(note);
    }
}
