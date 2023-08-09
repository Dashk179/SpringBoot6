package com.curso.bestTravel.domain.repository;

import com.curso.bestTravel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlyRepository extends JpaRepository<FlyEntity,Long> {

}
