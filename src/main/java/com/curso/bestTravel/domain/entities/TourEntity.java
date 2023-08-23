package com.curso.bestTravel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "tour")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,//
            fetch = FetchType.EAGER,//El eager hace el join y carga todo el objeto
            orphanRemoval = true,//Si alguno de estos objetos queda huerfano osea queda sin llave foranea se elimina
            mappedBy = "tour"
    )
    private Set<ReservationEntity> reservations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,//
            fetch = FetchType.EAGER,//El eager hace el join y carga todo el objeto
            orphanRemoval = true,//Si alguno de estos objetos queda huerfano osea queda sin llave foranea se elimina
            mappedBy = "tour"
    )
    private Set<TicketEntity> tickets;
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;


    //Mapeando las relaciones inversas para tickets
    public void addTicket(TicketEntity ticket){
        if ((Objects.isNull(this.tickets)))this.tickets = new HashSet<>();
        this.tickets.add(ticket);
    }

    public void removeTicket(UUID id){
        if ((Objects.isNull(this.tickets)))this.tickets = new HashSet<>();
        this.tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    public void updateTicket(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

    //Mapeando relaciones inversas para reservaciones
    public void addReservation(ReservationEntity reservation){
        if (Objects.isNull(this.reservations))this.reservations = new HashSet<>();
        this.reservations.add(reservation);
    }
    public void removeReservations(UUID idReservation){
        if (Objects.isNull(this.reservations))this.reservations = new HashSet<>();
        this.reservations.removeIf(r-> r.getId().equals(idReservation));
    }

    public void updateReservations(){
        this.reservations.forEach(r -> r.setTour(this));
    }
}
