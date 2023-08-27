package com.intergramma.stock.services;

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
import com.intergramma.stock.services.impl.StockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest
{

	@InjectMocks
	@Spy
	private StockServiceImpl stockService;

	@Mock
	ProductService productService;

	@Mock
	StockLevelRepository stockLevelRepository;

	@Mock
	StockLevelReservationRepository stockLevelReservationRepository;
	@Mock
	StockProperties stockProperties;

	@Test
	void whenProductIdIsNull_thenGetStockLevelThrowsException()
	{
		String id = null;

		when(productService.getProduct(id)).thenThrow(NoSuchElementFoundException.class);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.getStockLevel(id);
		});
	}

	@Test
	void whenProductIdIsNotFound_thenGetStockLevelThrowsException()
	{
		String id = "NOTFOUNDID";

		when(productService.getProduct(id)).thenThrow(NoSuchElementFoundException.class);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.getStockLevel(id);
		});
	}

	@Test
	void whenStockLevelNotFound_thenGetStockLevelThrowsException()
	{
		String id = "1";
		Product product = new Product("Dummy Product");
		when(productService.getProduct(id)).thenReturn(product);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.getStockLevel(id);
		});
	}

	@Test
	void whenAllOK_thenGetStockLevelReturnsSomething()
	{
		String id = "1";
		Product product = new Product("Dummy Product");
		Optional<StockLevel> stockLevel = Optional.of(new StockLevel(product, 10));
		when(productService.getProduct(id)).thenReturn(product);
		when(stockLevelRepository.findByProduct(product)).thenReturn(stockLevel);
		assertEquals(stockService.getStockLevel(id), stockLevel.get());
	}

	@Test
	void whenAllOK_thenGetAllStockLevelReturnsSomething()
	{
		Product productA = new Product("Dummy Product");
		Product productB = new Product("Dummy Product B");

		List<StockLevel> stockLevelList = List.of(new StockLevel(productA, 10), new StockLevel(productB, 10));
		when(stockLevelRepository.findAll()).thenReturn(stockLevelList);
		assertEquals(stockService.getAllStockLevel(), stockLevelList);
	}

	@Test
	void whenProductIDNotFound_updateStockLevelThrowsException()
	{
		String id = "NOTFOUNDID";

		when(productService.getProduct(id)).thenThrow(NoSuchElementFoundException.class);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.updateStockLevel(id, 10);
		});
	}

	@Test
	void whenNoStockLevel_updateStockLevelThrowsException()
	{
		String id = "1";
		Product product = new Product("Dummy Product");

		when(productService.getProduct(id)).thenReturn(product);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.updateStockLevel(id, 10);
		});
	}

	@Test
	void whenAmountIsNegative_updateStockLevelThrowsException()
	{
		String id = "1";
		assertThrows(NegativeOrZeroStockAmountException.class, () -> {
			stockService.updateStockLevel(id, -10);
		});
	}

	@Test
	void whenAmountIsInferiorToCurrentAvailable_updateStockLevelRemovesStockLevelReservations()
	{
		String id = "1";
		Product product = new Product("Dummy Product");
		StockLevel stockLevel = new StockLevel(product, 10);
		StockLevelReservation slr = new StockLevelReservation(stockLevel, 10);
		stockLevel.getStockLevelReservations().add(slr);

		when(productService.getProduct(id)).thenReturn(product);
		when(stockLevelRepository.findByProduct(product)).thenReturn(Optional.of(stockLevel));

		stockService.updateStockLevel(id, 1);

		assertTrue(stockLevel.getAvailable() > 0);
	}

	@Test
	void whenAllIsOk_updateStockLevelUpdatesAmount()
	{
		String id = "1";
		Product product = new Product("Dummy Product");
		StockLevel stockLevel = new StockLevel(product, 10);
		assertEquals(10, stockLevel.getTotalAmount());

		when(productService.getProduct(id)).thenReturn(product);
		when(stockLevelRepository.findByProduct(product)).thenReturn(Optional.of(stockLevel));

		stockService.updateStockLevel(id, 1);

		assertEquals(1, stockLevel.getTotalAmount());
	}

	@Test
	void whenProductIDNotFound_createStockLevelThrowsException()
	{
		String id = "NOTFOUNDID";

		when(productService.getProduct(id)).thenThrow(NoSuchElementFoundException.class);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.createStockLevel(id, 10);
		});
	}

	@Test
	void whenAllIsOk_createStockLevelCreatesNewStockLevel()
	{
		String id = "1";
		Product product = new Product("Dummy Product");
		int stockLevelAmount = 3;
		when(productService.getProduct(id)).thenReturn(product);
		when(stockLevelRepository.save(any(StockLevel.class))).thenAnswer(i -> i.getArguments()[0]);

		StockLevel stockLevel = stockService.createStockLevel(id, stockLevelAmount);
		assertEquals(stockLevel.getTotalAmount(), stockLevelAmount);
	}

	@Test
	void whenNoProduct_deleteStockLevelThrowsException()
	{
		String id = "NOTFOUND";
		when(productService.getProduct(id)).thenThrow(NoSuchElementFoundException.class);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.deleteStockLevel(id);
		});
	}

	@Test
	void whenNoStockLevel_deleteStockLevelThrowsException()
	{
		String id = "1";
		Product product = new Product("Dummy Product");
		when(productService.getProduct(id)).thenReturn(product);
		assertThrows(NoSuchElementFoundException.class, () -> {
			stockService.deleteStockLevel(id);
		});
	}

	@Test
	void whenAllOK_deleteStockLevelDeletesReservationAndStockLevel()
	{
		String id = "1";
		Product product = new Product("Dummy Product");
		StockLevel stockLevel = new StockLevel(product, 10);
		StockLevelReservation slr = new StockLevelReservation(stockLevel, 10);
		stockLevel.getStockLevelReservations().add(slr);

		when(productService.getProduct(id)).thenReturn(product);
		when(stockLevelRepository.findByProduct(product)).thenReturn(Optional.of(stockLevel));
		stockService.deleteStockLevel(id);

		verify(stockLevelReservationRepository).deleteAll(new ArrayList<>(stockLevel.getStockLevelReservations()));

	}

	@Test
	void whenStockLevelIsNull_createStockReservationShouldThowException()
	{
		assertThrows(NullPointerException.class, () -> {
			stockService.createStockReservation(null, 10);
		});
	}

	@Test
	void whenAmountToReserveIsTooHigh_createStockReservationShouldThowException()
	{
		Product product = new Product("Dummy Product");
		StockLevel stockLevel = new StockLevel(product, 10);

		assertThrows(TooMuchReserveAmountException.class, () -> {
			stockService.createStockReservation(stockLevel, 20);
		});
	}

	@Test
	void whenAmountToReserveIsNegative_createStockReservationShouldThowException()
	{
		Product product = new Product("Dummy Product");
		StockLevel stockLevel = new StockLevel(product, 10);

		assertThrows(NegativeOrZeroStockAmountException.class, () -> {
			stockService.createStockReservation(stockLevel, -2);
		});
	}

	@Test
	void whenOK_createStockReservationShouldCreateProperReservation()
	{
		Product product = new Product("Dummy Product");
		int reservedAmount = 5;
		int reservedDelay = 10;
		StockLevel stockLevel = new StockLevel(product, 10);
		when(stockProperties.getMaxReservationTime()).thenReturn(reservedDelay);

		stockService.createStockReservation(stockLevel, reservedAmount);
		assertEquals(1, stockLevel.getStockLevelReservations().size());
		StockLevelReservation slr = stockLevel.getStockLevelReservations().iterator().next();
		assertEquals(stockLevel, slr.getStockLevel());
		assertEquals(reservedAmount, slr.getReservedAmount());
		assertTrue(slr.getReservedMaxTime().isAfter(LocalDateTime.now().plusSeconds(reservedDelay - 1)));
		assertTrue(slr.getReservedMaxTime().isBefore(LocalDateTime.now().plusSeconds(reservedDelay)));

	}

}