package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.request.TicketRequest;
import com.curso.bestTravel.api.responses.FlyResponse;
import com.curso.bestTravel.api.responses.TicketResponse;
import com.curso.bestTravel.domain.entities.TicketEntity;
import com.curso.bestTravel.domain.repository.CustomerRepository;
import com.curso.bestTravel.domain.repository.FlyRepository;
import com.curso.bestTravel.domain.repository.TicketRepository;
import com.curso.bestTravel.infraestructure.abstract_services.ITicketService;
import com.fasterxml.jackson.databind.util.BeanUtil;
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
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    @Override
    public TicketResponse create(TicketRequest ticketRequest) {
        var fly= flyRepository.findById(ticketRequest.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(ticketRequest.getIdClient()).orElseThrow();

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().multiply(BigDecimal.valueOf(0.25)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(LocalDateTime.now())
                .departureDate(LocalDateTime.now())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);

        log.info("Ticket saved with id {}" , ticketPersisted.getId());

        return this.enittyToResponse(ticketToPersist);
    }

    @Override
    public TicketResponse read(UUID id) {
        var ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
        return this.enittyToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest ticketRequest, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow();
        var fly = flyRepository.findById(ticketRequest.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(BigDecimal.valueOf(0.25));
        ticketToUpdate.setArrivalDate(LocalDateTime.now());
        ticketToUpdate.setDepartureDate(LocalDateTime.now());

        var ticketUpdate = this.ticketRepository.save(ticketToUpdate);
        log.info("ticket updated with id {}",ticketUpdate.getId() );
        return this.enittyToResponse(ticketUpdate);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
    }

    private TicketResponse enittyToResponse(TicketEntity entity){
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity,response);

        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(),flyResponse);
        response.setFly(flyResponse);
        return response;
    }
}
