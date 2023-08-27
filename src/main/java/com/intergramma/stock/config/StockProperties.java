package com.intergramma.stock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "stock")
@Configuration("stockProperties")
public class StockProperties
{
	private int maxReservationTime;
	private int cleanJobDelay;

	public int getMaxReservationTime()
	{
		return maxReservationTime;
	}

	public void setMaxReservationTime(int maxReservationTime)
	{
		this.maxReservationTime = maxReservationTime;
	}

	public int getCleanJobDelay()
	{
		return cleanJobDelay;
	}

	public void setCleanJobDelay(int cleanJobDelay)
	{
		this.cleanJobDelay = cleanJobDelay;
	}
}
