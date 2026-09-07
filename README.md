# Automation Exercise Selenium Automation

## Project Overview

Selenium WebDriver automation framework for testing the Automation Exercise website.

## Technologies

* Java
* Selenium WebDriver
* TestNG
* Maven
* Allure Report
* WebDriverManager

## Test Scenarios

* Register a new account
* Register using an existing email
* Login with invalid credentials
* Login with valid credentials
* Search products
* Add products to cart
* Verify product quantities and totals
* Checkout and payment
* Download invoice

## Project Structure

```text
src
├── main/java
│   ├── base
│   ├── pages
│   └── utils
│
└── test/java
    ├── base
    └── tests
```

## How to Run

Run individual tests directly from IntelliJ using TestNG.

To execute the complete test suite with Maven:

```bash
mvn clean test
```

## Reporting

Allure is used for test reporting and failure screenshots.

Generate the report using:

```bash
allure serve allure-results
```
