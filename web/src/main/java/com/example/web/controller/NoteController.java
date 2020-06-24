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

    @GetMapping("/test")
    public String test(){
        return "这是我给你的response body~\n这是你的authentication" +
                SecurityContextHolder.getContext().getAuthentication().toString();
    }

    @PostMapping("/POST/note")
    public Response postNote(@RequestBody() Note note){
        noteService.insertNote(note);

        return Response.success(note);
    }
}
