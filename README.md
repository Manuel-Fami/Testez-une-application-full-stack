# Yoga App!

## Description

The website allows potential clients to sign up for yoga sessions. This project is a Spring Boot application that manages the back end of a yoga website.
The goal of this project is to test the application.

Follow the steps below to test the project.

## Prerequisites

- Java 11
- Maven
- MySQL
- Java
- Node.js

## Installations

### Database installation

You must have MySQL installed beforehand. You can find the SQL script to create the tables in the following folder:

> src/main/ressources/scripts/script.sql

To connect, the environment variables are configured in the "application.properties" file. Feel free to modify these settings to match your MySQL environment.

### Clone the repository:

Clone the repository to your local machine:

> git clone https://github.com/Manuel-Fami/yoga-app.git

- Install the dependencies:
  **Backend**

  > mvn clean install

**Frontend**

> npm install

## Running the tests

**Backend**
Go in "yoga-app/back".
For the backend, tests are performed using the JUnit and Mockito dependencies.

To execute unit tests, use the following command:

> mvn clean test

This will run all the tests in the project and display the results in the console.

A coverage report of the application is also available in the following file:

> back/target/site/jacoco/index.html

**Frontend**
Go in "yoga-app/front".
For the frontend, unit tests are performed with the Jest dependency and end-to-end tests with Cypress.

To run unit tests, use the following command:

> npm run test

To generate a coverage report, use: npm run test

The report can be found in the following file:

> front/coverage/jest/lcov-report/index.html

To run end-to-end tests, use the following command:

> npm run e2e

To view the overview of these tests, use:

> npm run e2e:coverage
