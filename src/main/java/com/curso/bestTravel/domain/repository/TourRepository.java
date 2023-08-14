package com.curso.bestTravel.domain.repository;

import com.curso.bestTravel.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity,Long> {

}
