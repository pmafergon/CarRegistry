package com.mafer.CarRegisty.service.mapper;

import com.mafer.CarRegisty.repository.entity.BrandEntity;
import com.mafer.CarRegisty.repository.entity.CarEntity;
import com.mafer.CarRegisty.service.domain.Brand;
import com.mafer.CarRegisty.service.domain.Car;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring")
public interface EntityMapper {


    CarEntity toRepository (Car car);
    List<CarEntity> toRepositoryList (List<Car> car);
    Car toService(CarEntity carEntity);
    List<Car> toServiceList(List<CarEntity> carEntity);

    BrandEntity BtoRepository (Brand brand);
    Brand BtoService(BrandEntity brandEntity);
    List<BrandEntity> BtoRepositoryList (List<Brand> brand);
    List<Brand> BtoServiceList (List<BrandEntity> brandEntity);

}
