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
            orphanRemoval = true,//Si alguno de estos objetos queda huerfano osea queda sin llave foranea y se elimina
            mappedBy = "tour"
    )
    private Set<TicketEntity> tickets;
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    @PrePersist
    @PreRemove
    public void updateFk(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }

    public void removeTicket(UUID id) {
        this.tickets.forEach(ticket -> {
            if (ticket.getId().equals(id)) {
                ticket.setTour(null);
            }
        });
    }

    public void addTicket(TicketEntity ticket ){
        if (Objects.isNull(this.tickets)) this.tickets= new  HashSet<>();
        this.tickets.add(ticket);
        this.tickets.forEach(t -> t.setTour(this));
    }

    public void removeReservation(UUID Id){
    this.reservations.forEach(reservation -> {
        if (reservation.getId().equals(Id)) {
        reservation.setTour((null));
        }
    });
    }

    public void addReservation(ReservationEntity reservation){
        if (Objects.isNull(this.reservations))this.reservations = new HashSet<>();
        this.reservations.add(reservation);
        this.reservations.forEach(r -> r.setTour(this));
    }  


}
