package com.inchcape.inchcapeisthebest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
public class Rental {
    @Id
    private Long rentalId;
    private LocalDateTime rentalDate;
    private Long inventoryId;
    private Long customerId;
    private LocalDateTime returnDate;
    private Long staffId;
    private LocalDateTime lastUpdate;

    // Constructors, getters, and setters
}