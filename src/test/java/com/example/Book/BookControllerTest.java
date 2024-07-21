package com.example.Book;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private BookDTO bookDTO;
    private BookDTO updatedBookDTO;
    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO(1L, "Title", "Author", "ISBN", LocalDate.now());
        updatedBookDTO = new BookDTO(1L, "Updated Title", "Updated Author", "Updated ISBN", LocalDate.now());
    }
    @Test
    void createBook_shouldReturnCreatedBook() throws Exception {
        when(bookService.createBook(any(BookDTO.class))).thenReturn(bookDTO);
        mockMvc.perform(post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.authorName").value(bookDTO.getAuthorName()))
                .andExpect(jsonPath("$.isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.publishDate").value(bookDTO.getPublishDate().toString()));
    }

    @Test
    void getAllBooks_shouldReturnListOfBooks() throws Exception {
        List<BookDTO> books = Arrays.asList(bookDTO, updatedBookDTO);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<BookDTO> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookService.getAllBooks(pageable)).thenReturn(bookPage);
        mockMvc.perform(get("/books/")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.content[1].title").value(updatedBookDTO.getTitle()))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.sort.sorted").value(true))
                .andExpect(jsonPath("$.sort.empty").value(false))
                .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
                .andExpect(jsonPath("$.pageable.sort.empty").value(false));

    }


    @Test
    void getBookById_shouldReturnBook() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(bookDTO);

        mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.authorName").value(bookDTO.getAuthorName()))
                .andExpect(jsonPath("$.isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.publishDate").value(bookDTO.getPublishDate().toString()));
    }

    @Test
    void getBookById_NotFound() throws Exception {
        when(bookService.getBookById(anyLong())).thenThrow(new EntityNotFoundException("No Book found with ID 1"));

        mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().is(404));
    }

    @Test
    void updateBook_shouldReturnUpdatedBook() throws Exception {
        when(bookService.updateBook(anyLong(), any(BookDTO.class))).thenReturn(updatedBookDTO);

        mockMvc.perform(put("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedBookDTO.getTitle()))
                .andExpect(jsonPath("$.authorName").value(updatedBookDTO.getAuthorName()))
                .andExpect(jsonPath("$.isbn").value(updatedBookDTO.getIsbn()))
                .andExpect(jsonPath("$.publishDate").value(updatedBookDTO.getPublishDate().toString()));
    }

    @Test
    void deleteBook_shouldReturnOk() throws Exception {
        doNothing().when(bookService).deleteBook(anyLong());
        mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook_nonExistingId_shouldReturnNotFound() throws Exception {
        doThrow(new EntityNotFoundException("No Book found with ID 1")).when(bookService).deleteBook(anyLong());

        mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().is(404));
    }

}
