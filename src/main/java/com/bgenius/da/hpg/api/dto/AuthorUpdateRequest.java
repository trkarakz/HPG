package com.bgenius.da.hpg.api.dto;

import java.util.List;

public record AuthorUpdateRequest(
        String name,
        List<BookCreateRequest> addBooks
) { }