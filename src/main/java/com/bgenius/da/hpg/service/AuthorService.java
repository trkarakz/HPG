package com.bgenius.da.hpg.service;

import com.bgenius.da.hpg.api.dto.*;
import com.bgenius.da.hpg.domain.Author;
import com.bgenius.da.hpg.repo.AuthorRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> nPlusOne() {
        List<Author> authors = authorRepository.findAll();

        return getAuthorDtoList(authors);
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> joinFetch() {
        List<Author> authors = authorRepository.findAllWithBooksJoinFetch();

        return getAuthorDtoList(authors);
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> entityGraph() {
        List<Author> authors = authorRepository.findAllWithBooksEntityGraph();

        return getAuthorDtoList(authors);
    }

    @Transactional
    public AuthorDto create(AuthorCreateRequest request) {
        if (request == null || request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("Author name must not be blank");
        }

        Author author = new Author(request.name().trim());

        List<BookCreateRequest> books = request.books();
        return getAuthorDto(author, books);
    }

    @Transactional
    public AuthorDto update(long authorId, AuthorUpdateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body must not be null");
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found: " + authorId));

        if (request.name() != null) {
            String newName = request.name().trim();
            if (newName.isBlank()) {
                throw new IllegalArgumentException("Author name must not be blank");
            }
            author.setName(newName);
        }

        List<BookCreateRequest> toAdd = request.addBooks();
        return getAuthorDto(author, toAdd);
    }

    @Transactional
    public List<AuthorDto> createBulk(List<AuthorCreateRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Request list must not be empty");
        }

        List<AuthorDto> result = new ArrayList<>(requests.size());
        for (AuthorCreateRequest request : requests) {
            result.add(create(request));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthor(long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found: " + authorId));

        // do NOT call author.getBooks() here
        return new AuthorDto(author.getId(), author.getName(), List.of());
    }

    private List<AuthorDto> getAuthorDtoList(List<Author> authors) {
        return authors.stream()
                .map(a -> new AuthorDto(
                        a.getId(),
                        a.getName(),
                        a.getBooks().stream().map(b -> new BookDto(b.getId(), b.getTitle())).toList()
                ))
                .toList();
    }

    private AuthorDto getAuthorDto(Author author, List<BookCreateRequest> books) {
        if (books != null) {
            for (BookCreateRequest b : books) {
                if (b != null && b.title() != null && !b.title().isBlank()) {
                    author.addBook(b.title().trim());
                }
            }
        }

        Author saved = authorRepository.save(author);

        return new AuthorDto(
                saved.getId(),
                saved.getName(),
                saved.getBooks().stream()
                        .map(book -> new BookDto(book.getId(), book.getTitle()))
                        .toList()
        );
    }
}
