# Stock Management

## Getting Started

This application is based on Spring, just simply launch like any other spring boot application with maven :
> ./mvnw spring-boot:run


Data is in memory using H2 database, data is wiped on each restart but a convenience class named LoadDatabase loads some dummy data for
testing purpose.

This application is backend oriented with an exposed API, more info in the About section below.
Once startup is done :
> Started IntergrammaStockApplication in 1.798 seconds (process running for 2.2)

You can access the application through :  http://localhost:8080

## About

This application aims to manage stock of products from a single store.

It revolves around 3 aspects :

* Product : the actual product
* Stock Level : current stock level of target product
* Stock Level Reservation : in case of reservation of stock, a new Stock Level Reservation is done

### Security

Application is secured and endpoints can be accessible through 2 users :

* User role : user/password
* Admin role : admin/admin

User role only has read access while Admin can do everything such as Create / Update / Delete stock level and Reserve some stock

### API

API give you possibilities such as  :

* CRUD Stocklevels
* Reserve some stock for a certain period of time

> API doc is available : http://localhost:8080/swagger-ui/index.html#/

### Cronjob

Cronjob exists in order to remove overdue stock reservation :

* StockReservationCleanJob

*Cronjob defaut behaviour can be changed from properties*
> Maximum Time of stock reservation time (default 5 minutes) :
> > stock.maxreservationtime=300

> Delay for each iteration of the cronjob (default 30 seconds) :
> > stock.cleanjobdelay=30000

### Build & Tests

UnitTests with Mock have been created, to launch them simply use maven goal test :
> ./mvnw test

In order to fully build & test, use maven clean install goals
> ./mvnw clean install
