package org.example.DAO;

import org.example.DTO.CarDTO;

import java.util.Set;

public interface iDAO<T, V> {
    Set<T> getAll();

    T getById(int id);

    T create(V v);

    T update(V v);

    T delete(int id);

    CarDTO create(CarDTO carDTO);

    Set<T> getCarsBySeller(int sellerId);

    CarDTO update(CarDTO carDTO);

    void addCarToSeller(int sellerId, int carId);

}
