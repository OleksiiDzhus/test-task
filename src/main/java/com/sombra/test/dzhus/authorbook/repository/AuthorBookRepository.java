package com.sombra.test.dzhus.authorbook.repository;

import com.sombra.test.dzhus.authorbook.model.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBook, Long> {

}
