package com.playtika.carshop.dao;

import com.google.common.collect.ImmutableMap;
import com.playtika.carshop.dao.entity.PersonEntity;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class PersonDaoTest extends AbstractDaoTest<PersonDao> {

    @Test
    public void shouldReturnNullWhenPersonDoesNotExist() {
        assertThat(dao.getPersonEntityByContact("unknown"), nullValue());
    }

    @Test
    public void shouldReturnPersonEntityByContact() {
        String person = "contact";
        addPersonToPersonDb(person);
        PersonEntity personToCompare = new PersonEntity(person);

        assertThat(dao.getPersonEntityByContact(person).getContact(),
                samePropertyValuesAs(personToCompare.getContact()));
    }

    private long addPersonToPersonDb(String contact) {
        return addRecordToDb("person", ImmutableMap.of("contact", contact));
    }

}