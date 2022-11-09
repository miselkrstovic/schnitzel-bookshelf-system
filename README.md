# Schnitzel Bookshelf System
You received a task from the company Schnitzel GmbH to develop a simple BookShelf web application for managing the books catalog. It is expected that the solution provides a simple frontend web application, which communicates with the server via REST API.

The BookShelf application should fulfil the following requirements:
- Store a new book item in the system.
    - Each book has an International Standard Book Number (ISBN), name, authors and a short annotation
- Update the book’s properties.
- Delete a book.
- Search for books by ISBN, name or author.

Your responsibility is to the design a REST API and implement the backend and frontend applications.

You are free to choose the technology stack, but we would appreciate if the programming language or framework used come from the Java, Python, JavaScript or TypeScript ecosystem.

A persistence layer is not needed, the data could be stored in memory. 

A few key things Schnitzel GmbH will be looking at are:
- Your approach to testing
- Use of frameworks/open source components to simplify implementation
- Clear architecture, and simple, easy-to-maintain code
- Frontend user experience, provided solution should look better than examples from
here

Do not hesitate to ask any questions regarding the requirements, in case if something is not clear.

## Install
The system is composed of a frontend application developed using [angular](https://angular.io) and a backend api developed using [spring boot](https://spring.io). Persistence is done in memory using an h2 database.

Java development kit and maven need to be installed to properly run the backend.
```
$ cd api-service/
$ mvn spring-boot:run
```

Nodejs, npm, and ng need to be installed to bring the frontend application up.
```
$ cd frontend-service/
$ ng serve
``` 

The application can be accessed using [http://localhost:4200/](http://localhost:4200/)

The h2 console is available to inspect the proper persistence of objects in the system, and can be accessed using [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/), the datasource url is **jdbc:h2:mem:schnitzel**. 

An importable [postman](https://www.postman.com) collection [file](postman.json) is available to test various api backend features.
