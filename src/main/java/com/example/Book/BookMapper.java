package com.example.Book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    @Autowired
    private ModelMapper modelMapper;
    public BookDTO convertToDTO(BookEntity book) {
        return modelMapper.map(book, BookDTO.class);
    }
    public BookEntity convertToBookEntity(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, BookEntity.class);
    }
}
