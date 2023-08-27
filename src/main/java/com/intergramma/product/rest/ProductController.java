package com.intergramma.product.rest;

import com.intergramma.product.Product;
import com.intergramma.product.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController
{

	private final ProductService productService;

	public ProductController(ProductService productService)
	{
		this.productService = productService;
	}

	@Operation(summary = "Get All products")
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
			description = "Retrieved all products",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Product.class)) }) })
	@GetMapping("/products")
	List<Product> getAllProducts()
	{
		return productService.getAllProducts();
	}

}
