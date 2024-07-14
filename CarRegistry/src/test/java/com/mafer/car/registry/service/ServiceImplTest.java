package com.mafer.car.registry.service;

import com.mafer.car.registry.repository.BrandRepository;
import com.mafer.car.registry.repository.CarRepository;
import com.mafer.car.registry.repository.entity.BrandEntity;
import com.mafer.car.registry.repository.entity.CarEntity;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.domain.Car;
import com.mafer.car.registry.service.impl.ServiceImpl;
import com.mafer.car.registry.service.mapper.EntityMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class ServiceImplTest {

    @InjectMocks
    private ServiceImpl service;

    @Mock
    private CarRepository carRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private EntityMapper entityMapper;

    @Test
    void test_deleteB(){
        Integer id = 1;
        BrandEntity brandEntity = new BrandEntity();

        when(brandRepository.findById(id)).thenReturn(Optional.of(brandEntity));
        doNothing().when(brandRepository).deleteById(id);
        String result = service.deleteB(id);

        assertEquals("Brand "+id+" removed.", result);
    }
    @Test
    void test_updateB() throws Exception {
        Integer id = 1;
        BrandEntity brandEntity=new BrandEntity();
        Brand brand = new Brand();
        when(brandRepository.findById(id)).thenReturn(Optional.of(brandEntity));
        when(entityMapper.btoRepository(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(entityMapper.btoService(brandEntity)).thenReturn(brand);

        Object result = service.updateB(id,brand);

        assertSame(brand, result);
    }
    @Test
    void test_getBrandById(){
        Integer id = 1;
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand();
        when(brandRepository.findById(id)).thenReturn(Optional.of(brandEntity));
        when(entityMapper.btoService(brandEntity)).thenReturn(brand);
        Object result=service.getBrandById(id);
        assertSame(brand, result);

    }
    @Test
    void test_findAllB(){
        List<Brand> brandList = new ArrayList<>();
        List<BrandEntity> brandEntityList=new ArrayList<>();
        brandEntityList.add(new BrandEntity());
        when(brandRepository.findAll()).thenReturn(brandEntityList);
        when(entityMapper.btoServiceList(brandEntityList)).thenReturn(brandList);
        Object result=service.findAllB();

        assertSame(result,brandList);

    }
    @Test
    void test_saveB() throws Exception {
        //creamos una lista para los retornos
        List<Brand> brandList = new ArrayList<>();

        Brand brand1=new Brand(); //añadimos dos marcas a cada lista
        brand1.setName("BrandTest");
        Brand brand2=new Brand();
        brand2.setName("BrandTest2");

        brandList.add(brand1);
        brandList.add(brand2);

        BrandEntity brandEntity1=new BrandEntity();
        brandEntity1.setName("BrandTest");
        BrandEntity brandEntity2=new BrandEntity();
        brandEntity2.setName("BrandTest2");


        //Creamos una lista vacía para las marcas guardadas

        List<BrandEntity> allBrands=new ArrayList<>();

        //Creamos los when para que devuelvan el objeto correcto sin guardarlo en el repositorio
        when(brandRepository.findAll()).thenReturn(allBrands);
        when(entityMapper.btoRepository(brand1)).thenReturn(brandEntity1);
        when(entityMapper.btoRepository(brand2)).thenReturn(brandEntity2);
        when(brandRepository.save(brandEntity1)).thenReturn(brandEntity1);
        when(brandRepository.save(brandEntity2)).thenReturn(brandEntity2);
        when(entityMapper.btoServiceList(anyList())).thenReturn(brandList);
        //Llamamos a la función con en el futuro
        CompletableFuture<List<Brand>> result = service.saveB(brandList);
        //Nos aseguramos que lo obtenido del result y la lista es la misma(se ha guardado correctamente)
        assertEquals(result.get(),brandList);
        assertNotNull(result);
    }

    @Test
    void test_findAll(){

        List<CarEntity> carEntityList= new ArrayList<>();
        List<BrandEntity> brandEntityList=new ArrayList<>();

        BrandEntity brandEntity=BrandEntity.builder()
                .name("BrandTest")
                .id(1)
                .build();
        CarEntity carEntity= CarEntity.builder()
                .id(1)
                .model("model")
                .colour("colour")
                .brand(brandEntity)
                .build();

        carEntityList.add(carEntity);
        brandEntityList.add(brandEntity);

        when(carRepository.findAll()).thenReturn(carEntityList);
        when(brandRepository.findAll()).thenReturn(brandEntityList);

        List<Car> result = service.findAll();

        assertEquals(result,entityMapper.toServiceList(carEntityList));


    }
    @Test
    void test_save() throws Exception {

        List<BrandEntity> allBrands = new ArrayList<>();
        List<Car> carList=new ArrayList<>();

        BrandEntity brandEntity= BrandEntity.builder()
                .id(1)
                .name("BrandTest")
                .build();

        allBrands.add(brandEntity);

        Brand brand= Brand.builder()
                .id(1)
                .name("BrandTest")
                .build();

        Car car = Car.builder()
                .id(1)
                .brandname("BrandTest")
                .brand(brand)
                .build();

        carList.add(car);

        CarEntity carEntity = CarEntity.builder()
                .id(1)
                .brand(brandEntity)
                .build();

        when(brandRepository.findAll()).thenReturn(allBrands);
        when(entityMapper.toRepository(car)).thenReturn(carEntity);
        when(carRepository.save(carEntity)).thenReturn(carEntity);
        when(entityMapper.toServiceList(anyList())).thenReturn(carList);

        CompletableFuture<List<Car>> result = service.save(carList);

        assertNotNull(result);
        assertEquals(result.get(),carList);
    }
    @Test
    void test_delete(){
        Integer id = 1;
        CarEntity carEntity = new CarEntity();

        when(carRepository.findById(id)).thenReturn(Optional.of(carEntity));
        doNothing().when(carRepository).deleteById(id);

        String result = service.delete(id);
        assertEquals("Car "+id+" removed.",result);
    }
    @Test
    void test_update(){
        Integer id = 1;
        CarEntity carEntity = new CarEntity();
        Car car = new Car();
        BrandEntity brandEntity = new BrandEntity();
        when(carRepository.findById(id)).thenReturn(Optional.of(carEntity));
        when(brandRepository.findByName(car.getBrandname())).thenReturn(Optional.of(brandEntity));
        when(entityMapper.toRepository(car)).thenReturn(carEntity);
        when(carRepository.save(carEntity)).thenReturn(carEntity);
        when(entityMapper.toService(carEntity)).thenReturn(car);

        Car result= service.update(id,car);

        assertNotNull(result);
        assertEquals(result,car);
    }
    @Test
    void test_getById() {

        Integer id = 1;
        BrandEntity brandEntity = BrandEntity.builder().name("BrandTest").id(id).build();
        CarEntity carEntity = CarEntity.builder().id(id).model("ModelTest").brand(brandEntity).build();

        Car car = new Car();
        when(carRepository.findById(id)).thenReturn(Optional.of(carEntity));
        when(brandRepository.findById(carEntity.getId())).thenReturn(Optional.of(brandEntity));
        when(entityMapper.toService(carEntity)).thenReturn(car);

        Car result = service.getById(id);
        assertNotNull(result);
        assertEquals(result,car);
    }

}
