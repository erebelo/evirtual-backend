# eVirtual backend
E-commerce REST API developed in Java with Spring Boot framework.

----------

## Features
- Authentication and Authorization by JWT (JSON Web Token)
- Google SMTP server to send outgoing email
- Amazon S3 (Simple Storage Service) for image storage
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

## Releases
[UML Diagram, Release Notes][2]

----------

## Demo
[Swagger UI][3]

----------

[1]: https://github.com/erebelo/evirtual-backend/blob/master/src/main/java/com/erebelo/evirtual/EvirtualApplication.java
[2]: https://github.com/erebelo/evirtual-backend/releases/tag/1.0.0
[3]: https://evirtual-api.herokuapp.com/swagger-ui.html