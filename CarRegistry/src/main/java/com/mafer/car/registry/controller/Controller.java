package com.mafer.car.registry.controller;
import com.mafer.car.registry.controller.dto.BrandDTO;
import com.mafer.car.registry.controller.dto.CarDTO;
import com.mafer.car.registry.controller.dto.CarDTOResponse;
import com.mafer.car.registry.controller.mapper.DTOMapper;
import com.mafer.car.registry.service.BrandService;
import com.mafer.car.registry.service.CarService;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.domain.Car;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {


    private final DTOMapper mapper;

    private final CarService carService;

    private final BrandService brandService;


    //Endpoints de CAR
    @GetMapping("/cars")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> findAll(){
        try {
            return ResponseEntity.ok(mapper.toDTOResponseList(carService.findAll()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("No cars found");
        }
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        try {
            return ResponseEntity.ok().body(mapper.toDTOResponse(carService.getById(id)));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Car "+id+" not found");
        }
    }

    @DeleteMapping("/deleteCar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        try {
            return ResponseEntity.ok().body(carService.delete(id));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Car "+id+" not found");
        }
    }
    @PostMapping(value = "/addCars", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<?> save(@RequestBody List<CarDTO> carDTO){
        try {
            CompletableFuture<List<Car>> carsSaved = carService.save(mapper.toModelList(carDTO));
            List<CarDTOResponse> carResponse=new ArrayList<>();
            carsSaved.get().forEach(car -> carResponse.add(mapper.toDTOResponse(car)));
            return CompletableFuture.completedFuture(carResponse)
                    .thenApply(ResponseEntity::ok);

        } catch (Exception e){
            return CompletableFuture.failedFuture(e);
        }

    }

    @PutMapping("/updateCar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> findAllB() throws NullPointerException{
        try {
            return ResponseEntity.ok(mapper.btoDTOList(brandService.findAllB()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("No brands found");
        }
    }

    @GetMapping("/getBrandById/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getBrandById(@PathVariable Integer id){
        try{
            return ResponseEntity.ok().body(mapper.btoDTO(brandService.getBrandById(id)));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Brand "+id+" not found");
        }

    }

    @DeleteMapping("/deleteBrand/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBrand(@PathVariable Integer id){
        try {
            return ResponseEntity.ok().body(brandService.deleteB(id));
        }
        catch (NullPointerException e){
            return ResponseEntity.ofNullable("Brand "+id+" not found");
        }
    }
    @PostMapping(value = "/addBrands", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<?> saveB(@RequestBody List<BrandDTO> brandDTO){
        try {
            CompletableFuture<List<Brand>> savedBrands = brandService.saveB(mapper.btoModelList(brandDTO));
            List<BrandDTO> brandDTOS=new ArrayList<>();
            savedBrands.get().forEach(brand -> {brandDTOS.add(mapper.btoDTO(brand));});
            return CompletableFuture.completedFuture(brandDTOS);

        }
        catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError());
        }
    }

    @PutMapping("/updateBrand/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateB(@PathVariable Integer id, @RequestBody BrandDTO brandDTO){
        try {
            return ResponseEntity.ok().body(mapper.btoDTO(brandService.updateB(id, mapper.btoModel(brandDTO))));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Brand not found");
        }
    }

    @GetMapping(value = "/downloadCars")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> downloadCars()throws IOException{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachement","cars.csv");
        byte[] csvBytes = carService.carCsv().getBytes();

        return new ResponseEntity<>(csvBytes,headers, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadCsv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadCsv(@RequestParam(value = "file")MultipartFile file){
        if(file.isEmpty()){
            log.error("The file it's empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (file.getOriginalFilename().contains(".csv")){

            carService.uploadCsvCars(file);
            return ResponseEntity.ok("File successfully uploaded");
        }
        log.error("The file it's empty");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The file it's not CSV");
    }

}



