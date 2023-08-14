package com.curso.bestTravel.domain.repository;

import com.curso.bestTravel.domain.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity,String> {

}
