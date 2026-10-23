# Todo List Application

A simple Todo List web application built with Java 17, Spring Boot, Thymeleaf, Bootstrap 4.6.2, and H2 Database.

## Requirements

* Java 17
* Maven Wrapper (included in the project)

## How to Run

1. Clone or download the project.

2. Open a terminal in the project root directory.

3. Run the following command on Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. Open your browser and visit:

   http://localhost:8081

## Features

* Add tasks
* Edit task titles
* Delete tasks
* Mark tasks as complete or incomplete
* Save tasks in a file-based H2 database

## Run Tests

Execute the following command in the project root:

```powershell
.\mvnw.cmd test
```

## Database

The application uses a file-based H2 database. Database files are stored in the `data` directory and persist between application restarts.

## Stop the Application

Press `Ctrl + C` in the terminal. 
