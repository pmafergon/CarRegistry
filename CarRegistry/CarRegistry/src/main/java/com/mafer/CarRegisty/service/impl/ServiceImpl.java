package com.mafer.CarRegisty.service.impl;

import com.mafer.CarRegisty.repository.BRepository;
import com.mafer.CarRegisty.repository.CRepository;
import com.mafer.CarRegisty.repository.entity.BrandEntity;
import com.mafer.CarRegisty.repository.entity.CarEntity;
import com.mafer.CarRegisty.service.BrandService;
import com.mafer.CarRegisty.service.domain.Brand;
import com.mafer.CarRegisty.service.mapper.EntityMapper;
import com.mafer.CarRegisty.service.CarService;
import com.mafer.CarRegisty.service.domain.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
public class ServiceImpl implements CarService, BrandService {

    @Autowired
    private CRepository cRepository;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BRepository bRepository;

    //Implementaciones de CarService
    @Override
    @Async
    public CompletableFuture<List<Car>> findAll() throws NoSuchElementException {

        long startTime = System.currentTimeMillis(); //Hora que empieza a ejecutarse el findAll
        List<CarEntity> carEntityList = cRepository.findAll();
        if (carEntityList.isEmpty()) {
                throw new NoSuchElementException("No cars found");
        } else {
            List<BrandEntity> brandEntities = bRepository.findAll();

            for (CarEntity carEntity : carEntityList) {
                BrandEntity brand = brandEntities.stream().filter(i -> i.
                            getId()
                            .equals(carEntity.getBrand().getId())).findFirst().orElseThrow();
                    carEntity.setBrand(brand);
                }
                long endTime = System.currentTimeMillis();
                log.info("Total time: " + (endTime - startTime));

                return CompletableFuture.completedFuture(entityMapper.toServiceList(carEntityList));
            }
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> save(List<Car> car)throws Exception{
        List<CarEntity> carEntitytoReturn = new ArrayList<CarEntity>();

        try {
           long startTime = System.currentTimeMillis();
           for (Car carBrand: car){
              Optional<BrandEntity> brandOfCar = bRepository.findByName(carBrand.getBrandname()); //Sacar la consulta a una base de datos a una lista para reducir consultas
              if (brandOfCar.isPresent()) {
                  CarEntity carEntity = entityMapper.toRepository(carBrand);
                  carEntity.setBrand(brandOfCar.get());
                  cRepository.save(carEntity);
                  carEntitytoReturn.add(carEntity);
              }else{
                   log.warn(carBrand.getId()+" No added due to missing brand");
               }
           }
           long endTime = System.currentTimeMillis();
           log.info("Total time "+ (endTime-startTime));

           return CompletableFuture.completedFuture(entityMapper.toServiceList(carEntitytoReturn));

        }catch (Exception e){
            log.error("Error occurred while saving cars" + e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public String delete(Integer id)throws NullPointerException{
        Optional<CarEntity> foundToDelete = cRepository.findById(id);
        if (foundToDelete.isPresent()){
            cRepository.deleteById(id);
            return "Car " + id + " removed.";
        }else {
            throw new NullPointerException();
        }
    }

    public Car update(Integer id,Car car) throws Exception{

           long startTime= System.currentTimeMillis();
           Optional<CarEntity> findingCar = cRepository.findById(id);
           Optional<BrandEntity> findingBrand = bRepository.findByName(car.getBrandname());

              if(findingCar.isPresent() && findingBrand.isPresent()){
                 CarEntity carEntity = findingCar.get();
                 carEntity.setBrand(findingBrand.get());
                 CarEntity savedCar=cRepository.save(carEntity);
                 long endTime = System.currentTimeMillis();
                 log.info("Total time: " + (endTime - startTime));

                 return entityMapper.toService(savedCar);
              }else {
                  throw new Exception();
              }
    }



    public Car getById(Integer id)throws NullPointerException{

       long startTime=System.currentTimeMillis();
       Optional<CarEntity> findingCar = cRepository.findById(id);
       Optional<BrandEntity>findingBrand = bRepository.findById(findingCar.get().getBrand().getId());

       if (findingCar.isPresent() && findingBrand.isPresent()) {
          CarEntity carEntity = findingCar.get();
          carEntity.setBrand(findingBrand.get());
          long endTime = System.currentTimeMillis();
          log.info("Total time: " + (endTime - startTime));
          return entityMapper.toService(carEntity);
       }else {
           throw new NullPointerException();
       }
    }



    //Implementaciones de BrandService
    @Override
    @Async
    public CompletableFuture<List<Brand>> findAllB()throws NullPointerException{
        long startTime = System.currentTimeMillis();

           List<BrandEntity> bfounds = bRepository.findAll();

            if (bfounds.isEmpty()){
                throw new NullPointerException();
            }else {

                long endTime = System.currentTimeMillis();
                log.info("Total time: " + (endTime-startTime));

                return CompletableFuture.completedFuture(entityMapper.btoServiceList(bfounds));
            }

    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<Brand>> saveB(List<Brand> brands)throws Exception{
        try {
            List<BrandEntity> entitiesSaved = new ArrayList<BrandEntity>();

              long startTime=System.currentTimeMillis();
              for(Brand brand : brands){
                  Optional<BrandEntity> checkingBrand = bRepository.findByName(brand.getName()); //Sacar la consulta a una base de datos a una lista para reducir consultas
                  if (checkingBrand.isPresent()){
                      log.warn(checkingBrand.get().getName()+" brand already added");

                  }else{
                      BrandEntity savedBrand=bRepository.save(entityMapper.btoRepository(brand));
                      entitiesSaved.add(savedBrand);
                  }
              }
              long endTime=System.currentTimeMillis();
              log.info("Total time: "+ (endTime-startTime));

              return CompletableFuture.completedFuture(entityMapper.btoServiceList(entitiesSaved));


        }catch (Exception e){
            log.error("Error occurred while saving brands", e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    public String deleteB(Integer id)throws NullPointerException{
        Optional<BrandEntity> foundToDelete = bRepository.findById(id);
        if (foundToDelete.isPresent()) {
            bRepository.deleteById(id);
            return "Brand " + id + " removed.";
        }else {
            throw new NullPointerException();
        }
    }


    public Brand updateB(Integer id, Brand brand) throws Exception {


       long startTime = System.currentTimeMillis();
       Optional<BrandEntity> findingBrand = bRepository.findById(id);
       if (findingBrand.isPresent()) {
           brand.setId(id);
           BrandEntity brandEntity = bRepository.save(entityMapper.btoRepository(brand));
           long endTime=System.currentTimeMillis();
           log.info("Total time: "+(endTime-startTime));
           return entityMapper.btoService(brandEntity);
        } else {
           throw new Exception();
        }
    }





    public Brand getBrandById(Integer id) throws NullPointerException{

       long startTime = System.currentTimeMillis();
       Optional<BrandEntity> findingBrand = bRepository.findById(id);
       if (findingBrand.isPresent()) {
          return entityMapper.btoService(findingBrand.get());
       }else {
          throw new NullPointerException();
       }
    }
}



