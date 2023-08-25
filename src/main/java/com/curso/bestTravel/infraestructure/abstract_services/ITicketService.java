package com.curso.bestTravel.infraestructure.abstract_services;

import com.curso.bestTravel.api.request.TicketRequest;
import com.curso.bestTravel.api.responses.TicketResponse;

import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

    
}
