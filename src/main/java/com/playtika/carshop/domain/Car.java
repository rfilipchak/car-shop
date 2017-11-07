package com.playtika.carshop.domain;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Car {

    @NonNull
    private String type;

    @NonNull
    private int year;
}
