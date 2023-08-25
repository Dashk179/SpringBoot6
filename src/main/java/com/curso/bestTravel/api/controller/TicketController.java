package com.curso.bestTravel.api.controller;

import com.curso.bestTravel.api.request.TicketRequest;
import com.curso.bestTravel.api.responses.TicketResponse;
import com.curso.bestTravel.infraestructure.abstract_services.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor

public class TicketController {

    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

}
