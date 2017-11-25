package com.playtika.carshop.domain;

import lombok.*;

@Data
public class CarSaleInfo {
    @NonNull
    private final Long id;
    @NonNull
    private final Car car;
    @NonNull
    private final long price;
    @NonNull
    private final String contact;

}
