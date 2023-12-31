package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.models.request.ReservationRequest;
import com.curso.bestTravel.api.models.responses.FlyResponse;
import com.curso.bestTravel.api.models.responses.HotelResponse;
import com.curso.bestTravel.api.models.responses.ReservationResponse;
import com.curso.bestTravel.api.models.responses.TicketResponse;
import com.curso.bestTravel.domain.entities.ReservationEntity;
import com.curso.bestTravel.domain.entities.TicketEntity;
import com.curso.bestTravel.domain.repository.CustomerRepository;
import com.curso.bestTravel.domain.repository.HotelRepository;
import com.curso.bestTravel.domain.repository.ReservationRepository;
import com.curso.bestTravel.infraestructure.abstract_services.IReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor

public class ReservationService implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationResponse create(ReservationRequest reservationRequest) {
        var hotel = this.hotelRepository.findById(reservationRequest.getIdHotel()).orElseThrow();
        var customer = customerRepository.findById(reservationRequest.getIdClient()).orElseThrow();

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(reservationRequest.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(reservationRequest.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)))
                .build();

        var reservationPersisted = reservationRepository.save(reservationToPersist);
        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = this.reservationRepository.findById(id).orElseThrow();
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest reservationRequest, UUID id) {
        var hotel = this.hotelRepository.findById(reservationRequest.getIdHotel()).orElseThrow();

        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow();

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(reservationRequest.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(reservationRequest.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)));

        var reservationUpdate =this.reservationRepository.save(reservationToUpdate);

        log.info("Reservation updated with id {}",reservationUpdate.getId() );

        return this.entityToResponse(reservationUpdate);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = reservationRepository.findById(id).orElseThrow();
        this.reservationRepository.delete(reservationToDelete);

    }

    private ReservationResponse entityToResponse(ReservationEntity entity){
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity,response);

        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(),hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }

    public BigDecimal findPrice(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage));

    }

    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.20);

}
