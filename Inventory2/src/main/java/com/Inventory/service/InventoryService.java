package com.Inventory.service;

import com.Inventory.entity.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory createInventory(Inventory inventory);
    List<Inventory> getAllInventories();
    Inventory getInventoryById(Long id);
    Inventory updateInventory(Long id, Inventory inventoryDetails);
    void deleteInventory(Long id);

    // Add the save method
    Inventory save(Inventory inventory);
}
