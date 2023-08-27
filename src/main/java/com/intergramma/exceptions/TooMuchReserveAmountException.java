package com.intergramma.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class TooMuchReserveAmountException extends RuntimeException
{
	public TooMuchReserveAmountException(String message)
	{
		super(message);
	}

}
