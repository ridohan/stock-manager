package com.intergramma.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class NegativeOrZeroStockAmountException extends RuntimeException
{
	public NegativeOrZeroStockAmountException(String message)
	{
		super(message);
	}

}
