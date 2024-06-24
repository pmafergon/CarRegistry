package com.mafer.CarRegisty.controller.mapper;

import com.mafer.CarRegisty.controller.dto.BrandDTO;
import com.mafer.CarRegisty.controller.dto.CarDTO;
import com.mafer.CarRegisty.controller.dto.CarDTOResponse;
import com.mafer.CarRegisty.service.domain.Brand;
import com.mafer.CarRegisty.service.domain.Car;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DTOMapper {

    Car toModel (CarDTO carDTO);
    List<Car> toModelList (List<CarDTO> carDTO);
    CarDTO toDTO(Car car);

    List<CarDTO> toDTOList(List<Car> car);

    Brand BtoModel(BrandDTO brandDTO);
    BrandDTO BtoDTO (Brand brand);
    List<Brand> BtoModelList(List<BrandDTO> brandDTO);
    List<BrandDTO> BtoDTOList (List<Brand> brand);

    CarDTOResponse toDTOResponse (Car car);
    List<CarDTOResponse> toDTOResponseList (List<Car> car);
}
