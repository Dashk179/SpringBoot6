package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.models.responses.FlyResponse;
import com.curso.bestTravel.domain.entities.FlyEntity;
import com.curso.bestTravel.domain.repository.FlyRepository;
import com.curso.bestTravel.infraestructure.abstract_services.IFlyService;
import com.curso.bestTravel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {
    private final FlyRepository flyRepository;
    @Override
    public Page<FlyResponse> realAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page,size);
            case LOWER -> pageRequest = PageRequest.of(page,size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);//Transformamos todas las  entidades en respuestas
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

    private FlyResponse entityToResponse(FlyEntity entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity,response);
        return response;
    }
}
