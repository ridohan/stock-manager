package com.intergramma.stock;

import com.intergramma.product.Product;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class StockLevel
{
	@Id
	@GeneratedValue
	@Column(name = "id",
			updatable = false,
			nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn(name = "product_id",
			referencedColumnName = "id")
	private Product product;

	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	private Set<StockLevelReservation> stockLevelReservations = new HashSet<>();

	private int totalAmount;

	public StockLevel()
	{
	}

	public StockLevel(Product product, int totalAmount)
	{
		this.product = product;
		this.totalAmount = totalAmount;
	}

	public Long getId()
	{
		return id;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public Set<StockLevelReservation> getStockLevelReservations()
	{
		return stockLevelReservations;
	}

	public int getAvailable()
	{
		return getTotalAmount() - (Optional.ofNullable(getStockLevelReservations()).orElse(Collections.emptySet()).stream()
				.mapToInt(value -> value.getReservedAmount()).sum());
	}

	public int getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public void setStockLevelReservations(Set<StockLevelReservation> stockLevelReservations)
	{
		this.stockLevelReservations = stockLevelReservations;
	}

	@Override
	public String toString()
	{
		return String.format("StockLevel available/total : %d/%d of product %d", this.getAvailable(), this.getTotalAmount(),
				this.product.getId());
	}
}
