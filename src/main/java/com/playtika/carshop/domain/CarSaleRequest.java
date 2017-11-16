package com.playtika.carshop.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class CarSaleRequest {
    @NonNull
    private final Car car;
    @NonNull
    private final long price;
    @NonNull
    private final String contact;
}
