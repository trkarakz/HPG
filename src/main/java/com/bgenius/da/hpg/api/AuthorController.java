package com.bgenius.da.hpg.api;

import com.bgenius.da.hpg.api.dto.AuthorCreateRequest;
import com.bgenius.da.hpg.api.dto.AuthorDto;
import com.bgenius.da.hpg.api.dto.AuthorUpdateRequest;
import com.bgenius.da.hpg.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/n-plus-one")
    public List<AuthorDto> nPlusOne() {
        return authorService.nPlusOne();
    }

    @GetMapping("/join-fetch")
    public List<AuthorDto> joinFetch() {
        return authorService.joinFetch();
    }

    @GetMapping("/entity-graph")
    public List<AuthorDto> entityGraph() {
        return authorService.entityGraph();
    }

    @GetMapping("/{id}")
    public AuthorDto getSummary(@PathVariable long id) {
        return authorService.getAuthor(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody AuthorCreateRequest request) {
        return authorService.create(request);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AuthorDto> createBulk(@RequestBody List<AuthorCreateRequest> requests) {
        return authorService.createBulk(requests);
    }

    @PutMapping("/{id}")
    public AuthorDto update(@PathVariable("id") long id, @RequestBody AuthorUpdateRequest request) {
        return authorService.update(id, request);
    }
}