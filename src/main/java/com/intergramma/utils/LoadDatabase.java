package com.intergramma.utils;

import com.intergramma.product.Product;
import com.intergramma.product.dao.ProductRepository;
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
	CommandLineRunner initDatabase(ProductRepository repository)
	{

		return args -> {
			log.info("Initializing {}", repository.save(new Product("Airpress compressor set HLO 215/25")));
			log.info("Initializing {}", repository.save(new Product("LUX cordless drill 18V")));
		};
	}
}
