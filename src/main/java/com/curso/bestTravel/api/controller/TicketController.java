package com.curso.bestTravel.api.controller;

import com.curso.bestTravel.api.request.TicketRequest;
import com.curso.bestTravel.api.responses.TicketResponse;
import com.curso.bestTravel.infraestructure.abstract_services.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor

public class TicketController {

    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

    @GetMapping(path = "{id}")
    public  ResponseEntity<TicketResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));

    }
    @PutMapping(path = "{id}")
    public  ResponseEntity<TicketResponse> put(@PathVariable UUID id,@RequestBody TicketRequest request){
        return ResponseEntity.ok(this.ticketService.update(request, id));
    }

    @DeleteMapping(path = "{id}")
    public  ResponseEntity<Void> delete(@PathVariable UUID id){
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();

    }

}
