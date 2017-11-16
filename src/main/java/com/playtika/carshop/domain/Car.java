package com.playtika.carshop.domain;

import lombok.*;

@Data
public class Car {

    @NonNull
    private final String type;
    @NonNull
    private final int year;
}
