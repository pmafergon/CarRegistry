package com.mafer.CarRegisty.service;

import com.mafer.CarRegisty.service.domain.Car;

import java.util.List;

public interface CarService {

    List<Car> findAll() throws Exception;
    Car save(Car car) throws Exception;
    String delete(Integer id);
    Car update(Integer id, Car car) throws Exception;
    Car getById(Integer id);

}
