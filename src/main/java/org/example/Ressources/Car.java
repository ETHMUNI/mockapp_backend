package org.example.Ressources;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "make")
    private String make;
    @Column(name = "year")
    private int year;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "first_registration_date")
    private LocalDate firstRegistrationDate;
    @Column(name = "price")
    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public Car(String brand, String model, String make, int year, LocalDate firstRegistrationDate, double price) {
        this.brand = brand;
        this.model = model;
        this.make = make;
        this.year = year;
        this.firstRegistrationDate = firstRegistrationDate;
        this.price = price;
    }

    public Car() {

    }
}


