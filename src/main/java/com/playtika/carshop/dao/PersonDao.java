package com.playtika.carshop.dao;

import com.playtika.carshop.dao.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonDao extends JpaRepository<PersonEntity, Long>{
    Optional<PersonEntity> getPersonEntityByContact(String contact);
}
