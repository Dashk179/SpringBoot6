package com.curso.bestTravel.infraestructure.abstract_services;

import com.curso.bestTravel.api.models.request.ReservationRequest;
import com.curso.bestTravel.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest,ReservationResponse, UUID>{
    BigDecimal findPrice(Long hotelId, Currency currency);
}
