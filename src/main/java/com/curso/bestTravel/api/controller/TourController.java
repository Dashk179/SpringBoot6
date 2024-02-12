package com.curso.bestTravel.api.controller;

import com.curso.bestTravel.api.models.request.TourRequest;
import com.curso.bestTravel.api.models.responses.TourResponse;
import com.curso.bestTravel.infraestructure.abstract_services.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
@Tag(name = "Tour")

public class TourController{

    private final ITourService tourService;
@Operation(summary = "Save in system a tour based in list oof hotels and flights")
    @PostMapping
    public ResponseEntity<TourResponse> post(@RequestBody TourRequest request){
        System.out.println(tourService.getClass().getSimpleName());
       return ResponseEntity.ok(this.tourService.create(request));
     }

    @Operation(summary = "Return a Tour with od passed")
     @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id){
       return ResponseEntity.ok(this.tourService.read(id));
     }

    @Operation(summary = "Delete a Tour with ad passed")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
       this.tourService.delete(id);
       return ResponseEntity.noContent().build();
     }
     /*La diferencia entre el put y pacth
         Put: Actualizamos todo el objeto
         Path: Actualizamos unicamente una propiedad del objeto.
         En este caso unicamente estamos actualizando su lista de tours
      */
     @Operation(summary = "Remove a ticket from tour")
     @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId,@PathVariable UUID ticketId){
       this.tourService.removeTicket(tourId,ticketId);
       return ResponseEntity.noContent().build();
     }
    @Operation(summary = "Add a ticket from tour")
    @PatchMapping(path = "{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String,UUID>> postTicket(@PathVariable Long tourId, @PathVariable Long flyId){
       var response = Collections.singletonMap("ticketId",this.tourService.addTicket(tourId,flyId));
       return ResponseEntity.ok(response);
     }

    @Operation(summary = "Remove a reservation from tour")
     @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId,@PathVariable UUID reservationId){
         this.tourService.removeReservation(tourId,reservationId);
         return  ResponseEntity.noContent().build();
     }
    @Operation(summary = "Add a reservation from tour")
     @PatchMapping(path = "{tourId}/add_reservation/{hotelId}")
    public  ResponseEntity<Map<String,UUID>> postReservation(
            @PathVariable Long tourId,
            @PathVariable Long hotelId,
            @RequestParam Integer totalDays)
      {
        var response = Collections.singletonMap("ticketId",this.tourService.addReservation(tourId,hotelId,totalDays));
        return ResponseEntity.ok(response);
     }

}
