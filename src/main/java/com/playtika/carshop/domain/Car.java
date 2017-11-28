package com.playtika.carshop.domain;

import lombok.Data;
import lombok.NonNull;


@Data
public class Car {

    @NonNull
    private final String brand;
    @NonNull
    private final int year;
}
