package org.example.Routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityManagerFactory;
import org.example.Handlers.CarHandler;
import org.example.Handlers.SecurityHandler;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    private static SecurityHandler securityHandler;
    private static CarHandler carHandler;


    public static EndpointGroup getSecurityRoutes(EntityManagerFactory emf) {
        securityHandler = SecurityHandler.getInstance(emf); // getting the singleton instance

        return () -> {
            path("/auth", () -> {
                post("/login", securityHandler.login(), Role.ANYONE);
                post("/register", securityHandler.register(), Role.ANYONE);
                before(securityHandler.authenticate());
                post("/reset-password", securityHandler.resetPassword(), Role.USER, Role.ADMIN);
                post("/logout", securityHandler.logout(), Role.USER, Role.ADMIN);
            });
        };
    }

    public static EndpointGroup getCarRoutes(EntityManagerFactory emf) {
        carHandler = CarHandler.getInstance(emf);
        securityHandler = SecurityHandler.getInstance(emf); // getting the singleton instance

        return () -> {
            path("/carshop", () -> {
                before(securityHandler.authenticate());
                get("/cars", carHandler.getAllCars(), Role.USER, Role.ADMIN);
                get("/cars/{id}", carHandler.getCarById(), Role.ADMIN);
                post("/cars", carHandler.createCar(), Role.ADMIN);
                put("/cars/{id}", carHandler.updateCar(), Role.ADMIN);
                delete("/cars/{id}", carHandler.deleteCar(), Role.ADMIN);
            });
        };
    }

    // Test Routes
    public static EndpointGroup getTestCarRoutes(EntityManagerFactory emf) {
        carHandler = CarHandler.getInstance(emf);
        securityHandler = SecurityHandler.getInstance(emf); // getting the singleton instance

        return () -> {
            path("/carshop", () -> {
                get("/cars", carHandler.getAllCars(), Role.ANYONE);
                get("/cars/{id}", carHandler.getCarById(), Role.ANYONE);
                post("/cars", carHandler.createCar(), Role.ANYONE);
                put("/cars/{id}", carHandler.updateCar(), Role.ANYONE);
                delete("/cars/{id}", carHandler.deleteCar(), Role.ANYONE);
            });
        };
    }
    public enum Role implements RouteRole {ANYONE, USER, ADMIN}
}
