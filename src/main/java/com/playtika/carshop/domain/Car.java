package com.playtika.carshop.domain;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data // - to Data
public class Car {
    @NonNull
    private final String brand;
    @NonNull
    private final int year;
    @NonNull
    private final String registration;
    @NotNull
    private final String color;
}
