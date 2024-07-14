package com.mafer.car.registry.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTOResponse {
    private Integer id;
    //private Integer brand_id;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fueltype;
    private Integer numdoors;
    private BrandDTO brand;
}
