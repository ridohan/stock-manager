package com.intergramma.stock.services.impl;

import com.intergramma.exceptions.NegativeOrZeroStockAmountException;
import com.intergramma.exceptions.NoSuchElementFoundException;
import com.intergramma.exceptions.TooMuchReserveAmountException;
import com.intergramma.product.Product;
import com.intergramma.product.services.ProductService;
import com.intergramma.stock.StockLevel;
import com.intergramma.stock.StockLevelReservation;
import com.intergramma.stock.config.StockProperties;
import com.intergramma.stock.dao.StockLevelRepository;
import com.intergramma.stock.dao.StockLevelReservationRepository;
import com.intergramma.stock.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StockServiceImpl implements StockService
{
	private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

	private final StockProperties stockProperties;

	private final StockLevelRepository stockLevelRepository;
	private final StockLevelReservationRepository stockLevelReservationRepository;

	private final ProductService productService;

	public StockServiceImpl(StockLevelRepository stockLevelRepository, StockProperties stockProperties,
			StockLevelReservationRepository stockLevelReservationRepository, ProductService productService)
	{
		this.stockLevelRepository = stockLevelRepository;
		this.stockProperties = stockProperties;
		this.stockLevelReservationRepository = stockLevelReservationRepository;
		this.productService = productService;
	}

	@Override
	public StockLevel createStockReservation(StockLevel stockLevel, int reservedAmount)
	{
		Objects.requireNonNull(stockLevel);
		if (reservedAmount <= 0)
		{
			throw new NegativeOrZeroStockAmountException(
					String.format("Invalid amount to reserve < 0 ,reservedAmount wished %d, stockLevel %s", reservedAmount, stockLevel));
		}
		if (stockLevel.getAvailable() > reservedAmount)
		{
			StockLevelReservation slr = new StockLevelReservation(stockLevel, reservedAmount);
			slr.setReservedMaxTime(LocalDateTime.now().plusSeconds(getStockProperties().getMaxReservationTime()));
			stockLevel.getStockLevelReservations().add(slr);

			return getStockLevelRepository().save(stockLevel);
		}
		else
		{
			throw new TooMuchReserveAmountException(
					String.format("Reserved amount wished is superior than available,reservedAmount wished %d, stockLevel %s",
							reservedAmount, stockLevel));
		}

	}

	@Override
	public void releaseStockLevelReservations()
	{
		List<StockLevelReservation> stockLevelReservationsToRelease = getStockLevelReservationRepository().findAllStockLevelReservationOverdue(
				LocalDateTime.now());
		log.info("StockLevelReservations to release {}", stockLevelReservationsToRelease);
		log.info("Removing {} StockLevelReservations", stockLevelReservationsToRelease.size());
		log.debug("Existing {} StockLevels", stockLevelRepository.count());
		log.debug("Info StockLevels {}", stockLevelRepository.findAll());

		getStockLevelReservationRepository().deleteAll(stockLevelReservationsToRelease);
	}

	@Override
	public List<StockLevel> getAllStockLevel()
	{
		return getStockLevelRepository().findAll();
	}

	@Override
	public StockLevel getStockLevel(String productId)
	{
		Optional<StockLevel> stockLevel = getStockLevelOptional(productId);
		if (stockLevel.isPresent())
		{
			return stockLevel.get();
		}
		else
		{
			throw new NoSuchElementFoundException(String.format("StockLevel for ProductID %s not found", productId));
		}

	}

	private Optional<StockLevel> getStockLevelOptional(String productId)
	{
		Product product = getProductService().getProduct(productId);
		return getStockLevelRepository().findByProduct(product);
	}

	@Override
	public StockLevel updateStockLevel(String productId, int amount)
	{
		if (amount <= 0)
		{
			throw new NegativeOrZeroStockAmountException(String.format("StockLevel Amount cannot be <= 0 for ProductID %s ", productId));
		}
		StockLevel stockLevel = getStockLevel(productId);
		stockLevel.setTotalAmount(amount);
		if (stockLevel.getAvailable() < 0)
		{
			List<StockLevelReservation> slrsToDelete = new ArrayList<>(stockLevel.getStockLevelReservations());
			stockLevel.setStockLevelReservations(new HashSet<>());
			stockLevelReservationRepository.deleteAll(slrsToDelete);
		}
		return stockLevelRepository.save(stockLevel);
	}

	@Override
	public StockLevel createStockLevel(String productId, int amount)
	{
		Optional<StockLevel> stockLevelOptional = getStockLevelOptional(productId);
		StockLevel stockLevel;
		if (stockLevelOptional.isEmpty())
		{
			Product product = getProductService().getProduct(productId);
			stockLevel = new StockLevel(product, amount);
		}
		else
		{
			stockLevel = stockLevelOptional.get();
		}

		stockLevel.setTotalAmount(amount);

		return stockLevelRepository.save(stockLevel);
	}

	@Override
	public void deleteStockLevel(String productId)
	{
		StockLevel stockLevel = getStockLevel(productId);
		getStockLevelReservationRepository().deleteAll(new ArrayList<>(stockLevel.getStockLevelReservations()));
		getStockLevelRepository().delete(stockLevel);
	}

	@Override
	public StockLevel reserveStock(String productId, int amountToReserve)
	{
		StockLevel stockLevel = getStockLevel(productId);

		return createStockReservation(stockLevel, amountToReserve);
	}

	public StockProperties getStockProperties()
	{
		return stockProperties;
	}

	public StockLevelRepository getStockLevelRepository()
	{
		return stockLevelRepository;
	}

	public StockLevelReservationRepository getStockLevelReservationRepository()
	{
		return stockLevelReservationRepository;
	}

	public ProductService getProductService()
	{
		return productService;
	}
}
