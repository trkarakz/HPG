package com.bgenius.da.hpg.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // TODO try these to demonstrate the performance difference
    @BatchSize(size = 2)
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    protected Author() { }

    public Author(String name) {
        this.name = name;
    }

    public void addBook(String title) {
        Book book = new Book(title, this);
        books.add(book);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Book> getBooks() { return books; }

    public void setName(String name) {
        this.name = name;
    }
}
