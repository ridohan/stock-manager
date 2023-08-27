package com.intergramma.stock.cron;

import com.intergramma.stock.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockReservationCleanJob
{
	private static final Logger log = LoggerFactory.getLogger(StockReservationCleanJob.class);

	final StockService stockService;

	public StockReservationCleanJob(StockService stockService)
	{
		this.stockService = stockService;
	}

	@Scheduled(fixedDelayString = "${stock.cleanjobdelay}")
	public void scheduleFixedDelayTask()
	{
		log.info("Cleaning stock reservations that are overdue");
		getStockService().releaseStockLevelReservations();
	}

	public StockService getStockService()
	{
		return stockService;
	}
}
