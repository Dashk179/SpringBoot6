package com.curso.bestTravel.infraestructure.abstract_services;

import com.curso.bestTravel.api.models.request.TicketRequest;
import com.curso.bestTravel.api.models.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

    BigDecimal findPrice(Long flyId);

}
