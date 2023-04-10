## Description
The goal of this project is to implement an application called 'city-list' to manage cities. The project contains back-end [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) application called `city-list-api` 
and font-end [ReactJS](https://reactjs.org/) application called `city-list-ui`. The back-end application uses [`MongoDB`](https://www.mongodb.com) as data storage. The initial data for DB is loaded from [cities.csv](city-list-api%2Fsrc%2Fmain%2Fresources%2Fcities.csv) sitting in resource folder. [`Basic Authentication`](https://en.wikipedia.org/wiki/Basic_access_authentication) is implemented to secure the applications. [`MongoDB test container`](https://www.testcontainers.org/modules/databases/mongodb/) was used to implement integration testing.

## Project Diagram

![SimpleDiagram.jpg](documentation%2FSimpleDiagram.jpg)

## Prerequisites

- [`npm`](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
- [`Java 17+`](https://www.oracle.com/java/technologies/downloads/#java17)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/) or [`MongoDB`](https://www.mongodb.com)
- [`Maven`](https://maven.apache.org)

## Applications

- ### city-list-api

  `Spring Boot` Web Java backend application that exposes a Rest API to populate initial city data, retrieve and update cities. Only if a user has `FULL_ACCESS` authority he/she can update city.

  The application secured endpoints can just be just accessed if a user has valid credentials (`username` and `password`) and has autorization roles for it.

  `city-list-api` stores its data in [`MongoDB`](https://www.mongodb.com) database. The default security and default port(27017) should be upplied on MongoDB side.

  `city-list-api` has the following endpoints

  | Endpoint                                                 | Secured | Authorities                     |
  |----------------------------------------------------------|---------|---------------------------------|
  | `GET /api/v1/user/context -d {"username","password"}`    | Yes     | `FULL_ACCESS, READ_ONLY_ACCESS` |
  | `GET /api/v1/cities/count`                               | Yes     | `FULL_ACCESS, READ_ONLY_ACCESS` |
  | `GET /api/v1/cities/ [?name],[?pageNumber],[?pageSize]`  | Yes     | `FULL_ACCESS, READ_ONLY_ACCESS` |
  | `PUT api/v1/cities/{id} {"id","name", "photo"}`          | Yes     | `FULL_ACCESS`                   |

You can take a look at endpoint documentation using this URL http://localhost:8080/swagger-ui/index.html after starting application.

- ### city-list-ui

  `ReactJS` frontend application where a user with role `READ_ONLY_ACCESS` can retrieve the information of a specific city or a list of cities. On the other hand, a user with role `FULL_ACCESS` has access to all secured endpoints.

  To login, a `user` or `admin` must provide valid `username` and `password` credentials. `city-list-ui` communicates with `city-list-api` to get `cities` and `users` data.

  `city-list-ui` uses [`Semantic UI React`](https://react.semantic-ui.com/) as CSS-styled framework.

## Build city-list application using Maven

- **city-list-api**

  - Open a terminal and navigate to parent folder folder

  - Run the following `Maven` command to build the application
    ```
    mvn clean install
    ```

Note: You can use IntelliJ IDEA to build and run application using GUI.

## Start Application

- In a terminal, make sure you are inside [city-list-api](city-list-api) root folder and see [docker-compose.yml](city-list-api%2Fdocker-compose.yml)

- Run the following command to start docker-compose containers. It will start MongoDB.
  ```
  docker-compose up
  ```
Note: if you want to degug application on your localhost you need to start Mongo DB only in Docker. In this case you will run app from IDE.
You need to clean up docker compose file. The result should be:

![docker-mongo.png](documentation%2Fdocker-mongo.png)


## Running city-list-ui application for development use only (run npm)

- **city-list-ui**

    - Open another terminal and navigate to `city-list-ui` folder

    - Run the command below if you are running the application for the first time
      ```
      npm install
      ```

    - Run the `npm` command below to start the application
      ```
      npm start
      ```

## Applications URLs

| Application   | URL                                         | Credentials                        |
|---------------|---------------------------------------------|------------------------------------|
| city-list-api | http://localhost:8080/swagger-ui/index.html | `admin/admin123`, `user/user123`    |
| city-list-ui  | http://localhost:3000                       | `admin/admin123`, `user/user123`   |

> **Note**: the credentials shown in the table are the ones already pre-defined in [application.yml](city-list-api%2Fsrc%2Fmain%2Fresources%2Fapplication.yml).

## Demo

 The gif below shows UI functionality

  ![demo.gif](documentation%2Fdemo.gif)

 The gif below shows Swagger functionality
![DemoSwagger.gif](documentation%2FDemoSwagger.gif)

## How to run application using docker
[![Watch the video](https://i.imgur.com/vKb2F1B.png)](https://1drv.ms/v/s!Agy9rAmpfPqxg6hLRLwRCN-wGMT-nQ?e=vTODoD)


## How to run application from IntelliJ IDEA
![RunUsingIDE.gif](documentation%2FRunUsingIDE.gif)
