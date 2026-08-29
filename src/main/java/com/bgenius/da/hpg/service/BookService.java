package com.bgenius.da.hpg.service;

import com.bgenius.da.hpg.api.dto.BookWithAuthorDto;
import com.bgenius.da.hpg.domain.Book;
import com.bgenius.da.hpg.repo.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<BookWithAuthorDto> nPlusOne() {
        List<Book> books = bookRepository.findAll();
        return toDtoList(books);
    }

    @Transactional(readOnly = true)
    public List<BookWithAuthorDto> joinFetch() {
        List<Book> books = bookRepository.findAllWithAuthorJoinFetch();
        return toDtoList(books);
    }

    @Transactional(readOnly = true)
    public List<BookWithAuthorDto> entityGraph() {
        List<Book> books = bookRepository.findAllWithAuthorEntityGraph();
        return toDtoList(books);
    }

    private List<BookWithAuthorDto> toDtoList(List<Book> books) {
        return books.stream()
                .map(b -> new BookWithAuthorDto(
                        b.getId(),
                        b.getTitle(),
                        b.getAuthor().getId(),
                        b.getAuthor().getName()
                ))
                .toList();
    }
}
