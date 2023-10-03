package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.models.request.TourRequest;
import com.curso.bestTravel.api.models.responses.TourResponse;
import com.curso.bestTravel.domain.entities.*;
import com.curso.bestTravel.domain.repository.CustomerRepository;
import com.curso.bestTravel.domain.repository.FlyRepository;
import com.curso.bestTravel.domain.repository.HotelRepository;
import com.curso.bestTravel.domain.repository.TourRepository;
import com.curso.bestTravel.infraestructure.abstract_services.ITourService;
import com.curso.bestTravel.infraestructure.helpers.TourHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public TourResponse create(TourRequest request) {

        var costumer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlyEntity>();

        request.getFlights().forEach(fly -> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow()));

        var hotels = new HashMap<HotelEntity,Integer>();

        request.getHotels().forEach(hotel -> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(),hotel.getTotalDays()));

        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights,costumer))
                .reservations(this.tourHelper.createReservations(hotels,costumer))
                .customer(costumer)
                .build();

        var tourSaved = this.tourRepository.save(tourToSave);

        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations()
                        .stream()
                        .map(ReservationEntity::getId)
                        .collect(Collectors.toSet()))

                .ticketIds(tourSaved.getTickets()
                        .stream()
                        .map(TicketEntity::getId)
                        .collect(Collectors.toSet()))

                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDb = this.tourRepository.findById(id).orElseThrow();

        return TourResponse.builder()
                .reservationIds(tourFromDb.getReservations()
                        .stream()
                        .map(ReservationEntity::getId)
                        .collect(Collectors.toSet()))

                .ticketIds(tourFromDb.getTickets()
                        .stream()
                        .map(TicketEntity::getId)
                        .collect(Collectors.toSet()))

                .id(tourFromDb.getId())
                .build();

    }

    @Override
    public TourResponse update(TourRequest tourRequest, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id).orElseThrow();
        this.tourRepository.delete(tourToDelete);

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
