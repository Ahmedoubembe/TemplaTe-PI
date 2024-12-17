package com.Borrow.Controller;

import com.Borrow.Entity.Borrow;
import com.Borrow.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // Créer un emprunt
    @PostMapping
    public Borrow createBorrow(@RequestBody Borrow borrow) {
        return borrowService.createBorrow(borrow);
    }

    // Récupérer un emprunt par son ID
    @GetMapping("/{id}")
    public Optional<Borrow> getBorrow(@PathVariable Long id) {
        return borrowService.getBorrow(id);
    }

    // Mettre à jour un emprunt
    @PutMapping("/{id}")
    public Borrow updateBorrow(@PathVariable Long id, @RequestBody Borrow borrow) {
        return borrowService.updateBorrow(id, borrow);
    }

    // Supprimer un emprunt
    @DeleteMapping("/{id}")
    public void deleteBorrow(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
    }

    // Récupérer les emprunts en retard
    @GetMapping("/late")
    public List<Borrow> getLateBorrows() {
        return borrowService.getLateBorrows();
    }
}
