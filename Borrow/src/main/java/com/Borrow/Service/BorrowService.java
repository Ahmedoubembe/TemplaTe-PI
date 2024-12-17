package com.Borrow.Service;

import com.Borrow.Entity.Borrow;
import com.Borrow.Entity.BorrowStatus;
import com.Borrow.Repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private RestTemplate restTemplate;


    // Méthode CRUD pour ajouter un emprunt
    private static final String INVENTORY_SERVICE_URL = "http://localhost:8082/inventory/borrow/";

    // Méthode CRUD pour ajouter un emprunt
    public Borrow createBorrow(Borrow borrow) {
        // Vérification de la disponibilité du livre avant de créer l'emprunt
        if (isBookAvailable(borrow.getBookId())) {
            borrow.setStatus(BorrowStatus.BORROWED);
            borrow.setBorrowDate(LocalDate.now());
            borrow.setDueDate(LocalDate.now().plusDays(14));  // L'emprunt dure 14 jours

            // Réduire la quantité du livre dans l'inventaire
            String url = INVENTORY_SERVICE_URL + borrow.getBookId();
            restTemplate.exchange(url, HttpMethod.PUT, null, Void.class);

            return borrowRepository.save(borrow);
        }
        throw new IllegalStateException("Le livre n'est pas disponible.");
    }

    // Vérifier si un livre est disponible dans le service Inventory
    public boolean isBookAvailable(Long bookId) {
        return restTemplate.getForObject("http://localhost:8082/inventory/check/" + bookId, Boolean.class);
    }

    // Méthode pour récupérer un emprunt par ID
    public Optional<Borrow> getBorrow(Long borrowId) {
        return borrowRepository.findById(borrowId);
    }

    // Méthode pour mettre à jour un emprunt
    public Borrow updateBorrow(Long borrowId, Borrow borrowDetails) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new IllegalStateException("Emprunt introuvable"));
        borrow.setBookId(borrowDetails.getBookId());
        borrow.setUserId(borrowDetails.getUserId());
        borrow.setStatus(borrowDetails.getStatus());
        return borrowRepository.save(borrow);
    }

    // Méthode pour supprimer un emprunt
    public void deleteBorrow(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new IllegalStateException("Emprunt introuvable"));
        borrowRepository.delete(borrow);
    }

    // Méthode pour obtenir les emprunts en retard
    public List<Borrow> getLateBorrows() {
        LocalDate today = LocalDate.now();
        return borrowRepository.findByDueDateBeforeAndStatus(today, BorrowStatus.BORROWED);
    }


}
