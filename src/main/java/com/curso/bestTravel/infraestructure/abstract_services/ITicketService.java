package com.curso.bestTravel.infraestructure.abstract_services;

import com.curso.bestTravel.api.models.request.TicketRequest;
import com.curso.bestTravel.api.models.responses.TicketResponse;

import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

    
}
