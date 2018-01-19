package com.playtika.carshop.dealservice;

import com.playtika.carshop.carshopservice.CarServiceImpl;
import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.CarDao;
import com.playtika.carshop.dao.CarShopDao;
import com.playtika.carshop.dao.DealsDao;
import com.playtika.carshop.dao.PersonDao;
import com.playtika.carshop.dao.entity.DealEntity;
import com.playtika.carshop.dealstatus.DealStatus;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class DealServiceImp implements DealService {

    private final CarServiceImpl carShopService;
    private final Converter converter;
    private final CarDao carDao;
    private final CarShopDao carShopDao;
    private final PersonDao personDao;
    private final DealsDao dealsDao;

    @Autowired
    public DealServiceImp(CarServiceImpl carShopService, Converter converter, CarDao carDao, CarShopDao carShopDao, PersonDao personDao, DealsDao dealsDao) {
        this.carShopService = carShopService;
        this.converter = converter;
        this.carDao = carDao;
        this.carShopDao = carShopDao;
        this.personDao = personDao;
        this.dealsDao = dealsDao;
    }

    @Override
    public long addDeal(CarSaleInfo carSaleInfo, String buyerContact, int price) {
        DealEntity dealEntity = new DealEntity(converter.domainToCarShopEntity(carSaleInfo),
                carShopService.checkPersonForExist(buyerContact), price);
        return dealsDao.save(dealEntity).getId();
    }

    @Override
    public Collection<Deal> getAllDeals() {
        return converter.DealEntitiesToDealsList(dealsDao.findAll());
    }

    @Override
    public Collection<Deal> getAllDealsTheSameCar(long carId) {
        Collection<DealEntity> dealEntities = dealsDao.getAllByCarShopEntityId(carId);
        return converter.DealEntitiesToDealsList(dealEntities);
    }

    //?????????????????/
    @Override
    public void acceptDeal(long dealId) {
        dealsDao.findOne(dealId).setDealStatus(DealStatus.ACCEPTED);
        rejectAllWithCarShoId(dealsDao.findOne(dealId).getCarShopEntity().getId());
    }

    @Override
    public void removeDealByCarShopId(long carSaleId) {
        if (dealsDao.exists(dealsDao.getFirstByCarShopEntityId(carSaleId).getId())) {
            dealsDao.deleteAllByCarShopEntityId(carSaleId);
        }
    }

    @Override
    public Optional<DealStatus> getDealStatusById(long dealId) {
        if (dealsDao.exists(dealId)) {
            return Optional.of(dealsDao.findOne(dealId).getDealStatus());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Deal> getBestDeal(long carSaleId) {
        DealEntity dealEntity = dealsDao.getFirstByCarShopEntityIdOrderByBuyerPriceDesc(carSaleId);
        return Optional.of(converter.dealEntityToDeal(dealEntity));
    }


    private void rejectAllWithCarShoId(long carSaleId) {
        Collection<DealEntity> dealEntities;
        dealEntities = dealsDao.getAllByCarShopEntityIdAndDealStatus(carSaleId, DealStatus.ACTIVE);
        for (DealEntity deal : dealEntities) {
            deal.setDealStatus(DealStatus.REJECTED);
        }
    }
}
