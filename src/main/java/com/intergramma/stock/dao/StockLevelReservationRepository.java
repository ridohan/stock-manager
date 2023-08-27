package com.intergramma.stock.dao;

import com.intergramma.stock.StockLevelReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StockLevelReservationRepository extends JpaRepository<StockLevelReservation, Long>
{

	@Query("select slr from StockLevelReservation slr where slr.reservedMaxTime <= :endDateTime")
	List<StockLevelReservation> findAllStockLevelReservationOverdue(@Param("endDateTime") LocalDateTime endDateTime);
}
