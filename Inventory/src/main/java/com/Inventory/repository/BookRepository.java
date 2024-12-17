package com.Inventory.repository;

import com.Inventory.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);  // Recherche par titre
    List<Book> findByAuthor(String author);  // Recherche par auteur
}
