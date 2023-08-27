package com.intergramma.stock.rest;

import com.intergramma.product.Product;
import com.intergramma.stock.StockLevel;
import com.intergramma.stock.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController
{

	private final StockService stockService;

	public StockController(StockService stockService)
	{
		this.stockService = stockService;
	}

	@Operation(summary = "Get All stockLevels")
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
			description = "Retrieved all stockLevels",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Product.class)) }) })
	@GetMapping("/")
	List<StockLevel> getAllStockLevels()
	{
		return stockService.getAllStockLevel();
	}

	@Operation(summary = "Get a stock level for specified product id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
			description = "Retrieved the stock level",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = StockLevel.class)) }), @ApiResponse(responseCode = "404",
			description = "Product not found or StockLevel not found",
			content = @Content) })
	@GetMapping("/product/{productId}")
	StockLevel getStockLevelOfProduct(@PathVariable String productId)
	{
		return stockService.getStockLevel(productId);
	}

	@Operation(summary = "Reserve stock for specified product id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
			description = "Stock is reserved",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = StockLevel.class)) }), @ApiResponse(responseCode = "404",
			description = "Product not found or Stock level Not found",
			content = @Content), @ApiResponse(responseCode = "422",
			description = "Amount to reserve invalid : negative or too high compared to available stock",
			content = @Content) })
	@PostMapping("/product/{productId}/reserve")
	StockLevel reserveStockOfProduct(@PathVariable String productId, @RequestParam int amount)
	{
		return stockService.reserveStock(productId, amount);
	}

	@Operation(summary = "Create a stock level for specified product id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
			description = "Newly created stock level",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = StockLevel.class)) }), @ApiResponse(responseCode = "404",
			description = "Product not found",
			content = @Content) })
	@PostMapping("/product/{productId}")
	StockLevel createStockLevelOfProduct(@PathVariable String productId, @RequestParam int amount)
	{

		return stockService.createStockLevel(productId, amount);
	}

	@Operation(summary = "Update stock level for specified product id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
			description = "Stock is updated",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = StockLevel.class)) }), @ApiResponse(responseCode = "404",
			description = "Product not found or Stock level Not found",
			content = @Content), @ApiResponse(responseCode = "422",
			description = "Amount invalid : negative",
			content = @Content) })
	@PutMapping("/product/{productId}")
	StockLevel updateStockLevelOfProduct(@PathVariable String productId, @RequestParam int amount)
	{
		return stockService.updateStockLevel(productId, amount);
	}

	@Operation(summary = "Delete stock level for specified product id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
			description = "Stock level is deleted",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = StockLevel.class)) }), @ApiResponse(responseCode = "404",
			description = "Product not found or Stock level Not found",
			content = @Content) })
	@DeleteMapping("/product/{productId}")
	void deleteStockLevelOfProduct(@PathVariable String productId)
	{
		stockService.deleteStockLevel(productId);
	}

}
