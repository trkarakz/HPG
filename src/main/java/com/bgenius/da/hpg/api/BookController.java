package com.bgenius.da.hpg.api;

import com.bgenius.da.hpg.api.dto.BookWithAuthorDto;
import com.bgenius.da.hpg.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/n-plus-one")
    public List<BookWithAuthorDto> nPlusOne() {
        return bookService.nPlusOne();
    }

    @GetMapping("/join-fetch")
    public List<BookWithAuthorDto> joinFetch() {
        return bookService.joinFetch();
    }

    @GetMapping("/entity-graph")
    public List<BookWithAuthorDto> entityGraph() {
        return bookService.entityGraph();
    }
}
