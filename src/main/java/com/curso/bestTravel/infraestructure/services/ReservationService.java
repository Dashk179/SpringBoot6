package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.api.models.request.ReservationRequest;
import com.curso.bestTravel.api.models.responses.FlyResponse;
import com.curso.bestTravel.api.models.responses.HotelResponse;
import com.curso.bestTravel.api.models.responses.ReservationResponse;
import com.curso.bestTravel.api.models.responses.TicketResponse;
import com.curso.bestTravel.domain.entities.ReservationEntity;
import com.curso.bestTravel.domain.entities.TicketEntity;
import com.curso.bestTravel.domain.repository.CustomerRepository;
import com.curso.bestTravel.domain.repository.HotelRepository;
import com.curso.bestTravel.domain.repository.ReservationRepository;
import com.curso.bestTravel.infraestructure.abstract_services.IReservationService;
import com.curso.bestTravel.infraestructure.helpers.ApiCurrencyConnectorHelper;
import com.curso.bestTravel.infraestructure.helpers.BlackListHelper;
import com.curso.bestTravel.infraestructure.helpers.CustomerHelper;
import com.curso.bestTravel.infraestructure.helpers.EmailHelper;
import com.curso.bestTravel.util.enums.Tables;
import com.curso.bestTravel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor

public class ReservationService implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;
    final private BlackListHelper blackListHelper;
    private final ApiCurrencyConnectorHelper currencyConnectorHelper;
    private final EmailHelper emailHelper;

    @Override
    public ReservationResponse create(ReservationRequest reservationRequest) {
        blackListHelper.isInBlackListCutomer(reservationRequest.getIdClient());
        var hotel = this.hotelRepository.findById(reservationRequest.getIdHotel()).orElseThrow(()-> new IdNotFoundException("hotel"));
        var customer = customerRepository.findById(reservationRequest.getIdClient()).orElseThrow(()-> new IdNotFoundException("customer"));

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(reservationRequest.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(reservationRequest.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)))
                .build();

        var reservationPersisted = reservationRepository.save(reservationToPersist);
        customerHelper.incrase(customer.getDni(), ReservationService.class);
        if (Objects.nonNull(reservationRequest.getEmail()))this.emailHelper.sendMail(reservationRequest.getEmail(),customer.getFullName(),Tables.reservation.name());
        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = this.reservationRepository.findById(id).orElseThrow(()-> new IdNotFoundException(Tables.reservation.name()));
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest reservationRequest, UUID id) {
        var hotel = this.hotelRepository.findById(reservationRequest.getIdHotel()).orElseThrow(()-> new IdNotFoundException("hotel"));

        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow(()->new IdNotFoundException("reservation"));

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(reservationRequest.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(reservationRequest.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)));

        var reservationUpdate =this.reservationRepository.save(reservationToUpdate);

        log.info("Reservation updated with id {}",reservationUpdate.getId() );

        return this.entityToResponse(reservationUpdate);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = reservationRepository.findById(id).orElseThrow(()->new IdNotFoundException("reservation"));
        this.reservationRepository.delete(reservationToDelete);

    }

    private ReservationResponse entityToResponse(ReservationEntity entity){
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity,response);

        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(),hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }

    public BigDecimal findPrice(Long hotelId, Currency currency) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException(Tables.hotel.name()));
        var priceInDollars = hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage));
        if (currency.equals(Currency.getInstance("USD"))) return priceInDollars;
        var currencyDTO = this.currencyConnectorHelper.getCurrency(currency);
       log.info("API currency in {}, response {}", currencyDTO.getExchangeDate().toString(),currencyDTO.getRates());

    return  priceInDollars.multiply(currencyDTO.getRates().get(currency));
    }

    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.20);

}
