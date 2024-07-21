package com.example.Book;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookDTO {
    private Long id;

    @NotNull(message = "Title could not be null")
    @NotBlank(message = "Title could not be blank")
    private String title;

    @NotNull(message = "Author Name could not be null")
    @NotBlank(message = "Author Name could not be blank")
    private String authorName;

    @NotNull(message = "ISBN could not be null")
    @NotBlank(message = "ISBN could not be blank")
    private String isbn;

    @NotNull(message = "Publish Date could not be null")
    private LocalDate publishDate;

    public BookDTO(Long id, String title, String authorName, String isbn, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }
    public BookDTO(String title, String authorName, String isbn, LocalDate publishDate) {
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}
