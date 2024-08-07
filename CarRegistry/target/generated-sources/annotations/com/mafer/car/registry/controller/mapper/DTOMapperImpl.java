package com.mafer.car.registry.controller.mapper;

import com.mafer.car.registry.controller.dto.BrandDTO;
import com.mafer.car.registry.controller.dto.CarDTO;
import com.mafer.car.registry.controller.dto.CarDTOResponse;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.domain.Car;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-16T17:57:12+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class DTOMapperImpl implements DTOMapper {

    @Override
    public Car toModel(CarDTO carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        Car.CarBuilder car = Car.builder();

        car.id( carDTO.getId() );
        car.brandname( carDTO.getBrandname() );
        car.model( carDTO.getModel() );
        car.milleage( carDTO.getMilleage() );
        car.price( carDTO.getPrice() );
        car.year( carDTO.getYear() );
        car.description( carDTO.getDescription() );
        car.colour( carDTO.getColour() );
        car.fueltype( carDTO.getFueltype() );
        car.numdoors( carDTO.getNumdoors() );

        return car.build();
    }

    @Override
    public List<Car> toModelList(List<CarDTO> carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        List<Car> list = new ArrayList<Car>( carDTO.size() );
        for ( CarDTO carDTO1 : carDTO ) {
            list.add( toModel( carDTO1 ) );
        }

        return list;
    }

    @Override
    public CarDTO toDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTO.CarDTOBuilder carDTO = CarDTO.builder();

        carDTO.id( car.getId() );
        carDTO.brandname( car.getBrandname() );
        carDTO.model( car.getModel() );
        carDTO.milleage( car.getMilleage() );
        carDTO.price( car.getPrice() );
        carDTO.year( car.getYear() );
        carDTO.description( car.getDescription() );
        carDTO.colour( car.getColour() );
        carDTO.fueltype( car.getFueltype() );
        carDTO.numdoors( car.getNumdoors() );

        return carDTO.build();
    }

    @Override
    public List<CarDTO> toDTOList(List<Car> car) {
        if ( car == null ) {
            return null;
        }

        List<CarDTO> list = new ArrayList<CarDTO>( car.size() );
        for ( Car car1 : car ) {
            list.add( toDTO( car1 ) );
        }

        return list;
    }

    @Override
    public Brand btoModel(BrandDTO brandDTO) {
        if ( brandDTO == null ) {
            return null;
        }

        Brand.BrandBuilder brand = Brand.builder();

        brand.id( brandDTO.getId() );
        brand.name( brandDTO.getName() );
        brand.warranty( brandDTO.getWarranty() );
        brand.country( brandDTO.getCountry() );

        return brand.build();
    }

    @Override
    public BrandDTO btoDTO(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandDTO.BrandDTOBuilder brandDTO = BrandDTO.builder();

        brandDTO.id( brand.getId() );
        brandDTO.name( brand.getName() );
        brandDTO.warranty( brand.getWarranty() );
        brandDTO.country( brand.getCountry() );

        return brandDTO.build();
    }

    @Override
    public List<Brand> btoModelList(List<BrandDTO> brandDTO) {
        if ( brandDTO == null ) {
            return null;
        }

        List<Brand> list = new ArrayList<Brand>( brandDTO.size() );
        for ( BrandDTO brandDTO1 : brandDTO ) {
            list.add( btoModel( brandDTO1 ) );
        }

        return list;
    }

    @Override
    public List<BrandDTO> btoDTOList(List<Brand> brands) {
        if ( brands == null ) {
            return null;
        }

        List<BrandDTO> list = new ArrayList<BrandDTO>( brands.size() );
        for ( Brand brand : brands ) {
            list.add( btoDTO( brand ) );
        }

        return list;
    }

    @Override
    public CarDTOResponse toDTOResponse(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTOResponse.CarDTOResponseBuilder carDTOResponse = CarDTOResponse.builder();

        carDTOResponse.id( car.getId() );
        carDTOResponse.model( car.getModel() );
        carDTOResponse.milleage( car.getMilleage() );
        carDTOResponse.price( car.getPrice() );
        carDTOResponse.year( car.getYear() );
        carDTOResponse.description( car.getDescription() );
        carDTOResponse.colour( car.getColour() );
        carDTOResponse.fueltype( car.getFueltype() );
        carDTOResponse.numdoors( car.getNumdoors() );
        carDTOResponse.brand( btoDTO( car.getBrand() ) );

        return carDTOResponse.build();
    }

    @Override
    public List<CarDTOResponse> toDTOResponseList(List<Car> car) {
        if ( car == null ) {
            return null;
        }

        List<CarDTOResponse> list = new ArrayList<CarDTOResponse>( car.size() );
        for ( Car car1 : car ) {
            list.add( toDTOResponse( car1 ) );
        }

        return list;
    }
}
