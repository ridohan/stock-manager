package com.intergramma.product.services.impl;

import com.intergramma.exceptions.NoSuchElementFoundException;
import com.intergramma.product.Product;
import com.intergramma.product.dao.ProductRepository;
import com.intergramma.product.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
		return getProductRepository().findAll();
	}

	@Override
	public Product getProduct(String productId)
	{
		Optional<Product> product = getProductRepository().findById(Long.valueOf(productId));
		if (product.isPresent())
		{
			return product.get();
		}
		else
		{
			throw new NoSuchElementFoundException(String.format("ProductID %s not found", productId));
		}
	}

	public ProductRepository getProductRepository()
	{
		return productRepository;
	}
}
