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

public class BestTravelApplication {


	public static void main(String[] args) {SpringApplication.run(BestTravelApplication.class, args);}
	}

