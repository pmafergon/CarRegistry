package com.mafer.CarRegisty.service.mapper;

import com.mafer.CarRegisty.repository.entity.BrandEntity;
import com.mafer.CarRegisty.repository.entity.CarEntity;
import com.mafer.CarRegisty.service.domain.Brand;
import com.mafer.CarRegisty.service.domain.Car;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.concurrent.CompletableFuture;


@Mapper(componentModel = "spring")
public interface EntityMapper {


    CarEntity toRepository (Car car);
    List<CarEntity> toRepositoryList (List<Car> car);
    Car toService(CarEntity carEntity);
    List<Car> toServiceList(List<CarEntity> carEntity);

    BrandEntity btoRepository(Brand brand);
    Brand btoService(BrandEntity brandEntity);
    List<BrandEntity> btoRepositoryList(List<Brand> brand);
    List<Brand> btoServiceList(List<BrandEntity> brandEntity);


}
