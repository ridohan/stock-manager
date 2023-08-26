package com.intergramma.product.services;

import com.intergramma.product.Product;

import java.util.List;

/**
 * Service class for handling all {@link com.intergramma.product.Product} related aspects
 */
public interface ProductService
{
	/**
	 *  Retrieve all existing products
	 * @return List of {@link com.intergramma.product.Product}
	 */
	List<Product> getAllProducts();
}
