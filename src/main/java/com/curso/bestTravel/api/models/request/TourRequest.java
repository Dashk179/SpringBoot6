package com.curso.bestTravel.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest implements Serializable {

    public Serializable customerId;
    public Set<TourFlyRequest> fligths;
    public Set<TourHotelRequest> hotels;
}
