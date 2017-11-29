package com.playtika.carshop.dao.repository;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collection;

@Repository
@AllArgsConstructor
public class CarRepJpaImpl implements CarRepJpa {
    @PersistenceContext
    private EntityManager em;

    @Override
    public long addCarSaleInfo(CarEntity c, PersonEntity p, Long price) {
        CarShopEntity carShopEntity = new CarShopEntity();
        carShopEntity.setCar(checkCarForExist(c));
        carShopEntity.setPerson(checkPersonForExsist(p));
        carShopEntity.setPrice(price);
        em.persist(carShopEntity);
        return carShopEntity.getId();
    }

    @Override
    public Collection<CarSaleInfo> getCars() {
        Query query = em.createQuery("SELECT cs FROM cars_shop cs");
        return (Collection<CarSaleInfo>) query.getResultList();
    }

    @Override
    public CarShopEntity getCar(Long id) {
        return em.find(CarShopEntity.class, id);
    }

    @Override
    public boolean removeCar(Long id) {
        if (getCar(id) != null) {
            em.remove(getCar(id));
            return true;
        }
        return false;
    }

    @Override
    public Collection<Car> getAllCars() {
        Query query = em.createQuery("SELECT c FROM cars c");
        return (Collection<Car>) query.getResultList();
    }

    private CarEntity checkCarForExist(CarEntity c) {
        String registr = c.getRegistration();
        TypedQuery<Long> query = em.createQuery(
                "SELECT id FROM cars WHERE registration = :registr", Long.class);
        query.setParameter("registr", registr);
        try {
            Long id = query.getSingleResult();
            return em.find(CarEntity.class, id);
        } catch (NoResultException e) {
            return c;
        }
    }
    private PersonEntity checkPersonForExsist(PersonEntity p) {
        String contact = p.getContact();
        TypedQuery<Long> query = em.createQuery(
                "SELECT id FROM person WHERE contact = :contact", Long.class);
        query.setParameter("contact", contact);
        try {
            Long id = query.getSingleResult();
            return em.find(PersonEntity.class, id);
        } catch (NoResultException e) {
            return p;
        }
    }
}
