# Stock Management

## About

This application aims to manage stock of products from a single store.

It revolves around 3 aspects :

* Product : the actual product
* Stock Level : current stock level of target product
* Stock Level Reservation : in case of reservation of stock, a new Stock Level Reservation is done

API give you possibilities such as  :

* CRUD Stocklevels
* Reserve some stock for a certain period of time

> API doc is available :http://localhost:8080/swagger-ui/index.html#/

Cronjob exists in order to remove overdue stock reservation :

* StockReservationCleanJob

*Cronjob defaut behaviour can be changed from properties*
> Maximum Time of stock reservation time (default 5 minutes) :
> > stock.maxreservationtime=300

> Delay for each iteration of the cronjob (default 30 seconds) :
> > stock.cleanjobdelay=30000

## Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.3/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

