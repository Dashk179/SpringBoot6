package com.curso.bestTravel.api.controller;

import com.curso.bestTravel.api.models.request.ReservationRequest;
import com.curso.bestTravel.api.models.request.TicketRequest;
import com.curso.bestTravel.api.models.responses.ErrorsResponse;
import com.curso.bestTravel.api.models.responses.ReservationResponse;
import com.curso.bestTravel.api.models.responses.TicketResponse;
import com.curso.bestTravel.infraestructure.abstract_services.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(path = "reservation")
@AllArgsConstructor
@Tag(name = "Reservation")

public class ReservationController {
    private final IReservationService reservationService;

    @ApiResponse(
            responseCode = "400",
            description = "When the request have a field invalid we response this",
    content = {
    @Content(mediaType = "aplication/json",schema = @Schema(implementation = ErrorsResponse.class))
    }
    )
    @Operation(summary = "Save in system un reservation with the fly passed in parameter")
    @PostMapping
    public ResponseEntity<ReservationResponse> post(@Valid  @RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.create(request));
    }

    @GetMapping(path = "{id}")
    public  ResponseEntity<ReservationResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.read(id));

    }
    @PutMapping(path = "{id}")
    public  ResponseEntity<ReservationResponse> put(@Valid @PathVariable UUID id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(this.reservationService.update(request, id));
    }

    @DeleteMapping(path = "{id}")
    public  ResponseEntity<Void> delete(@PathVariable UUID id){
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();

    }

@Operation(summary = "Return a reservation price given ")
    @GetMapping
    public  ResponseEntity<Map<String, BigDecimal>> getReservationPrice(
            @RequestParam Long hotelId,
            @RequestHeader(required = false) Currency currency){
        if (Objects.isNull(currency)) currency = Currency.getInstance("USD");
        return ResponseEntity.ok(Collections.singletonMap("hotelPrice",this.reservationService.findPrice(hotelId,currency)));

    }
}
