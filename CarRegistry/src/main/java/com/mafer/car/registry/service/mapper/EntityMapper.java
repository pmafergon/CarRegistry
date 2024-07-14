package com.mafer.car.registry.service.mapper;

import com.mafer.car.registry.repository.entity.BrandEntity;
import com.mafer.car.registry.repository.entity.CarEntity;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.domain.Car;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
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
