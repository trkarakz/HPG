package com.bgenius.da.hpg.api.dto;

import java.util.List;

public record AuthorCreateRequest(
        String name,
        List<BookCreateRequest> books
) { }