package org.example.Handlers;

import io.javalin.http.Handler;
import org.example.DAO.CarDAO;
import org.example.DTO.CarDTO;
import org.example.Exceptions.CarNotFoundException;
import jakarta.persistence.EntityManagerFactory;
import org.example.Config.HibernateConfig;

public class CarHandler {

    private static CarHandler instance;
    private CarDAO carDAO;

    private CarHandler() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory(true);
        this.carDAO = CarDAO.getInstance(emf);
    }

    public static CarHandler getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new CarHandler();
        }
        return instance;
    }

    public Handler getAllCars() {
        return ctx -> {
            var cars = carDAO.getAll();
            if (cars.isEmpty()) {
                throw new CarNotFoundException(404, "No cars found.");
            } else {
                ctx.json(cars);
            }
        };
    }

    public Handler getCarById() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                CarDTO car = carDAO.getById(id);
                if (car == null) {
                    throw new CarNotFoundException(404, "No cars found.");
                } else {
                    ctx.json(car);
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid ID format");
            }
        };
    }

    public Handler createCar() {
        return ctx -> {
            CarDTO car = ctx.bodyAsClass(CarDTO.class);
            CarDTO createdCar = carDAO.create(car);
            if (createdCar != null) {
                ctx.status(201).json(createdCar);
            } else {
                ctx.status(500).result("Car could not be created.");
            }
        };
    }

    public Handler updateCar() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                CarDTO carToUpdate = ctx.bodyAsClass(CarDTO.class);
                carToUpdate.setId(id);
                CarDTO updatedCar = carDAO.update(carToUpdate);
                if (updatedCar != null) {
                    ctx.json(updatedCar);
                } else {
                    throw new CarNotFoundException(404, "Car not found or car could not be updated");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid ID format");
            }
        };
    }

    public Handler deleteCar() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                CarDTO deletedCar = carDAO.delete(id);
                if (deletedCar != null) {
                    ctx.result("Car deleted successfully.");
                } else {
                    throw new CarNotFoundException(404, "Car not found");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid ID format");
            }
        };
    }
}
