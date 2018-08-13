package com.sombra.test.dzhus.authorbook.repository;

import com.sombra.test.dzhus.authorbook.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
