# Bank X Application

## Overview

The **Bank X Application** is a standalone Spring Boot-based banking system designed to support both internal and external system integrations. 
It enables customer onboarding, account management, transaction tracking, and external bank integration. 
The system provides a full-stack experience with a backend in Java and a frontend rendered with Thymeleaf.

## Business Case

Bank X aims to modernize its banking system with the following core features:

- Onboard new customers with automatic creation of both **Current** and **Savings** accounts.
- Credit a **joining bonus** of R500.00 to every new customer’s **Savings Account**.
- Support **fund transfers** between accounts.
- Enable **payments** only from the **Current Account**.
- Credit **0.5% interest** to the **Savings Account** for all deposits.
- Charge **0.05% fee** on all outgoing payments.
- Maintain a **complete transaction history**.
- Send **notifications** to customers for every transaction event.
- Allow **Bank Z** to:
  - Perform **immediate debit/credit transactions** on Bank X customer accounts.
  - Submit a **batch of transactions** for reconciliation at the end of each business day.

---

## Features

- Customer onboarding  
- Current & Savings account management  
- Fund transfer & payments with fee/interest handling  
- Transaction history tracking  
- External system (Bank Z) integration  
- Real-time customer notifications  
- Thymeleaf-based web UI  
- SQLite database support

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- SQLite (bundled, no setup needed)

### Run Locally

```bash
git clone https://github.com/your-username/bank-x-app.git
cd bank-x-app
mvn spring-boot:run

### Configuration    

Before running the application, open the `application.properties` file located under the `src/main/resources` directory and update the SQLite database path to point to your own machine:

```properties
spring.datasource.url=jdbc:sqlite:C:/Users/godfrey.marosha/OneDrive - Omnia/Documents/Projects/BankXApp/bankx.db
