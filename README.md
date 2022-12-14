# reservation-service

REST API demonstration using MuServer library for simpler and faster REST API creation based on JAX-RS implementation.

       ## required versions
       JDK 16
       Maven 4.0.0

## Mu server API documentation - https://muserver.io/

       # Flexible routing and filtering with request handlers
       # A clean model giving simple access to request and response objects
       # HTTP/2 Support
       # Convenient static resource handling, with GZIP, range requests, and custom response headers based on file type.
       # Extremely simple HTTPS configuration including Let's Encrypt integration
       # Server Sent Events
       # Web Sockets
       # JAX-RS Rest support with no extra dependencies, including:
       # Automatic documentation for rest services
       # Simple CORS support for JAX-RS
       # File Upload support
       # Optional async handling
       # Path contexts
       # A Reverse Proxy handler
       # Statistics
       # Various useful utilities

## Requirements

1: As a customer I want to be able to request a booking at this restaurant.

2: As the restaurant owner I want to be able to see all bookings for a particular day.

![flow_diagram_final](https://user-images.githubusercontent.com/11241862/199073276-e0b9ecaa-6a70-438a-9014-e96612e34e07.png)




## Desing and development features includes
       # library provided SecuritContext configuration for role based API access
       # Java 8 streams API
       # Mu standalone http web server
       # Handler mapping for rest resources and security features
       # Extremely simple HTTPS configuration 
       # Handler mapping for documentation
       # API Documentation UI -- https://localhost:{port}/api.html


       
       
## JSON Request for adding new booking

       
   

       {
        "status": "New Booking",
        "customerName": "Amit Gurav",
        "startTime": "2022-10-31 18:52:13",
        "tableSize": 4,
        "contactNumber": "1234567898",
        "restaurantName": "China-Town"
       }


       ## Basic Auth credentials required to access the API
       Amit s@curePa55word!
       


 
 ![ADD_BOOKING](https://user-images.githubusercontent.com/11241862/199080925-e1e8c210-3873-4642-a644-8767eaff9429.png)
 
 





![Screenshot 2022-11-01 010405](https://user-images.githubusercontent.com/11241862/199094272-760dbdf5-7f51-49bc-8968-a6f90420dc47.png)


