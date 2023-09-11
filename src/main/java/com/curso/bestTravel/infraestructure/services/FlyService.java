package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.models.responses.FlyResponse;
import com.curso.bestTravel.infraestructure.abstract_services.IFlyService;
import com.curso.bestTravel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {
    @Override
    public Page<FlyResponse> realAll(Integer page, Integer size, SortType sortType) {
        return null;
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return null;
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return null;
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origen, String destiny) {
        return null;
    }
}
