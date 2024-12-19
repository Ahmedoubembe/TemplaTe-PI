package com.Inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Nom de l'article

    @Column(nullable = false)
    private int quantity; // Quantité en stock

    @Column(nullable = false)
    private double price; // Prix de l'article

    @Column
    private String description; // Description de l'article
}
