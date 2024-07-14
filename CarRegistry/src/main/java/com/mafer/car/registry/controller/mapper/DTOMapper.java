package com.mafer.car.registry.controller.mapper;

import com.mafer.car.registry.controller.dto.BrandDTO;
import com.mafer.car.registry.controller.dto.CarDTO;
import com.mafer.car.registry.controller.dto.CarDTOResponse;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.domain.Car;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface DTOMapper {

    Car toModel (CarDTO carDTO);
    List<Car> toModelList (List<CarDTO> carDTO);
    CarDTO toDTO(Car car);

    List<CarDTO> toDTOList(List<Car> car);

    Brand btoModel(BrandDTO brandDTO);
    BrandDTO btoDTO(Brand brand);
    List<Brand> btoModelList(List<BrandDTO> brandDTO);
    List<BrandDTO> btoDTOList (List<Brand> brands);

    CarDTOResponse toDTOResponse (Car car);
    List<CarDTOResponse> toDTOResponseList (List<Car> car);
}
