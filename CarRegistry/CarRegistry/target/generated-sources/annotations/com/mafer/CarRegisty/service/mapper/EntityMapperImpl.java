package com.mafer.CarRegisty.service.mapper;

import com.mafer.CarRegisty.repository.entity.BrandEntity;
import com.mafer.CarRegisty.repository.entity.CarEntity;
import com.mafer.CarRegisty.service.domain.Brand;
import com.mafer.CarRegisty.service.domain.Car;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-03T16:43:26+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public CarEntity toRepository(Car car) {
        if ( car == null ) {
            return null;
        }

        CarEntity carEntity = new CarEntity();

        carEntity.setId( car.getId() );
        carEntity.setModel( car.getModel() );
        carEntity.setMilleage( car.getMilleage() );
        carEntity.setPrice( car.getPrice() );
        carEntity.setYear( car.getYear() );
        carEntity.setDescription( car.getDescription() );
        carEntity.setColour( car.getColour() );
        carEntity.setFueltype( car.getFueltype() );
        carEntity.setNumdoors( car.getNumdoors() );
        carEntity.setBrand( btoRepository( car.getBrand() ) );

        return carEntity;
    }

    @Override
    public List<CarEntity> toRepositoryList(List<Car> car) {
        if ( car == null ) {
            return null;
        }

        List<CarEntity> list = new ArrayList<CarEntity>( car.size() );
        for ( Car car1 : car ) {
            list.add( toRepository( car1 ) );
        }

        return list;
    }

    @Override
    public Car toService(CarEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        Car.CarBuilder car = Car.builder();

        car.id( carEntity.getId() );
        car.model( carEntity.getModel() );
        car.milleage( carEntity.getMilleage() );
        car.price( carEntity.getPrice() );
        car.year( carEntity.getYear() );
        car.description( carEntity.getDescription() );
        car.colour( carEntity.getColour() );
        car.fueltype( carEntity.getFueltype() );
        car.numdoors( carEntity.getNumdoors() );
        car.brand( btoService( carEntity.getBrand() ) );

        return car.build();
    }

    @Override
    public List<Car> toServiceList(List<CarEntity> carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        List<Car> list = new ArrayList<Car>( carEntity.size() );
        for ( CarEntity carEntity1 : carEntity ) {
            list.add( toService( carEntity1 ) );
        }

        return list;
    }

    @Override
    public BrandEntity btoRepository(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setId( brand.getId() );
        brandEntity.setName( brand.getName() );
        brandEntity.setWarranty( brand.getWarranty() );
        brandEntity.setCountry( brand.getCountry() );

        return brandEntity;
    }

    @Override
    public Brand btoService(BrandEntity brandEntity) {
        if ( brandEntity == null ) {
            return null;
        }

        Brand.BrandBuilder brand = Brand.builder();

        brand.id( brandEntity.getId() );
        brand.name( brandEntity.getName() );
        brand.warranty( brandEntity.getWarranty() );
        brand.country( brandEntity.getCountry() );

        return brand.build();
    }

    @Override
    public List<BrandEntity> btoRepositoryList(List<Brand> brand) {
        if ( brand == null ) {
            return null;
        }

        List<BrandEntity> list = new ArrayList<BrandEntity>( brand.size() );
        for ( Brand brand1 : brand ) {
            list.add( btoRepository( brand1 ) );
        }

        return list;
    }

    @Override
    public List<Brand> btoServiceList(List<BrandEntity> brandEntity) {
        if ( brandEntity == null ) {
            return null;
        }

        List<Brand> list = new ArrayList<Brand>( brandEntity.size() );
        for ( BrandEntity brandEntity1 : brandEntity ) {
            list.add( btoService( brandEntity1 ) );
        }

        return list;
    }
}
