package com.mafer.car.registry.service.impl;

import com.mafer.car.registry.repository.BrandRepository;
import com.mafer.car.registry.repository.CarRepository;
import com.mafer.car.registry.repository.entity.BrandEntity;
import com.mafer.car.registry.repository.entity.CarEntity;
import com.mafer.car.registry.service.BrandService;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.mapper.EntityMapper;
import com.mafer.car.registry.service.CarService;
import com.mafer.car.registry.service.domain.Car;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceImpl implements CarService, BrandService {


    private final CarRepository carRepository;
    private final EntityMapper entityMapper;
    private final BrandRepository brandRepository;
    private final String[] HEADERS = {"id","brand","model","milleage","price","year","description","colour","fueltype","numdoors"};

    //Implementations of CarService

    public List<Car> findAll() throws NoSuchElementException {

        long startTime = System.currentTimeMillis(); //Hora que empieza a ejecutarse el findAll
        List<CarEntity> carEntityList = carRepository.findAll();
        if (carEntityList.isEmpty()) {
                throw new NoSuchElementException("No cars found");
        } else {
                long endTime = System.currentTimeMillis();
                String msg = "Total time: " + (endTime - startTime);
                log.info(msg);

                return entityMapper.toServiceList(carEntityList);
            }
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> save(List<Car> cars) {
        List<CarEntity> carEntityToReturn = new ArrayList<>();
        List<BrandEntity> allBrands = brandRepository.findAll();
        Map<String, BrandEntity> brandMap = allBrands.stream()
                .collect(Collectors.toMap(BrandEntity::getName, brand -> brand));

        try {
           long startTime = System.currentTimeMillis();
           cars.forEach(car-> {
               BrandEntity brandOfCar = brandMap.get(car.getBrandname());


               if (brandOfCar != null) {
                   CarEntity carEntity = entityMapper.toRepository(car);
                   carEntity.setBrand(brandOfCar);
                   carEntity = carRepository.save(carEntity);
                   carEntityToReturn.add(carEntity);
               } else {
                   log.warn(car.getId() + " No added due to missing brand");
               }
           });

           long endTime = System.currentTimeMillis();
           String msg = "Total time: " + (endTime - startTime);
           log.info(msg);

           return CompletableFuture.completedFuture(entityMapper.toServiceList(carEntityToReturn));

        }catch (Exception e){
            log.error("Error occurred while saving cars" + e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public String delete(Integer id)throws NullPointerException{
        Optional<CarEntity> foundToDelete = carRepository.findById(id);
        if (foundToDelete.isPresent()){
            carRepository.deleteById(id);
            return "Car " + id + " removed.";
        }else {
            throw new NullPointerException();
        }
    }

    public Car update(Integer id,Car car) throws NullPointerException{

           long startTime= System.currentTimeMillis();
           Optional<CarEntity> findingCar = carRepository.findById(id);
           Optional<BrandEntity> findingBrand = brandRepository.findByName(car.getBrandname());


              if(findingCar.isPresent() && findingBrand.isPresent()){
                 CarEntity carEntity = entityMapper.toRepository(car);
                 carEntity.setBrand(findingBrand.get());
                 carEntity.setId(id);
                 CarEntity savedCar= carRepository.save(carEntity);
                 long endTime = System.currentTimeMillis();
                 String msg = "Total time: " + (endTime - startTime);
                 log.info(msg);

                 return entityMapper.toService(savedCar);
              }else {
                  throw new NullPointerException();
              }
    }



    public Car getById(Integer id)throws NullPointerException{

       long startTime=System.currentTimeMillis();
       Optional<CarEntity> findingCar = carRepository.findById(id);
       Optional<BrandEntity>findingBrand = brandRepository.findById(findingCar.get().getBrand().getId());

       if (findingCar.isPresent() && findingBrand.isPresent()) {
          CarEntity carEntity = findingCar.get();
          carEntity.setBrand(findingBrand.get());
          long endTime = System.currentTimeMillis();
          String msg = "Total time: " + (endTime - startTime);
          log.info(msg);
          return entityMapper.toService(carEntity);
       }else {
           throw new NullPointerException();
       }
    }



    //Implementations of BrandService
    @Override
    public List<Brand> findAllB()throws NullPointerException{
        long startTime = System.currentTimeMillis();

           List<BrandEntity> brandList = brandRepository.findAll();

            if (brandList.isEmpty()){
                throw new NullPointerException();
            }else {

                long endTime = System.currentTimeMillis();
                String msg = "Total time: " + (endTime - startTime);
                log.info(msg);

                return entityMapper.btoServiceList(brandList);
            }

    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<Brand>> saveB(List<Brand> brands)throws Exception{
        try {
            List<BrandEntity> entitiesSaved = new ArrayList<>();
            List<BrandEntity> allBrands = brandRepository.findAll();
              long startTime=System.currentTimeMillis();
              brands.forEach(brand->{
                  boolean checkingBrand = allBrands.stream().anyMatch(newBrand -> newBrand.getName().equals(brand.getName()));
                  if (checkingBrand){
                      log.warn(brand.getName()+" brand already added");

                  }else{
                      BrandEntity savedBrand= brandRepository.save(entityMapper.btoRepository(brand));
                      entitiesSaved.add(savedBrand);
                  }
              });
              long endTime=System.currentTimeMillis();
              String msg = "Total time: " + (endTime - startTime);
              log.info(msg);

              return CompletableFuture.completedFuture(entityMapper.btoServiceList(entitiesSaved));


        }catch (Exception e){
            log.error("Error occurred while saving brands", e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    public String deleteB(Integer id)throws NullPointerException{
        Optional<BrandEntity> foundToDelete = brandRepository.findById(id);
        if (foundToDelete.isPresent()) {
            brandRepository.deleteById(id);
            return "Brand " + id + " removed.";
        }else {
            throw new NullPointerException();
        }
    }


    public Brand updateB(Integer id, Brand brand) throws Exception {


       long startTime = System.currentTimeMillis();
       Optional<BrandEntity> findingBrand = brandRepository.findById(id);
       if (findingBrand.isPresent()) {
           brand.setId(id);
           BrandEntity brandEntity = brandRepository.save(entityMapper.btoRepository(brand));
           long endTime=System.currentTimeMillis();
           String msg = "Total time: " + (endTime - startTime);
           log.info(msg);
           return entityMapper.btoService(brandEntity);
        } else {
           throw new Exception();
        }
    }





    public Brand getBrandById(Integer id) throws NullPointerException{

       Optional<BrandEntity> findingBrand = brandRepository.findById(id);
       if (findingBrand.isPresent()) {
          return entityMapper.btoService(findingBrand.get());
       }else {
          throw new NullPointerException();
       }
    }

    public String carCsv(){
        List<CarEntity> carEntityList = carRepository.findAll();
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(Arrays.toString(HEADERS)).append("\n");

        for(CarEntity car : carEntityList){
            csvContent.append(car.getId()).append(", ")
                    .append(car.getBrand().getName()).append(", ")
                    .append(car.getModel()).append(", ")
                    .append(car.getMilleage()).append(", ")
                    .append(car.getPrice()).append(", ")
                    .append(car.getYear()).append(", ")
                    .append(car.getDescription()).append(", ")
                    .append(car.getColour()).append(", ")
                    .append(car.getFueltype()).append(", ")
                    .append(car.getNumdoors()).append("\n");
        }
        return csvContent.toString();
    }

    @Override
    public List<Car> uploadCsvCars(MultipartFile file) {
        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){

            List<CarEntity> carEntityList = new ArrayList<>();
            Iterable<CSVRecord> csvRecord = csvParser.getRecords();

            List<BrandEntity> brandEntities=brandRepository.findAll();
            Map<String, BrandEntity> brandMap = brandEntities.stream()
                    .collect(Collectors.toMap(BrandEntity::getName, brand -> brand));


            for (CSVRecord record:csvRecord){
                CarEntity carEntity = CarEntity.builder()
                        .model(record.get("model"))
                        .milleage(Integer.valueOf(record.get("milleage")))
                        .price(Double.valueOf(record.get("price")))
                        .year(Integer.valueOf(record.get("year")))
                        .description(record.get("description"))
                        .colour(record.get("colour"))
                        .fueltype(record.get("fueltype"))
                        .numdoors(Integer.valueOf(record.get("numdoors")))
                        .build();

                BrandEntity brandOfCar = brandMap.get(record.get("brand"));
                carEntity.setBrand(brandOfCar);

                carEntityList.add(carEntity);
            }
            carRepository.saveAll(carEntityList);
            return entityMapper.toServiceList(carEntityList);


        } catch (Exception e) {


            log.error("Failed to load cars.");
            throw new RuntimeException(e);

    }
    }
}



