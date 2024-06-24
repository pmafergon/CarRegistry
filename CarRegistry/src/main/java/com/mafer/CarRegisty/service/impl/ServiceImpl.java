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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Car> findAll() throws NullPointerException{
        List<CarEntity> carEntityList = cRepository.findAll();
        if (carEntityList.isEmpty()){
            throw new NullPointerException("No cars found");
        } else {
            for (CarEntity carEntity : carEntityList) {
                Optional<BrandEntity> brand = bRepository.findById(carEntity.getBrand().getId());
                carEntity.setBrand(brand.get());
            }
            return entityMapper.toServiceList(carEntityList);
        }
    }

    public Car save(Car car)throws Exception{

        Optional<BrandEntity> brandOfCar = bRepository.findById(car.getBrand_id());
        if (brandOfCar.isPresent()){
            CarEntity carEntity = entityMapper.toRepository(car);
            carEntity.setBrand(brandOfCar.get());
            cRepository.save(carEntity);
            List<CarEntity> lastCarAdded = cRepository.findLastCar();
            carEntity.setId(lastCarAdded.get(0).getId());
            return entityMapper.toService(carEntity);
        }else {
            throw new Exception();
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
    public Car update(Integer id,Car car)throws Exception{
        Optional<CarEntity> findingCar = cRepository.findById(id);
        Optional<BrandEntity>findingBrand=bRepository.findById(findingCar.get().getBrand().getId());
        if(findingCar.isPresent() && findingBrand.isPresent()){
            car.setId(id);
            CarEntity carEntity = entityMapper.toRepository(car);
            carEntity.setBrand(findingBrand.get());
            return entityMapper.toService(cRepository.save(carEntity));
        }else {
            throw new Exception();
        }
    }

    public Car getById(Integer id)throws NullPointerException{
        Optional<CarEntity> findingCar = cRepository.findById(id);
        Optional<BrandEntity>findingBrand = bRepository.findById(findingCar.get().getBrand().getId());

        if (findingCar.isPresent() && findingBrand.isPresent()) {
            CarEntity carEntity = findingCar.get();
            carEntity.setBrand(findingBrand.get());
            return entityMapper.toService(carEntity);
        }else {
            throw new NullPointerException();
        }
    }

    //Implementaciones de BrandService
    public List<Brand> findAllB()throws NullPointerException{
        List<BrandEntity> bfounds=bRepository.findAll();
        if (bfounds.isEmpty()){
            throw new NullPointerException();
        }else {
            return entityMapper.BtoServiceList(bfounds);
        }
    }

    public Brand saveB(Brand brand)throws Exception{

        List<BrandEntity> brandEntities=bRepository.findAll();
        List<String>brandNames=brandEntities.stream()
                    .map(BrandEntity::getName)
                    .collect(Collectors.toList());

        for (String bName:brandNames){
            if (brand.getName().equals(bName)){
                throw new Exception();
            }
        }
        BrandEntity brandEntity=bRepository.save(entityMapper.BtoRepository(brand));
        brandEntities=bRepository.findLastBrand();
        brandEntity.setId(brandEntities.get(0).getId());



        return entityMapper.BtoService(brandEntity);
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
    public Brand updateB(Integer id,Brand brand)throws Exception{
        Optional<BrandEntity> findingBrand = bRepository.findById(id);
        if(findingBrand.isPresent()){
            brand.setId(id);
            return entityMapper.BtoService(bRepository.save(entityMapper.BtoRepository(brand)));
        }else{
            throw new Exception();
        }
    }

    public Brand getBrandById(Integer id)throws NullPointerException{
        Optional<BrandEntity> findingBrand = bRepository.findById(id);
        if (findingBrand.isPresent()) {
            return entityMapper.BtoService(findingBrand.get());
        }else {
            throw new NullPointerException();
        }
    }
}


