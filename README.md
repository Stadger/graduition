[![Codacy Badge](https://app.codacy.com/project/badge/Grade/f283439da3b44e7aa9a0b6c38c1ff32c)](https://www.codacy.com/gh/Stadger/graduition/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Stadger/graduition&amp;utm_campaign=Badge_Grade)
# REST API service project (Graduation TopJava)

----
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.
*  2 types of users: admin and regular users
*  Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
*  Menu changes each day (admins do the updates)
*  Users can vote on which restaurant they want to have lunch at
*  Only one vote counted per user
*  If user votes again the same day:
    -  If it is before 11:00 we assume that he changed his mind.
    -  If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it (**better - Swagger**).

-----------------------------

# technologies used in the project
* Maven
* Spring Boot
* Spring Data JPA
* H2 SQLDB (in memory DB)
* Hibernate
* JUnit
* Spring cache

# REST API

API documentation generate Swagger: http://localhost:8080/swagger-ui.html

# Authentication requests
*  /api/profile post: register request. Available to unregistered users
*  /api/admin/**
  *  Available to admin-role users
  *  use base authentication: admin@gmail.com/admin
*  /api/**
  *  Available to registered(admin and user) users
  *  use base authentication: user@yandex.ru/password
# Requirements
* JDK 16
* Maven