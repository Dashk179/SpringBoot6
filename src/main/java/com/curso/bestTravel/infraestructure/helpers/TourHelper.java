package com.curso.bestTravel.infraestructure.helpers;

import com.curso.bestTravel.domain.entities.*;
import com.curso.bestTravel.domain.repository.ReservationRepository;
import com.curso.bestTravel.domain.repository.TicketRepository;
import com.curso.bestTravel.infraestructure.services.ReservationService;
import com.curso.bestTravel.infraestructure.services.TicketService;
import com.curso.bestTravel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer){
        var response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(fly ->{
            var ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.charger_price_percentage)))
                    .purchaseDate(LocalDate.now())
                    .arrivalDate(BestTravelUtil.getRandomSoon())
                    .departureDate(BestTravelUtil.getRandomLatter())
                    .build();
            response.add(this.ticketRepository.save(ticketToPersist));

        });
        return response;
    }

    public Set<ReservationEntity> createReservations(HashMap<HotelEntity,Integer> hotels, CustomerEntity customer){
        var response = new HashSet<ReservationEntity>(hotels.size());
       hotels.forEach((hotel,totalDays)->
       {
           var reservationToPersist = ReservationEntity.builder()
                   .id(UUID.randomUUID())
                   .hotel(hotel)
                   .customer(customer)
                   .totalDays(totalDays)
                   .dateTimeReservation(LocalDateTime.now())
                   .dateStart(LocalDate.now())
                   .dateEnd(LocalDate.now().plusDays(totalDays))
                   .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.charger_price_percentage)))
                   .build();
           response.add(this.reservationRepository.save(reservationToPersist));

       });
        return response;
    }

    public TicketEntity  createTicket(FlyEntity fly, CustomerEntity customer){
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.charger_price_percentage)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomSoon())
                .departureDate(BestTravelUtil.getRandomLatter())
                .build();
        return this.ticketRepository.save(ticketToPersist);
    }

}
