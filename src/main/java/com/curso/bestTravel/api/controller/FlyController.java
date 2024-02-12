package com.curso.bestTravel.api.controller;

import com.curso.bestTravel.api.models.responses.FlyResponse;
import com.curso.bestTravel.infraestructure.abstract_services.IFlyService;
import com.curso.bestTravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("fly")
@AllArgsConstructor
public class FlyController {

    private final IFlyService flyService;

    @GetMapping//Esta anotacion es importante por que aqui exponemos nuestro servicio
    public ResponseEntity<Page<FlyResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false)SortType sortType)
    {
        if (Objects.isNull(sortType)) sortType= SortType.NONE;//Si no contiene ningun header regresa un none
        var response = this.flyService.realAll(page,size,sortType);
        return response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response); //Esto es un if else
    }

    @GetMapping(path="less_price")//Esta anotacion es importante por que aqui exponemos nuestro servicio
    public ResponseEntity<Set<FlyResponse>> getLessPrice(
            @RequestParam BigDecimal price)
    {
        var response = this.flyService.readLessPrice(price);
        return response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response); //Esto es un if else
    }

    @GetMapping(path="between_price")//Esta anotacion es importante por que aqui exponemos nuestro servicio
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max)
    {
        var response = this.flyService.readBetweenPrice(min,max);
        return response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response); //Esto es un if else
    }

    @GetMapping(path="origin_destiny")//Esta anotacion es importante por que aqui exponemos nuestro servicio
    public ResponseEntity<Set<FlyResponse>> getByOriginDestiny(
            @RequestParam String origin,
            @RequestParam String destiny)
    {
        var response = this.flyService.readByOriginDestiny(origin,destiny);
        return response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response); //Esto es un if else
    }
}
