package com.intergramma.stock.dao;

import com.intergramma.product.Product;
import com.intergramma.stock.StockLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockLevelRepository extends JpaRepository<StockLevel, Long>
{
	Optional<StockLevel> findByProduct(Product product);

}
