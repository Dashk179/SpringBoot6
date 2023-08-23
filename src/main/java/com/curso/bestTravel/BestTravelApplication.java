package com.curso.bestTravel;

import com.curso.bestTravel.domain.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class BestTravelApplication  implements CommandLineRunner {


	private final HotelRepository hotelRepository;
	private final FlyRepository flyRepository;
	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;
	private final TourRepository tourRepository;
	private final CustomerRepository customerRepository;

	public BestTravelApplication(HotelRepository hotelRepository,
								 FlyRepository flyRepository,
								 TicketRepository ticketRepository,
								 ReservationRepository reservationRepository,
								 TourRepository tourRepository,
								 CustomerRepository customerRepository) {
		this.hotelRepository = hotelRepository;
		this.flyRepository = flyRepository;
		this.ticketRepository = ticketRepository;
		this.reservationRepository = reservationRepository;
		this.tourRepository = tourRepository;
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		var fly =  flyRepository.findById(15L).get();
//		var hotel = hotelRepository.findById(7L).get();
//		try {
//			var ticketId = UUID.fromString("42345678-1234-5678-5233-567812345678");
//			var ticket = ticketRepository.findById(ticketId);
//
//			if (ticket.isPresent()) {
//				// Ticket encontrado
//				log.info("Ticket encontrado: {}", ticket.get());
//			} else {
//				// Ticket no encontrado
//				log.warn("Ticket no encontrado para el ID: {}", ticketId);
//			}
//		} catch (IllegalArgumentException e) {
//			// Captura excepción si el UUID es inválido
//			log.error("Error al convertir el UUID: {}", e.getMessage());
//		} catch (Exception e) {
//			// Captura otras excepciones
//			log.error("Error al buscar el ticket: {}", e.getMessage());
//		}
//
//		var reservation = reservationRepository.findById(UUID.fromString("22345678-1234-5678-1234-567812345678")).get();
//		var ticket = ticketRepository.findById(UUID.fromString("42345678-1234-5678-5233-567812345678")).get();
//		var customer = customerRepository.findById("BBMB771012HMCRR022").get();
//
//		log.info(String.valueOf(reservation));
//		log.info(String.valueOf(ticket));
//		log.info(String.valueOf(customer));
//		log.info(String.valueOf(fly));
//		log.info(String.valueOf(hotel));

		//Probando nuestras consultas en JPQL

		//this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(f -> System.out.println(f));

		//this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10),BigDecimal.valueOf(15)).forEach(System.out::println);

//		this.flyRepository.selectOriginDestiny("Grecia","Mexico").forEach(System.out::println);

//		var fly = flyRepository.findByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678"));
//		System.out.println(fly);


		//Queries personalizados con lenguaje inclusivo de spring data

		//hotelRepository.findByPriceLessThan(BigDecimal.valueOf(100)).forEach(System.out::println);

//		hotelRepository.findByPriceBetween(BigDecimal.valueOf(100),
//				BigDecimal.valueOf(150)).forEach(System.out::println);

//		hotelRepository.findByRatingGreaterThan(3).forEach(System.out::println);

		var hotelReservation = hotelRepository.findByReservationId(UUID.fromString("12345678-1234-5678-1234-567812345678"));
		System.out.println(hotelReservation);

	}
}
