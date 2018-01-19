package com.playtika.carshop.domain;

import com.playtika.carshop.dealstatus.DealStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Deal {
    @NonNull
    private final long id;
    @NonNull
    private final CarSaleInfo carSaleInfo;
    @NonNull
    private final String buyerContact;
    @NonNull
    private final int buyerPrice;
    @NonNull
    private final DealStatus dealStatus;
}
