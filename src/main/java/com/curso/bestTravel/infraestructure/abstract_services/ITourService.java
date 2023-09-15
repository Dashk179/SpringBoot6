package com.curso.bestTravel.infraestructure.abstract_services;

import com.curso.bestTravel.api.models.request.TourFlyRequest;
import com.curso.bestTravel.api.models.request.TourRequest;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourFlyRequest,Long>{

    void removeTicket(UUID ticketId,Long tourId);
    UUID addTicket(Long flyId,Long tourId);

    void removeReservation(UUID reservationId,Long tourId);
    UUID addReservation(Long reservationId,Long tourId);

}