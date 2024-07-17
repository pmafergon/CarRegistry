package com.mafer.car.registry.service;

import com.mafer.car.registry.service.domain.Car;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {

    List<Car> findAll();
    CompletableFuture<List<Car>> save(List<Car> car) throws HttpServerErrorException.InternalServerError;
    String delete(Integer id);
    Car update(Integer id, Car car) throws NullPointerException ;
    Car getById(Integer id);
    String carCsv();
    List<Car> uploadCsvCars(MultipartFile file);

}
