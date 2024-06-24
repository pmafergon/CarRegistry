package com.mafer.CarRegisty.controller;
import com.mafer.CarRegisty.controller.dto.BrandDTO;
import com.mafer.CarRegisty.controller.dto.CarDTO;
import com.mafer.CarRegisty.controller.dto.CarDTOResponse;
import com.mafer.CarRegisty.controller.mapper.DTOMapper;
import com.mafer.CarRegisty.service.BrandService;
import com.mafer.CarRegisty.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



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
    public ResponseEntity<?> findAll(){
        try {
            return ResponseEntity.ok().body(mapper.toDTOResponseList(carService.findAll()));
        }
        catch (Exception e){
            return ResponseEntity.ofNullable("No cars found");
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
    @PostMapping("/addCar")
    public ResponseEntity<?> save(@RequestBody CarDTO carDTO){
        try {
            return ResponseEntity.ok().body(mapper.toDTOResponse(carService.save(mapper.toModel(carDTO))));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
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
    public ResponseEntity<?> findAllB(){
        try {
            return ResponseEntity.ok().body(mapper.BtoDTOList(brandService.findAllB()));
        }
        catch (Exception e){
            return ResponseEntity.ofNullable("No brands found");
        }
    }

    @GetMapping("/getBrandById/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Integer id){
        try{
            return ResponseEntity.ok().body(mapper.BtoDTO(brandService.getBrandById(id)));
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
    @PostMapping("/addBrand")
    public ResponseEntity<?> saveB(@RequestBody BrandDTO brandDTO){
        try {
            return ResponseEntity.ok().body(mapper.BtoDTO(brandService.saveB(mapper.BtoModel(brandDTO))));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Brand already added");
        }
    }

    @PutMapping("/updateBrand/{id}")
    public ResponseEntity<?> updateB(@PathVariable Integer id, @RequestBody BrandDTO brandDTO){
        try {
            return ResponseEntity.ok().body(mapper.BtoDTO(brandService.updateB(id, mapper.BtoModel(brandDTO))));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Brand not found");
        }
    }

}



