package com.mafer.car.registry.service.mapper;

import com.mafer.car.registry.repository.entity.BrandEntity;
import com.mafer.car.registry.repository.entity.CarEntity;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.domain.Car;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T15:52:21+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public CarEntity toRepository(Car car) {
        if ( car == null ) {
            return null;
        }

        CarEntity.CarEntityBuilder carEntity = CarEntity.builder();

        carEntity.id( car.getId() );
        carEntity.model( car.getModel() );
        carEntity.milleage( car.getMilleage() );
        carEntity.price( car.getPrice() );
        carEntity.year( car.getYear() );
        carEntity.description( car.getDescription() );
        carEntity.colour( car.getColour() );
        carEntity.fueltype( car.getFueltype() );
        carEntity.numdoors( car.getNumdoors() );
        carEntity.brand( btoRepository( car.getBrand() ) );

        return carEntity.build();
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

        BrandEntity.BrandEntityBuilder brandEntity = BrandEntity.builder();

        brandEntity.id( brand.getId() );
        brandEntity.name( brand.getName() );
        brandEntity.warranty( brand.getWarranty() );
        brandEntity.country( brand.getCountry() );

        return brandEntity.build();
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
