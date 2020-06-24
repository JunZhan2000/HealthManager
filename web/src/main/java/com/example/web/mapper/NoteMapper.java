package com.example.web.mapper;

import com.example.web.entity.Note;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoteMapper {

    Integer insertNote(Note note);
}
