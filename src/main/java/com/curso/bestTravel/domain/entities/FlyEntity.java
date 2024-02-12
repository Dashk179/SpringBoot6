package com.curso.bestTravel.domain.entities;

import com.curso.bestTravel.util.enums.AeroLine;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name="fly")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    @Column(length = 20)
    private String origin_name;
    @Column(length = 20)
    private String destiny_name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,//
            fetch = FetchType.EAGER,//El eager hace el join y carga todo el objeto
            orphanRemoval = true,//Si alguno de estos objetos queda huerfano osea queda sin llave foranea se elimina
            mappedBy = "fly"
    )
    private Set<TicketEntity> tickets;
    }
