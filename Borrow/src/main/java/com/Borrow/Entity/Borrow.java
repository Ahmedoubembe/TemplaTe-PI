  package com.Borrow.Entity;

  import jakarta.persistence.*;
  import lombok.Data;

  import java.math.BigDecimal;
  import java.time.LocalDate;

  @Entity
  @Table(name = "borrowers")
  @Data
  public class Borrow {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(name = "user_id")
      private Long userId; // Identifiant de l'utilisateur

      @Column(name = "book_id")
      private Long bookId; // Identifiant du livre

      @Column(name = "date_emprunt")
      private LocalDate dateEmprunt;

      @Column(name = "date_retour_prevu")
      private LocalDate dateRetourPrevu;

      @Column(name = "date_retour_reel")
      private LocalDate dateRetourReel;

      @Enumerated(EnumType.STRING)
      private EtatPret etat;

      @Column(name = "montant_penalite")
      private BigDecimal montantPenalite;
  }
