package com.Inventory.service;

import com.Inventory.entity.Inventory;
import com.Inventory.exception.ResourceNotFoundException;
import com.Inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Override
    public Inventory createInventory(Inventory inventory) {
        return repository.save(inventory);
    }

    @Override
    public List<Inventory> getAllInventories() {
        return repository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Inventory inventory = getInventoryById(id);
        inventory.setName(inventoryDetails.getName());
        inventory.setQuantity(inventoryDetails.getQuantity());
        inventory.setPrice(inventoryDetails.getPrice());
        inventory.setDescription(inventoryDetails.getDescription());
        return repository.save(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = getInventoryById(id);
        repository.delete(inventory);
    }

    // Implement the save method
    @Override
    public Inventory save(Inventory inventory) {
        return repository.save(inventory);
    }
}
