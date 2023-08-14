package com.curso.bestTravel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.io.Serializable;

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

}
