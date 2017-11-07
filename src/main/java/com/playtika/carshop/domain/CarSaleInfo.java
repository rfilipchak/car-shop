package com.playtika.carshop.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CarSaleInfo {

    private Car car;
    @NonNull
    private long price;
    @NonNull
    private String contact;
}
