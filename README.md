
# Todo List Application

![Java 17](https://img.shields.io/badge/Java-17-2ea44f?style=flat)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat&logo=springboot&logoColor=white)
![CI: GitHub Actions](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)
![CD: Azure DevOps](https://img.shields.io/badge/CD-Azure_DevOps-0078D4?style=flat&logo=azuredevops&logoColor=white)

[![codecov](https://codecov.io/gh/truonghuy2490/todo-spring-ci-demo/branch/main/graph/badge.svg)](https://app.codecov.io/github/truonghuy2490/todo-spring-ci-demo)

A simple Todo List web application built with Java 17, Spring Boot, Thymeleaf, Bootstrap 4.6.2, and H2 Database. This project demonstrates a complete CI/CD workflow using GitHub Actions, Azure DevOps Pipelines, and Azure App Service.


## Live Demo

**[Open Todo List Application](https://todo-demo-dzg6hte9bgbzdcbe.southeastasia-01.azurewebsites.net/)**

## Tech Stack

- **Backend:** Java 17, Spring Boot
- **Frontend:** Thymeleaf, Bootstrap 4.6.2
- **Database:** H2 Database
- **Build Tool:** Maven Wrapper
- **CI:** GitHub Actions
- **CD:** Azure DevOps Pipelines
- **Hosting:** Azure App Service (Linux, Java 17)

## Features

- Create new tasks
- Edit task titles
- Delete tasks
- Mark tasks as complete or incomplete
- Display task status in a simple, responsive interface
- Store tasks using an H2 database

## CI/CD Pipeline

The project uses GitHub Actions for Continuous Integration and Azure DevOps Pipelines for Continuous Deployment.

1. **Code Push:** Push changes to the `main` branch on GitHub.
2. **Continuous Integration:** GitHub Actions builds the application and runs automated tests.
3. **Build and Validation:** Azure Pipelines validates the Spring Boot application using Maven Wrapper and runs the configured test steps.
4. **Artifact Publishing:** The pipeline publishes the packaged JAR for deployment.
5. **Continuous Deployment:** Azure Pipelines deploys the application to Azure App Service.
6. **Live Application:** The updated application becomes available through its Azure-hosted URL.

> Deployment runs only when the pipeline's configured branch and conditions are satisfied.

## Requirements

- Java 17
- Maven Wrapper (included in the repository)

## Getting Started

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd <YOUR_PROJECT_DIRECTORY>
```

Replace the placeholders with your repository URL and project directory.

### 2. Run the Application

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
chmod +x ./mvnw
./mvnw spring-boot:run
```

### 3. Open the Application

Visit:

[http://localhost:8081](http://localhost:8081)

### 4. Run Tests

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

On macOS or Linux:

```bash
./mvnw test
```

To run the full Maven verification lifecycle, including configured verification checks:

```bash
./mvnw clean verify
```

## Database

The application uses H2 Database. In the local environment, it is configured to store database files in the `data` directory, allowing data to persist between application restarts.

**Note:** Persistence on Azure depends on the configured database URL and storage. An in-memory H2 database loses its data when the application restarts, while local file-based storage does not automatically guarantee persistence on Azure App Service.

## Deployment

The application is hosted on Azure App Service with the Linux operating system and Java 17 runtime.

The deployment workflow is configured through Azure DevOps Pipelines, which builds and packages the application before deploying the JAR artifact to the Web App.

## Stopping the Application

Press `Ctrl + C` in the terminal to stop the local application.

## License

This project is intended for learning and demonstration purposes.