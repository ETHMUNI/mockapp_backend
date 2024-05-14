package org.example.DAO;

import org.example.DTO.CarDTO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CarDAOMock {
    private static Map<Integer, CarDTO> cars;

    static {
        cars = new HashMap<>();
        populateData();
    }

    public CarDAOMock() {
    }

    private static void populateData() {
        cars.put(1, new CarDTO("Toyota", "Camry", "Sedan", 2020, LocalDate.of(2020, 2, 10), 25000.00, 1));
        cars.put(2, new CarDTO("Ford", "Mustang", "Coupe", 2022, LocalDate.of(2022, 3, 15), 35000.00, 2));
        cars.put(3, new CarDTO("Honda", "Civic", "Sedan", 2019, LocalDate.of(2019, 8, 20), 20000.00, 3));
        cars.put(4, new CarDTO("Chevrolet", "Malibu", "Sedan", 2021, LocalDate.of(2021, 5, 22), 22000.00, 4));
        cars.put(5, new CarDTO("Tesla", "Model 3", "Sedan", 2023, LocalDate.of(2023, 1, 5), 48000.00, 5));
    }

    public Set<CarDTO> getAll() {
        return new HashSet<>(cars.values());
    }

    public CarDTO getById(int id) {
        return cars.get(id);
    }

    public CarDTO create(CarDTO car) {
        if (!cars.containsKey(car.getId())) {
            cars.put(car.getId(), car);
        }
        return car;
    }

    public CarDTO update(CarDTO car) {
        if (cars.containsKey(car.getId())) {
            cars.put(car.getId(), car);
        }
        return car;
    }

    public CarDTO delete(int id) {
        return cars.remove(id);
    }

    // 3.1
    public List<CarDTO> filterCarsByYear(int year) {
        return cars.values().stream()
                .filter(car -> car.getYear() == year)
                .collect(Collectors.toList());
    }

    // 3.2
    public Map<String, Double> groupCarsByBrandAndTotalPrice() {
        return cars.values().stream()
                .collect(Collectors.groupingBy(
                        car -> car.getBrand(),
                        Collectors.summingDouble(car -> car.getPrice())
                ));
    }
}

