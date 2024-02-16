package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.models.request.TicketRequest;
import com.curso.bestTravel.api.models.responses.FlyResponse;
import com.curso.bestTravel.api.models.responses.TicketResponse;
import com.curso.bestTravel.domain.entities.TicketEntity;
import com.curso.bestTravel.domain.repository.CustomerRepository;
import com.curso.bestTravel.domain.repository.FlyRepository;
import com.curso.bestTravel.domain.repository.TicketRepository;
import com.curso.bestTravel.infraestructure.abstract_services.ITicketService;
import com.curso.bestTravel.infraestructure.helpers.BlackListHelper;
import com.curso.bestTravel.infraestructure.helpers.CustomerHelper;
import com.curso.bestTravel.infraestructure.helpers.EmailHelper;
import com.curso.bestTravel.util.BestTravelUtil;
import com.curso.bestTravel.util.enums.Tables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;
    private BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;
    @Override
    public TicketResponse create(TicketRequest ticketRequest) {
        blackListHelper.isInBlackListCutomer(ticketRequest.getIdClient());
        var fly= flyRepository.findById(ticketRequest.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(ticketRequest.getIdClient()).orElseThrow();

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomSoon())
                .departureDate(BestTravelUtil.getRandomLatter())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);

        customerHelper.incrase(customer.getDni(), TicketService.class);
        if (Objects.nonNull(ticketRequest.getEmail()))this.emailHelper.sendMail(ticketRequest.getEmail(),customer.getFullName(), Tables.ticket.name());
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
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)));
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomLatter());

        var ticketUpdate = this.ticketRepository.save(ticketToUpdate);
        log.info("ticket updated with id {}",ticketUpdate.getId() );
        return this.enittyToResponse(ticketUpdate);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findPrice(Long flyId) {
        var fly = this.flyRepository.findById(flyId).orElseThrow();
       return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));

    }

    //Los metodos privados siempre van al final
    private TicketResponse enittyToResponse(TicketEntity entity){
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity,response);

        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(),flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);


}
