package com.Inventory.controller;

import com.Inventory.entity.Inventory;
import com.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // 1. Ajouter un nouveau livre dans l'inventaire
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory newInventory = inventoryService.createInventory(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInventory);
    }

    // 2. Récupérer la liste complète des livres
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    // 3. Récupérer un livre spécifique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    // 4. Mettre à jour les détails d’un livre
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
        return ResponseEntity.ok(updatedInventory);
    }

    // 5. Supprimer un livre de l'inventaire
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok("Inventory deleted successfully.");
    }

    // 6. Vérifier si un livre est disponible (stock > 0)
    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> isBookAvailable(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        boolean available = inventory.getQuantity() > 0;
        return ResponseEntity.ok(available);
    }

    // 7. Diminuer la quantité d’un livre (emprunt)
    @PostMapping("/{id}/decrease")
    public ResponseEntity<String> decreaseQuantity(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory.getQuantity() > 0) {
            inventory.setQuantity(inventory.getQuantity() - 1);
            inventoryService.save(inventory);
            return ResponseEntity.ok("Stock decreased successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock is empty for this book.");
        }
    }

    // 8. Augmenter la quantité d’un livre (retour)
    @PostMapping("/{id}/increase")
    public ResponseEntity<String> increaseQuantity(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        inventory.setQuantity(inventory.getQuantity() + 1);
        inventoryService.save(inventory);
        return ResponseEntity.ok("Stock increased successfully.");
    }
}
