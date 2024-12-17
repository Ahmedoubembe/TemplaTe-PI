package com.Inventory.controller;

import com.Inventory.entity.Book;
import com.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Vérifier si un livre est disponible
    @GetMapping("/check/{bookId}")
    public boolean checkBookAvailability(@PathVariable Long bookId) {
        return inventoryService.isBookAvailable(bookId);
    }

    // Ajouter un livre à l'inventaire
    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return inventoryService.addBook(book);
    }

    // Réduire la quantité d'un livre (utilisé après un emprunt)
    @PutMapping("/borrow/{bookId}")
    public void borrowBook(@PathVariable Long bookId) {
        inventoryService.reduceBookQuantity(bookId);
    }

    // Augmenter la quantité d'un livre (utilisé après un retour)
    @PutMapping("/return/{bookId}")
    public void returnBook(@PathVariable Long bookId) {
        inventoryService.increaseBookQuantity(bookId);
    }

    // Recherche de livres par titre
    @GetMapping("/search/title")
    public List<Book> searchBooksByTitle(@RequestParam String title) {
        return inventoryService.findBooksByTitle(title);
    }

    // Recherche de livres par auteur
    @GetMapping("/search/author")
    public List<Book> searchBooksByAuthor(@RequestParam String author) {
        return inventoryService.findBooksByAuthor(author);
    }
}
