package com.bgenius.da.hpg.repo;

import com.bgenius.da.hpg.domain.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select distinct a from Author a left join fetch a.books")
    List<Author> findAllWithBooksJoinFetch();

    @EntityGraph(attributePaths = "books")
    @Query("select a from Author a")
    List<Author> findAllWithBooksEntityGraph();
}
