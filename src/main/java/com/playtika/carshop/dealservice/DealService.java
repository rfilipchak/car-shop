package com.playtika.carshop.dealservice;

import com.playtika.carshop.dao.entity.DealEntity;
import com.playtika.carshop.dealstatus.DealStatus;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.Deal;

import java.util.Collection;
import java.util.Optional;

public interface DealService {

    long addDeal(CarSaleInfo carSaleInfo, String buyerContact, int price);

    Collection<Deal> getAllDeals();

    Collection<Deal> getAllDealsTheSameCar(long carSaleId);

    void acceptDeal(long carSaleId);

    void removeDealByCarShopId(long carSaleId);

    Optional<DealStatus> getDealStatusById(long carSaleId);

    Optional<Deal> getBestDeal(long carSaleId);


}
