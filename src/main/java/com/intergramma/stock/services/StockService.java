package com.intergramma.stock.services;

import com.intergramma.exceptions.NoSuchElementFoundException;
import com.intergramma.product.Product;
import com.intergramma.stock.StockLevel;

import java.util.List;

/**
 * Service class for handling  {@link com.intergramma.stock.StockLevel} and  {@link com.intergramma.stock.StockLevelReservation} related aspects
 */
public interface StockService
{
	/**
	 * Reserve stock for target stockLevel
	 *
	 * @param stockLevel     : target stock level referring
	 *                       to a product
	 * @param reservedAmount : target amount to reserve
	 * @return updated StockLevel
	 */
	StockLevel createStockReservation(StockLevel stockLevel, int reservedAmount);

	/**
	 * Release stock for overdue stock level reservations
	 * Looks for stock level reservations that are past their time limit and remove them
	 */
	void releaseStockLevelReservations();

	/**
	 * Retrieve all stock levels
	 *
	 * @return List of {@link StockLevel}
	 */
	List<StockLevel> getAllStockLevel();

	/**
	 * Retrieve stock level for target product
	 *
	 * @param productId : target product id
	 * @return {@link StockLevel} corresponding for target  {@link Product}
	 */
	StockLevel getStockLevel(String productId);

	/**
	 * Update stock level for target product
	 *
	 * @param productId : target product id
	 * @param amount    : current stock amount
	 * @return {@link StockLevel} corresponding for target  {@link Product}
	 * @throws NoSuchElementFoundException if Product doesnt exist
	 * @throws NoSuchElementFoundException if Stock Level doesnt exist
	 */
	StockLevel updateStockLevel(String productId, int amount);

	/**
	 * Create stock level for target product
	 * Update stock level if already existing
	 *
	 * @param productId : target product id
	 * @param amount    : current stock amount
	 * @return {@link StockLevel} corresponding for target  {@link Product}
	 * @throws NoSuchElementFoundException if Product doesnt exist
	 */
	StockLevel createStockLevel(String productId, int amount);

	/**
	 * Delete stock level for target product
	 *
	 * @param productId : target product id
	 * @throws NoSuchElementFoundException if Product doesnt exist
	 */
	void deleteStockLevel(String productId);

	/**
	 * Reserve stock for target product
	 *
	 * @param productId       : target product id
	 * @param amountToReserve : stock amount to reserve
	 * @return StockLevel corresponding for target  {@link Product}
	 * @throws NoSuchElementFoundException if Product doesnt exist
	 */
	StockLevel reserveStock(String productId, int amountToReserve);
}
