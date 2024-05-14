package org.example.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.DTO.CarDTO;
import org.example.Exceptions.ApiException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Ressources.Car;
import org.example.Ressources.Seller;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CarDAO implements iDAO <CarDTO, CarDTO>{
    private static CarDAO instance;
    private final EntityManagerFactory entityManagerFactory;

    private CarDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public static CarDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new CarDAO(emf);
        }
        return instance;
    }

    // convert Car to CarDTO
    private CarDTO convertToDTO(Car car) {
        return new CarDTO(
                car.getBrand(),
                car.getModel(),
                car.getMake(),
                car.getYear(),
                car.getFirstRegistrationDate(),
                car.getPrice(),
                car.getId()
        );
    }

    @Override
    public Set<CarDTO> getAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            Set<Car> cars = new HashSet<>(query.getResultList());
            return cars.stream().map(this::convertToDTO).collect(Collectors.toSet());
        }
    }
    @Override
    public CarDTO getById(int id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Car car = entityManager.find(Car.class, id);
            return convertToDTO(car);
        }
    }
    @Override
    public CarDTO create(CarDTO carDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Car car = new Car(
                    carDTO.getBrand(),
                    carDTO.getModel(),
                    carDTO.getMake(),
                    carDTO.getYear(),
                    carDTO.getFirstRegistrationDate(),
                    carDTO.getPrice()
            );
            entityManager.persist(car);
            transaction.commit();
            return convertToDTO(car);
        }
    }
    @Override
    public CarDTO update(CarDTO carDTO) throws ApiException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Car car = entityManager.find(Car.class, carDTO.getId());
            if (car != null) {
                car.setBrand(carDTO.getBrand());
                car.setModel(carDTO.getModel());
                car.setMake(carDTO.getMake());
                car.setYear(carDTO.getYear());
                car.setFirstRegistrationDate(carDTO.getFirstRegistrationDate());
                car.setPrice(carDTO.getPrice());
                entityManager.merge(car);
            } else {
                throw new ApiException(404, "Car not found");
            }
            transaction.commit();
            return convertToDTO(car);
        }
    }

    @Override
    public CarDTO delete(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Car car = entityManager.find(Car.class, id);
            if (car != null) {
                entityManager.remove(car);
                transaction.commit();
                return convertToDTO(car);
            } else {
                throw new IllegalArgumentException("Car not found");
            }
        }
    }

    @Override
    public void addCarToSeller(int sellerId, int carId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Seller seller = entityManager.find(Seller.class, sellerId);
            Car car = entityManager.find(Car.class, carId);
            if (seller != null && car != null) {
                car.setSeller(seller);
                entityManager.persist(car);
                transaction.commit();
            } else {
                throw new IllegalArgumentException("Seller or Car not found");
            }
        }
    }

    @Override
    public Set<CarDTO> getCarsBySeller(int sellerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            Query query = entityManager.createQuery("SELECT c FROM Car c WHERE c.seller.id = :sellerId", Car.class);
            query.setParameter("sellerId", sellerId);
            Set<Car> cars = new HashSet<>(query.getResultList());
            return cars.stream().map(this::convertToDTO).collect(Collectors.toSet());
        }
    }
}
