package com.bgenius.da.hpg.repo;

import com.bgenius.da.hpg.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join fetch b.author")
    List<Book> findAllWithAuthorJoinFetch();

    @EntityGraph(attributePaths = "author")
    @Query("select b from Book b")
    List<Book> findAllWithAuthorEntityGraph();
}
