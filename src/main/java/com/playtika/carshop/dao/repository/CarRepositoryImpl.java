package com.playtika.carshop.dao.repository;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collection;

@Repository
@AllArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long addCarSaleInfo(CarEntity car, PersonEntity person, int price) {
        CarShopEntity carShopEntity = new CarShopEntity();
        carShopEntity.setCar(checkCarForExist(car));
        carShopEntity.setPerson(checkPersonForExsist(person));
        carShopEntity.setPrice(price);
        em.persist(carShopEntity);
        return carShopEntity.getId();
    }

    @Override
    public Collection<CarShopEntity> getCars() {
        Query query = em.createQuery("SELECT cs FROM CarShopEntity cs");
        return (Collection<CarShopEntity>) query.getResultList();
    }

    @Override
    public CarShopEntity getCar(long id) {
        return em.find(CarShopEntity.class, id);
    }

    @Override
    public boolean removeCar(long id) {
        if (getCar(id) != null) {
            em.remove(getCar(id));
            return true;
        }
        return false;
    }

    private CarEntity checkCarForExist(CarEntity car) {
        String registration = car.getRegistration();
        TypedQuery<Long> query = em.createQuery(
                "SELECT id FROM CarEntity WHERE registration = :registration", Long.class);
        query.setParameter("registration", registration);
        try {
            Long id = query.getSingleResult();
            return em.find(CarEntity.class, id);
        } catch (NoResultException e) {
            return car;
        }
    }

    private PersonEntity checkPersonForExsist(PersonEntity person) {
        String contact = person.getContact();
        TypedQuery<Long> query = em.createQuery(
                "SELECT id FROM PersonEntity WHERE contact = :contact", Long.class);
        query.setParameter("contact", contact);
        try {
            Long id = query.getSingleResult();
            return em.find(PersonEntity.class, id);
        } catch (NoResultException e) {
            return person;
        }
    }
}
