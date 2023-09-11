package com.curso.bestTravel.infraestructure.abstract_services;

import com.curso.bestTravel.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse>{

    Set<FlyResponse> readByOriginDestiny(String origen,String destiny);
}
