package com.mafer.CarRegisty.controller;
import com.mafer.CarRegisty.controller.dto.BrandDTO;
import com.mafer.CarRegisty.controller.dto.CarDTO;
import com.mafer.CarRegisty.controller.dto.CarDTOResponse;
import com.mafer.CarRegisty.controller.mapper.DTOMapper;
import com.mafer.CarRegisty.service.BrandService;
import com.mafer.CarRegisty.service.CarService;
import com.mafer.CarRegisty.service.domain.Brand;
import com.mafer.CarRegisty.service.domain.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class Controller {

    @Autowired
    private DTOMapper mapper;
    @Autowired
    private CarService carService;
    @Autowired
    private BrandService brandService;


    //Endpoints de CAR
    @GetMapping("/cars")
    public CompletableFuture<?> findAll(){
        try {
            CompletableFuture<List<Car>> carsFound = carService.findAll();
            List<CarDTOResponse> carsDTO = new ArrayList<>();
            carsFound.get().forEach(car -> {carsDTO.add(mapper.toDTOResponse(car));});
            return CompletableFuture.completedFuture(carsDTO);
        }catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.notFound());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        try {
            return ResponseEntity.ok().body(mapper.toDTOResponse(carService.getById(id)));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Car "+id+" not found");
        }
    }

    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        try {
            return ResponseEntity.ok().body(carService.delete(id));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Car "+id+" not found");
        }
    }
    @PostMapping("/addCars")
    public CompletableFuture<?> save(@RequestBody List<CarDTO> carDTO){
        try {
            CompletableFuture<List<Car>> carsSaved = carService.save(mapper.toModelList(carDTO));
            List<CarDTOResponse> carResponse=new ArrayList<>();
            carsSaved.get().forEach(car -> {carResponse.add(mapper.toDTOResponse(car));});
            return CompletableFuture.completedFuture(carResponse);

        } catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError());
        }

    }

    @PutMapping("/updateCar/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CarDTO carDTO){
        try {
            return ResponseEntity.ok().body(mapper.toDTOResponse(carService.update(id, mapper.toModel(carDTO))));
        }
        catch (Exception e){
            return ResponseEntity.ofNullable("Car not found");
        }
    }

    //Endpoints de Brand
    @GetMapping("/brands")
    public CompletableFuture<?> findAllB(){
        try {
            CompletableFuture<List<Brand>> foundbrands= brandService.findAllB();
            List<BrandDTO> brandDTOS = new ArrayList<>();
            foundbrands.get().forEach(brand -> {brandDTOS.add(mapper.btoDTO(brand));});
            return CompletableFuture.completedFuture(brandDTOS);
        }catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.notFound());
        }
    }

    @GetMapping("/getBrandById/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Integer id){
        try{
            return ResponseEntity.ok().body(mapper.btoDTO(brandService.getBrandById(id)));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Brand "+id+" not found");
        }

    }

    @DeleteMapping("/deleteBrand/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Integer id){
        try {
            return ResponseEntity.ok().body(brandService.deleteB(id));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Brand "+id+" not found");
        }
    }
    @PostMapping("/addBrands")
    public CompletableFuture<?> saveB(@RequestBody List<BrandDTO> brandDTO){
        try {
            CompletableFuture<List<Brand>> savedBrands = brandService.saveB(mapper.btoModelList(brandDTO));
            List<BrandDTO> brandDTOS=new ArrayList<>();
            savedBrands.get().forEach(brand -> {brandDTOS.add(mapper.btoDTO(brand));});
            return CompletableFuture.completedFuture(savedBrands);

        }
        catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError());
        }
    }

    @PutMapping("/updateBrand/{id}")
    public ResponseEntity<?> updateB(@PathVariable Integer id, @RequestBody BrandDTO brandDTO){
        try {
            return ResponseEntity.ok().body(mapper.btoDTO(brandService.updateB(id, mapper.btoModel(brandDTO))));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Brand not found");
        }
    }

}



