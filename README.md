# evirtual-backend
E-commerce REST API developed in Java with Spring Boot framework.

----------

## Features
- Authentication and Authorization by JWT
- Google SMTP server to send outgoing email
- Amazon Simple Storage Service S3 for image storage
- Heroku deployment

----------

## Requirements
- Java 11
- Spring Boot 2.4.5
- Apache Maven 3.6.3 

----------

## Environment Variables
- JWT_SECRET
- JWT_EXPIRATION_TIME
- SENDER_EMAIL
- RECIPIENT_EMAIL
- USERNAME_EMAIL
- PASSWORD_EMAIL

----------

## Run App
Run the [EvirtualApplication][1] class as Java Application

----------

## Swagger
URL: http://localhost:8090/swagger-ui.html

----------

## Demo
[evirtual-api][2]

----------

## Releases
[UML Diagram, Release Notes][3]

----------

[1]: https://github.com/erebelo/evirtual-backend/blob/master/src/main/java/com/erebelo/evirtual/EvirtualApplication.java
[2]: https://evirtual-api.herokuapp.com/swagger-ui.html
[3]: https://github.com/erebelo/evirtual-backend/releases/tag/1.0.0