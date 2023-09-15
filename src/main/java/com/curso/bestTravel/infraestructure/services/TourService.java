package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.models.request.TourFlyRequest;
import com.curso.bestTravel.api.models.request.TourRequest;
import com.curso.bestTravel.domain.repository.CustomerRepository;
import com.curso.bestTravel.domain.repository.FlyRepository;
import com.curso.bestTravel.domain.repository.HotelRepository;
import com.curso.bestTravel.domain.repository.TourRepository;
import com.curso.bestTravel.infraestructure.abstract_services.ITourService;
import com.curso.bestTravel.infraestructure.helpers.TourHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;


    @Override
    public TourFlyRequest create(TourRequest tourRequest) {
        return null;
    }

    @Override
    public TourFlyRequest read(Long aLong) {
        return null;
    }

    @Override
    public TourFlyRequest update(TourRequest tourRequest, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(Long reservationId, Long tourId) {
        return null;
    }
}
