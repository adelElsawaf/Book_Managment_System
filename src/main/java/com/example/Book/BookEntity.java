package com.example.Book;

import jakarta.persistence.*;


import java.awt.print.Book;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "books")
public class BookEntity{
    @Column(name = "bookId")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String authorName;
    private String ISBN;
    private LocalDate publishDate;

    public BookEntity(Long id, String title, String authorName, String ISBN, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.ISBN = ISBN;
        this.publishDate = publishDate;
    }

    public BookEntity(String title, String authorName, String ISBN, LocalDate publishDate) {
        this.title = title;
        this.authorName = authorName;
        this.ISBN = ISBN;
        this.publishDate = publishDate;
    }
    public BookEntity(){}

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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}


