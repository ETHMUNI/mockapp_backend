package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.Ressources.Car;
import org.example.Ressources.Seller;

import java.time.LocalDate;

public class Populator {

    private final EntityManagerFactory entityManagerFactory;

    public Populator(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void populate() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Create Sellers
            Seller seller1 = new Seller("John", "Doe", "john@gmail.com", "123456789", "København");
            Seller seller2 = new Seller("Jane", "Smith", "jane@gmail.com", "987654321", "Helsingør");

            // Create and associate Cars with specific Sellers
            Car car1 = new Car("Toyota", "Camry", "Sedan", 2020, LocalDate.of(2020, 2, 10), 25000.00);
            car1.setSeller(seller1);

            Car car2 = new Car("Ford", "Mustang", "Coupe", 2022, LocalDate.of(2022, 3, 15), 35000.00);
            car2.setSeller(seller1);

            Car car3 = new Car("Honda", "Civic", "Sedan", 2019, LocalDate.of(2019, 8, 20), 20000.00);
            car3.setSeller(seller2);

            Car car4 = new Car("Chevrolet", "Malibu", "Sedan", 2021, LocalDate.of(2021, 5, 22), 22000.00);
            car4.setSeller(seller2);

            Car car5 = new Car("Tesla", "Model 3", "Sedan", 2023, LocalDate.of(2023, 1, 5), 48000.00);
            car5.setSeller(seller1);

            // Persist entities
            entityManager.persist(seller1);
            entityManager.persist(seller2);
            entityManager.persist(car1);
            entityManager.persist(car2);
            entityManager.persist(car3);
            entityManager.persist(car4);
            entityManager.persist(car5);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
