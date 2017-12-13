package com.playtika.carshop.domain;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
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
