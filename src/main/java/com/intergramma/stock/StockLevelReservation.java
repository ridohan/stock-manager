package com.intergramma.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class StockLevelReservation
{

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "stock_id",
			referencedColumnName = "id")
	@JsonIgnore
	private StockLevel stockLevel;

	@PreRemove
	private void removeStockLevelReservationsFromStockLevel()
	{
		stockLevel.getStockLevelReservations().remove(this);
	}

	private int reservedAmount;
	@Column(name = "reserved_max_time",
			columnDefinition = "TIMESTAMP")
	private LocalDateTime reservedMaxTime;

	public StockLevelReservation()
	{
	}

	public StockLevelReservation(StockLevel stockLevel, int reservedAmount)
	{
		this.stockLevel = stockLevel;
		this.reservedAmount = reservedAmount;
	}

	public Long getId()
	{
		return id;
	}

	public int getReservedAmount()
	{
		return reservedAmount;
	}

	public void setReservedAmount(int reservedAmount)
	{
		this.reservedAmount = reservedAmount;
	}

	public StockLevel getStockLevel()
	{
		return stockLevel;
	}

	public void setStockLevel(StockLevel stockLevel)
	{
		this.stockLevel = stockLevel;
	}

	public LocalDateTime getReservedMaxTime()
	{
		return reservedMaxTime;
	}

	public void setReservedMaxTime(LocalDateTime reservedMaxTime)
	{
		this.reservedMaxTime = reservedMaxTime;
	}

	@Override
	public String toString()
	{
		return String.format("StockLevelReservation reservedAmount : %d  / reservedMaxTime  %s", this.getReservedAmount(),
				this.getReservedMaxTime());
	}
}
