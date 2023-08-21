package com.curso.bestTravel.domain.repository;

import com.curso.bestTravel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface FlyRepository extends JpaRepository<FlyEntity,Long> {




    @Query("select f from fly f where f.price < :price")//select * from fly where price < 20.00;
    Set<FlyEntity> selectLessPrice(BigDecimal price); //Lista de datos

    @Query("select f from fly f where f.price between :min and :max")//select * from fly where price between 10 and 15;
    Set<FlyEntity> selectBetweenPrice(BigDecimal min,BigDecimal max); //Lista de datos

    @Query("select f from fly f where f.origin_name = :origin and f.destiny_name = :destiny")//select * from fly where origin_name = 'Grecia' and destiny_name = 'Mexico';
    Set<FlyEntity> selectOriginDestiny(String origin,String destiny); //Lista de datos

    @Query("select f from fly f join fetch f.tickets t where t.id =:id")//select  * from fly join ticket t on fly.id = t.fly_id where fly.id =?;
    Optional<FlyEntity> findByTicketId(UUID id);
}
