package com.mafer.CarRegisty.service;

import com.mafer.CarRegisty.service.domain.Car;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {

    CompletableFuture<List<Car>> findAll();
    CompletableFuture<List<Car>> save(List<Car> car) throws Exception;
    String delete(Integer id);
    Car update(Integer id, Car car) throws Exception ;
    Car getById(Integer id);

}
