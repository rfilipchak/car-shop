package com.playtika.carshop.repository;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public long addCarDb(Car car, long price, String contact) {
        String sqlGetCarId = "SELECT id FROM cars WHERE brand =?";

        jdbcTemplate.update("INSERT INTO cars(brand, car_year) VALUES (?,?)", car.getBrand(), car.getYear());
        long carId = jdbcTemplate.queryForObject(sqlGetCarId, new Object[]{car.getBrand()}, Long.class);
        jdbcTemplate.update("INSERT INTO car_deallers(price, contact, car_id) VALUES (?, ?, ?)", price, contact, carId);
        String carSaleIdSql = "SELECT id FROM car_deallers WHERE price = ? AND car_id = ?";
        return jdbcTemplate.queryForObject(carSaleIdSql, new Object[]{price, carId}, Long.class);
    }

    @Override
    public Collection<CarSaleInfo> getCarsDb() {
        String sql = "SELECT cd.id, c.brand, c.car_year, cd.price, cd.contact " +
                "FROM car_deallers cd INNER JOIN cars c ON cd.car_id = c.id";

        List<CarSaleInfo> cars = new ArrayList<CarSaleInfo>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            CarSaleInfo carSaleInfo = new CarSaleInfo(
                    ((Integer) row.get("id")).longValue(),
                    (new Car((String) row.get("brand"), (int) row.get("car_year"))),
                    ((Integer) row.get("price")).longValue(),
                    (String)row.get("contact"));
            cars.add(carSaleInfo);
        }
        return cars;
    }

    @Override
    public Optional<CarSaleInfo> getCarDb(Long id) {
        String sql = "SELECT cd.id, c.brand, c.car_year, cd.price, cd.contact " +
                "FROM car_deallers cd INNER JOIN cars c ON cd.car_id = c.id WHERE cd.id=?";

        Optional<CarSaleInfo> result = jdbcTemplate.queryForObject(
                sql,new Object[]{id},
                (ResultSet rs, int rowNum) -> {
                    return Optional.ofNullable(new CarSaleInfo(rs.getLong("id"),
                            new Car(rs.getString("brand"), rs.getInt("car_year")),
                            rs.getLong("price"),
                            rs.getString("contact")));
                });
        return result;


    }

    @Override
    public boolean removeCarDb(Long id) {

        String getCarIdSql = "SELECT car_id FROM car_deallers  WHERE id =?";
        String delCarDeallerSql = "DELETE FROM car_deallers WHERE id =?";
        String delCarSql = "DELETE FROM cars WHERE id =?";

        long carId = jdbcTemplate.queryForObject(getCarIdSql,new Object[]{id},Long.class);
        jdbcTemplate.update(delCarDeallerSql,new Object[]{id});
        jdbcTemplate.update(delCarSql,new Object[]{id});
        return false;
    }

}
