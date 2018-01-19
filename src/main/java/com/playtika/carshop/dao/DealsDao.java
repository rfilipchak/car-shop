package com.playtika.carshop.dao;

import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.DealEntity;
import com.playtika.carshop.dealstatus.DealStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DealsDao extends JpaRepository<DealEntity,Long> {
    DealEntity getFirstByCarShopEntityId(long id);
    DealEntity deleteDealEntitiesByCarShopEntity(long id);
    DealEntity getFirstByCarShopEntityIdOrderByBuyerPriceDesc(long id);
    Collection<DealEntity> getAllByCarShopEntityIdAndDealStatus(long id, DealStatus dealStatus);
    Collection<DealEntity> getAllByCarShopEntityId (long id);
    DealEntity getByCarShopEntityId (long id);
    DealEntity deleteAllByCarShopEntityId (long id);
    //Collection<DealEntity> getAllByCarShopEntity (CarShopEntity)
}
