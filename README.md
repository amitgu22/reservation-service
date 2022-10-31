# reservation-service

REST API demonstration using MuServer library for simpler and faster REST API creation based on JAX-RS implementation.

## Mu server API documentation - https://muserver.io/

## Requirements

1: As a customer I want to be able to request a booking at this restaurant.

2: As the restaurant owner I want to be able to see all bookings for a particular day.


![Flow_Diagram](https://user-images.githubusercontent.com/11241862/199072159-e00dc45b-4f39-4e09-ab26-9efecd5c46bc.png)


## Desing and development features includes
       library provided SecuritContext configuration for role based API access
       Java 8 streams API
       Mu standalone http web server
       Handler mapping for rest resources and security features 


       
       
## JSON Request for adding new booking

            

       {
        "status": "New Booking",
        "customerName": "Amit Gurav",
        "startTime": "2022-10-31 18:52:13",
        "tableSize": 4,
        "contactNumber": "1234567898",
        "restaurantName": "China-Town"
       }

       

