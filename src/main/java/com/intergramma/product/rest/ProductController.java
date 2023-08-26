package com.intergramma.product.rest;

import com.intergramma.product.Product;
import com.intergramma.product.services.ProductService;
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

	@GetMapping("/products")
	List<Product> getAllProducts()
	{
		return productService.getAllProducts();
	}

}
