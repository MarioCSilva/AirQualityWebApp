package tqs.airquality.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.airquality.model.City;

import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}