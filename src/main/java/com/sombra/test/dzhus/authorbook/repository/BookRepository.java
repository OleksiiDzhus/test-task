package com.sombra.test.dzhus.authorbook.repository;

import com.sombra.test.dzhus.authorbook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
