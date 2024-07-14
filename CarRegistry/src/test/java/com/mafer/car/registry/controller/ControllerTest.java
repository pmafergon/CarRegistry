package com.mafer.car.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mafer.car.registry.controller.dto.BrandDTO;
import com.mafer.car.registry.controller.dto.CarDTO;
import com.mafer.car.registry.controller.dto.CarDTOResponse;
import com.mafer.car.registry.controller.mapper.DTOMapper;
import com.mafer.car.registry.service.BrandService;
import com.mafer.car.registry.service.CarService;
import com.mafer.car.registry.service.domain.Brand;
import com.mafer.car.registry.service.domain.Car;
import com.mafer.car.registry.service.impl.JwtService;
import com.mafer.car.registry.service.impl.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Controller.class)
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Controller controller;
    @MockBean
    private CarService carService;
    @MockBean
    private DTOMapper dtoMapper;
    @MockBean
    private BrandService brandService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void test_findAll() throws Exception {
        List<Car> carList = new ArrayList<>();
        List<CarDTOResponse> carDTOResponseList = new ArrayList<>();
        when(carService.findAll()).thenReturn(carList);
        when(dtoMapper.toDTOResponseList(carList)).thenReturn(carDTOResponseList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carDTOResponseList)));

    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void test_getById() throws Exception {
        Integer id = 1;
        Car car = new Car();
        CarDTOResponse carDTOResponse = new CarDTOResponse();
        when(carService.getById(id)).thenReturn(car);
        when(dtoMapper.toDTOResponse(car)).thenReturn(carDTOResponse);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getById/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void test_delete() throws Exception {
        Integer id = 1;
        String expectedMessage = "Car " + id + " removed.";
        when(carService.delete(id)).thenReturn(expectedMessage);
        this.mockMvc
                        .perform(MockMvcRequestBuilders.delete("/deleteCar/{id}", id)
                        .with(csrf())  // Añade CSRF token si es necesario
                        .contentType(MediaType.APPLICATION_JSON))  // Establece el tipo de contenido como JSON si es aplicable
                        .andExpect(status().isOk())  // Espera una respuesta HTTP 200 OK
                        .andExpect(content().string(expectedMessage));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void test_update() throws Exception {
        Integer id = 1;
        CarDTO carDTO = new CarDTO();
        Car car = new Car();
        CarDTOResponse carDTOResponse = new CarDTOResponse();
        when(dtoMapper.toModel(carDTO)).thenReturn(car);
        when(carService.update(id, car)).thenReturn(car);
        when(dtoMapper.toDTOResponse(car)).thenReturn(carDTOResponse);



        this.mockMvc.perform(MockMvcRequestBuilders.put("/updateCar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)  // Especifica el tipo de contenido como JSON
                        .content(objectMapper.writeValueAsString(carDTO))  // Convierte carDTO a JSON y establece como cuerpo de la solicitud
                        .with(csrf()))  // Añade CSRF token si es necesario
                .andExpect(status().isOk());




    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void test_save() throws Exception {
        List<CarDTO> carDTOList = new ArrayList<>();
        List<Car> carList = new ArrayList<>();


        CarDTO carDTO = CarDTO.builder().id(1).brandname("BrandTest").build();
        Car car = Car.builder().id(1).brandname("BrandTest").build();
        BrandDTO brandDTO = BrandDTO.builder().name("BrandTest").build();
        CarDTOResponse carDTOResponse = CarDTOResponse.builder().id(1).brand(brandDTO).build();

        carList.add(car);
        carDTOList.add(carDTO);


        when(dtoMapper.toModelList(carDTOList)).thenReturn(carList);
        when(carService.save(carList)).thenReturn(CompletableFuture.completedFuture(carList));
        when(dtoMapper.toDTOResponse(car)).thenReturn(carDTOResponse);



        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/addCars")
                        .contentType(MediaType.APPLICATION_JSON)  // Especifica el tipo de contenido como JSON
                        .with(csrf())  // Añade CSRF token si es necesario
                        .content(objectMapper.writeValueAsString(carDTOList)))  // Convierte carDTOList a JSON
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void test_findAllB() throws Exception {
        List<BrandDTO> brandDTOList = new ArrayList<>();
        List<Brand> brandList = new ArrayList<>();
        when(brandService.findAllB()).thenReturn(brandList);
        when(dtoMapper.btoDTOList(brandList)).thenReturn(brandDTOList);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/brands"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(brandDTOList)));

    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void test_getBrandById() throws Exception {
        Integer id = 1;
        Brand brand = new Brand();
        BrandDTO brandDTO = new BrandDTO();

        when(brandService.getBrandById(id)).thenReturn(brand);
        when(dtoMapper.btoDTO(brand)).thenReturn(brandDTO);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getBrandById/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void test_deleteBrand() throws Exception {
        Integer id = 1;
        String expectedMessage = "Brand " + id + " removed.";
        when(brandService.deleteB(id)).thenReturn(expectedMessage);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteBrand/{id}", id)
                        .with(csrf())  // Añade CSRF token si es necesario
                        .contentType(MediaType.APPLICATION_JSON))  // Establece el tipo de contenido como JSON si es aplicable
                        .andExpect(status().isOk())  // Espera una respuesta HTTP 200 OK
                        .andExpect(content().string(expectedMessage));




    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void test_saveB() throws Exception {
        List<BrandDTO> brandDTOList = new ArrayList<>();
        List<Brand>brandList=new ArrayList<>();


        BrandDTO brandDTO = BrandDTO.builder().name("BrandTest").build();
        Brand brand = Brand.builder().name("BrandTest").build();


        brandDTOList.add(brandDTO);
        brandList.add(brand);

        when(dtoMapper.btoModelList(brandDTOList)).thenReturn(brandList);
        when(brandService.saveB(brandList)).thenReturn((CompletableFuture.completedFuture(brandList)));
        when(dtoMapper.btoDTOList(brandList)).thenReturn(brandDTOList);

        this.mockMvc
                .perform(post("/addBrands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(brandDTOList)))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void test_updateB() throws Exception {
        Integer id = 1;
        BrandDTO brandDTO = new BrandDTO();
        Brand brand = new Brand();

        when(dtoMapper.btoModel(brandDTO)).thenReturn(brand);
        when(brandService.updateB(id, brand)).thenReturn(brand);
        when(dtoMapper.btoDTO(brand)).thenReturn(brandDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/updateBrand/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)  // Especifica el tipo de contenido como JSON
                        .content(objectMapper.writeValueAsString(brandDTO))  // Convierte carDTO a JSON y establece como cuerpo de la solicitud
                        .with(csrf()))  // Añade CSRF token si es necesario
                        .andExpect(status().isOk());

    }
}
