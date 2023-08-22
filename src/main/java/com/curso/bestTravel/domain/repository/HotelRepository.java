package com.curso.bestTravel.domain.repository;

import com.curso.bestTravel.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;

public interface HotelRepository extends JpaRepository<HotelEntity,Long> {

    Set<HotelEntity> findByPriceLessThan(BigDecimal price);

    Set<HotelEntity> findByPriceBetween(BigDecimal min,BigDecimal max);

    Set<HotelEntity> findByRatingGreaterThan(Integer rating);


}
