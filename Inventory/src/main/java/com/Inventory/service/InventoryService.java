package com.Inventory.service;
import com.Inventory.entity.Book;
import com.Inventory.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class InventoryService {

    @Autowired
    private BookRepository bookRepository;

    // Vérifier la disponibilité d'un livre
    public boolean isBookAvailable(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        return bookOpt.isPresent() && bookOpt.get().getQuantity() > 0;
    }

    // Réduire la quantité d'un livre (après un emprunt)
    public void reduceBookQuantity(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.getQuantity() > 0) {
                book.setQuantity(book.getQuantity() - 1);
                bookRepository.save(book);
            } else {
                throw new RuntimeException("Le livre n'est pas disponible.");
            }
        } else {
            throw new RuntimeException("Livre non trouvé.");
        }
    }

    // Ajouter un livre dans l'inventaire
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Augmenter la quantité d'un livre (après un retour)
    public void increaseBookQuantity(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Livre non trouvé.");
        }
    }

    // Recherche de livres par titre
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // Recherche de livres par auteur
    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
}