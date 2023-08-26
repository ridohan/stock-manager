package com.intergramma.product.services.impl;

import com.intergramma.product.Product;
import com.intergramma.product.dao.ProductRepository;
import com.intergramma.product.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{
	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository)
	{
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts()
	{
		return productRepository.findAll();
	}
}
