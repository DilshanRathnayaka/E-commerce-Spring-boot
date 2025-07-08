# 🛍️ Spring Boot E-Commerce Backend

A robust, full-featured e-commerce backend built using **Spring Boot**, designed with **loose coupling** and a **layered architecture** for scalability and maintainability. This application supports secure authentication, product and variant management, Stripe payments, image uploads, and email notifications — all in a monolithic setup with modular services.

---

## 🚀 Key Features

- 🔐 **Authentication & Authorization**
  - Spring Security with OAuth2
  - Google Login Integration
  - JWT Token-based session management
  - Role-based access control (Admin/User)

- 🛒 **Product & Variant Management**
  - Add/update/delete products and variants
  - Inventory tracking for each product variant

- 📤 **File Upload Service**
  - Multipart image upload support
  - Linked with product gallery feature

- 💳 **Payment Integration**
  - Seamless Stripe API integration
  - Secure checkout and transaction flow

- 📧 **Email Notification Service**
  - Gmail SMTP (Google Mail) integration
  - Order confirmation and account emails

- 🧾 **Order & Cart Management**
  - Add to cart, update quantity, remove items
  - Place orders and track order history

- 🧠 **Loose Coupling & Modularity**
  - Well-defined service layers with dependency injection
  - Interfaces and abstractions for core services
  - Easier to test, maintain, and extend

- ⚠️ **Centralized Exception Handling**
  - Global exception handler with meaningful responses

---

## 🧱 Architecture

- **Monolithic Design** with **Loose Coupling**
- **Layered Structure**:
  - `Controller → Service → Repository → Model`
- **Separated Services**:
  - AuthService, ProductService, FileService, MailService, PaymentService
- **Loose Coupling via**:
  - Interface-driven design
  - `@RequiredArgsConstructor` with constructor injection
  - Clear contract between layers

> This architecture promotes **reusability**, **testability**, and future scalability — such as migrating to microservices.

---

## ⚙️ Tech Stack

| Layer        | Technology                                |
|--------------|-------------------------------------------|
| Backend      | Spring Boot, Spring Web                   |
| Security     | Spring Security, OAuth2, JWT              |
| Auth         | Google OAuth2 Login, JWT Token            |
| Mail         | Gmail SMTP (JavaMailSender)               |
| Payment      | Stripe API                                |
| File Upload  | MultipartFile, REST                       |
| Build Tool   | Maven                                     |
| Database     | PostgreSQL / MySQL (Configurable)         |


