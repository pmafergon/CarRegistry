package com.mafer.car.registry.service.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private Integer id;
    private String brandname;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fueltype;
    private Integer numdoors;

    private Brand brand;


}
