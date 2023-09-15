package com.curso.bestTravel.infraestructure.abstract_services;

import com.curso.bestTravel.api.models.responses.HotelResponse;

import java.util.Set;
import java.util.UUID;

public interface IHotelService extends CatalogService<HotelResponse>{
    Set<HotelResponse> readGreaterThan(Integer rating);

    Set<HotelResponse> readReservationId(UUID id);
}
