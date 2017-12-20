package com.playtika.carshop.dao;

import com.playtika.carshop.dao.entity.PersonEntity;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class PersonDaoTest extends AbstractDaoTest<PersonDao> {

    @Test
    public void shouldReturnNullWhenPersonDoesNotExist() {
        Optional<PersonEntity> notExistingPerson = dao.getPersonEntityByContact("unknown");

        assertThat(notExistingPerson, is(Optional.empty()));
    }

    @Test
    public void shouldReturnPersonEntityByContact() {
        String contact = "newContact";
        long id = addPersonToPersonDb(contact);
        PersonEntity expectedPerson = new PersonEntity(contact);
        expectedPerson.setId(id);

        PersonEntity person = dao.getPersonEntityByContact(contact).get();

        assertThat(person, samePropertyValuesAs(expectedPerson));
    }

    private long addPersonToPersonDb(String contact) {
        PersonEntity person = new PersonEntity(contact);
        return dao.save(person).getId();
    }
}