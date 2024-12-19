package com.Inventory.repository;

import com.Inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Méthodes de recherche personnalisées si nécessaires
}
