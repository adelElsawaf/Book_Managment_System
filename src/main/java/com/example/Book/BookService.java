package com.example.Book;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {


    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDTO createBook(BookDTO toBeSavedBookDTO) {
       BookEntity toBeSavedBook = bookMapper.convertToBookEntity(toBeSavedBookDTO);
       toBeSavedBook = bookRepository.save(toBeSavedBook);
        return bookMapper.convertToDTO(toBeSavedBook);
    }

    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<BookEntity> bookPage = bookRepository.findAll(pageable);
        return bookPage.map(bookMapper::convertToDTO);
    }
    public BookDTO getBookById(Long id) {
        BookEntity book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No book found with ID " + id));
        return bookMapper.convertToDTO(book);
    }


    public BookDTO updateBook(Long id, BookDTO bookWithUpdatesDTO) {
        BookEntity existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with ID " + id));
        existingBook.setTitle(bookWithUpdatesDTO.getTitle());
        existingBook.setAuthorName(bookWithUpdatesDTO.getAuthorName());
        existingBook.setISBN(bookWithUpdatesDTO.getIsbn());
        existingBook.setPublishDate(bookWithUpdatesDTO.getPublishDate());

        BookEntity updatedBook = bookRepository.save(existingBook);
        return bookMapper.convertToDTO(updatedBook);
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
