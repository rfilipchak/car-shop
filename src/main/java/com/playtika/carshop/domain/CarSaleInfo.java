package com.playtika.carshop.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class CarSaleInfo {
    @NonNull
    private final long id;
    @NonNull
    private final Car car;
    @NonNull
    private final int price;
    @NonNull
    private final String contact;
}
