package com.intergramma.utils;

import com.intergramma.product.Product;
import com.intergramma.product.dao.ProductRepository;
import com.intergramma.stock.StockLevel;
import com.intergramma.stock.dao.StockLevelRepository;
import com.intergramma.stock.dao.StockLevelReservationRepository;
import com.intergramma.stock.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase
{
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(ProductRepository productRepository, StockLevelRepository stockLevelRepository,
			StockLevelReservationRepository stockLevelReservationRepository, StockService stockService)
	{

		return args -> {

			Product dummyProductA = productRepository.save(new Product("Airpress compressor set HLO 215/25"));
			StockLevel stockLevelA = new StockLevel(dummyProductA, 100);
			stockService.createStockReservation(stockLevelA, 10);

			log.info("Initializing {}", dummyProductA);
			log.info("stockLevels  {}", stockLevelRepository.findAll());

			stockService.createStockReservation(stockLevelA, 20);
			log.info("stockLevels  {}", stockLevelRepository.findAll());
			log.info("stockLevelReservations  {}", stockLevelReservationRepository.findAll());

			log.info("Initializing {}", productRepository.save(new Product("LUX cordless drill 18V")));

		};
	}
}
