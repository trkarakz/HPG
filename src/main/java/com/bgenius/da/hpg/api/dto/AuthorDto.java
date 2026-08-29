package com.bgenius.da.hpg.api.dto;

import java.util.List;

public record AuthorDto(Long id, String name, List<BookDto> books) { }