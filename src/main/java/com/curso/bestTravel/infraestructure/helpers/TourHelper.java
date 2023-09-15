package com.curso.bestTravel.infraestructure.helpers;

import com.curso.bestTravel.domain.repository.ReservationRepository;
import com.curso.bestTravel.domain.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
}
