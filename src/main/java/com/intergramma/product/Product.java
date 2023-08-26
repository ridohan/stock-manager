package com.intergramma.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Product
{
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public Product()
	{
	}

	public Product(String name)
	{
		this.name = name;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(this.id, product.id) && Objects.equals(this.name, product.name);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.id, this.name);
	}

	@Override
	public String toString()
	{
		return String.format("Product (ID %d / Name %s)", this.id, this.name);
	}
}
