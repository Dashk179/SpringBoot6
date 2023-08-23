package com.curso.bestTravel;

import com.curso.bestTravel.domain.entities.ReservationEntity;
import com.curso.bestTravel.domain.entities.TicketEntity;
import com.curso.bestTravel.domain.entities.TourEntity;
import com.curso.bestTravel.domain.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
		//Extraemos un cliente
	var customer = customerRepository.findById("GOTW771012HMRGR087").orElseThrow();
	log.info("Client name = " + customer.getFullName());

	//Extraemos un vuelo
	var fly = flyRepository.findById(11L).orElseThrow();
		log.info("fly" + fly.getOrigin_name() + "-"+ fly.getDestiny_name());

	//Extraemos un hotel
	var hotel = hotelRepository.findById(3L).orElseThrow();
		log.info("hotel" + hotel);

		//Creamos un tour
	var tour = TourEntity.builder()
			.customer(customer).build();

	//Creamos un ticket y anexamos sus llaves foraneas
	var ticket = TicketEntity.builder()
			.id(UUID.randomUUID())
			.price(fly.getPrice().multiply(BigDecimal.TEN))
			.arrivalDate(LocalDate.now())
			.departureDate(LocalDate.now())
			.purchaseDate(LocalDate.now())
			.customer(customer)//Llave foranea
			.tour(tour)//Llave foranea
			.fly(fly)//Llave foranea
			.build();


	//Creamos una reservation y anexamos sus llaves foraneas
	var reservation = ReservationEntity.builder()
			.id(UUID.randomUUID())
			.dateTimeReservation(LocalDateTime.now())
			.dateEnd(LocalDate.now().plusDays(2))
			.dateStart(LocalDate.now().plusDays(1))
			.hotel(hotel)//Llave foranea
			.customer(customer)//Llave foranea
			.tour(tour)//Llave foranea
			.totalDays(1)
			.price(hotel.getPrice().multiply(BigDecimal.TEN))
			.build();

		System.out.println("---SAVING---");

		//Guardamos un tour a partir de nuestras relaciones inversas y podamos consultarlos apartir de un join
		tour.addReservation(reservation);
		tour.updateReservations();
		tour.addTicket(ticket);
		tour.updateTicket();

		var tourSaved = this.tourRepository.save(tour);
		Thread.sleep(8000);
		this.tourRepository.deleteById(tourSaved.getId());
	}
}
