package com.example.Book;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    private BookService bookService;
    private BookDTO underTestBookDTO;
    private BookEntity underTestBookEntity;


    @BeforeEach
    void setup() {
        bookService = new BookService(bookRepository, bookMapper);
        underTestBookDTO = new BookDTO("Title", "Author Name", "ISBN", LocalDate.now());
        underTestBookEntity = new BookEntity(underTestBookDTO.getTitle(), underTestBookDTO.getAuthorName(), underTestBookDTO.getIsbn(), underTestBookDTO.getPublishDate());
    }

    @Test
    @DisplayName("Create Book Success")
    void testCreateBook_SuccessfullySavesBook() throws InstantiationException, IllegalAccessException {
        when(bookMapper.convertToBookEntity(underTestBookDTO)).thenReturn(underTestBookEntity);
        bookService.createBook(underTestBookDTO);
        verify(bookRepository).save(underTestBookEntity);
    }

    @Test
    @DisplayName("GetBookById Success")
    void testGetBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(underTestBookEntity));
        when(bookMapper.convertToDTO(underTestBookEntity)).thenReturn(underTestBookDTO);
        BookDTO result = bookService.getBookById(1L);
        assertEquals(underTestBookDTO, result);
    }

    @Test
    @DisplayName("GetBookById No book found")
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            bookService.getBookById(1L);
        });
        assertEquals("No book found with ID " + 1L, thrown.getMessage());
    }

    @Test
    @DisplayName("Update Book Success")
    void testUpdateBook_success() {
        Long id = 1L;
        BookDTO bookWithUpdatesDTO = new BookDTO(id, "New Title", "New Author", "1234567890", LocalDate.now());
        BookEntity updatedBook = new BookEntity(id, "New Title", "New Author", "1234567890", LocalDate.now());
        when(bookRepository.findById(id)).thenReturn(Optional.of(underTestBookEntity));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(updatedBook);
        when(bookMapper.convertToDTO(updatedBook)).thenReturn(bookWithUpdatesDTO);
        BookDTO updatedBookDTO = bookService.updateBook(id, bookWithUpdatesDTO);
        assertThat(updatedBookDTO).isEqualTo(bookWithUpdatesDTO);
    }


    @Test
    @DisplayName("Get All books Success")
    void getAllBooks_returnsListOfBookDTOs() {
        BookEntity firstBookEntity = new BookEntity(1L, "Title1", "Author1", "ISBN1", LocalDate.now());
        BookEntity secondBookEntity = new BookEntity(2L, "Title2", "Author2", "ISBN2", LocalDate.now());
        BookDTO firstBookDTO = new BookDTO(1L, "Title1", "Author1", "ISBN1", LocalDate.now());
        BookDTO secondBookDTO = new BookDTO(2L, "Title2", "Author2", "ISBN2", LocalDate.now());
        List<BookEntity> books = List.of(firstBookEntity, secondBookEntity);

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<BookEntity> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.convertToDTO(firstBookEntity)).thenReturn(firstBookDTO);
        when(bookMapper.convertToDTO(secondBookEntity)).thenReturn(secondBookDTO);

        Page<BookDTO> result = bookService.getAllBooks(pageable);

        assertThat(result.getContent()).containsExactlyInAnyOrder(firstBookDTO, secondBookDTO);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getSort()).isEqualTo(Sort.by(Sort.Direction.ASC, "id"));
    }
    @Test
    @DisplayName("Delete by existing id success")
    void testDeleteBook() {
        Long bookId = 1L;
        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }



}



















