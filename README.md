# PetHub

PetHub is a pet-care e-commerce web application where users can browse and buy pets,
purchase pet products, book appointments (grooming/vet), leave reviews, and chat with
an assistant. Includes a separate admin panel to manage products and orders.

**Status:** This is the original (v1) version, built solo with plain Servlets, JSP, and JDBC.
It's currently being rewritten with Spring Boot + Spring Data JPA for cleaner layering
(DAO/Service/Controller separation) — see the `rebuild` branch for that progress.

## Tech Stack
- Java (Jakarta EE Servlets, Servlet API 6.0)
- JSP (JavaServer Pages)
- MySQL (via mysql-connector-j)
- Architecture: MVC (Servlets as Controllers, JSP as Views, DAO-style classes as Model)

## Features
- User registration, login, logout, forgot/reset password
- Browse pets and pet products by category
- Cart and wishlist management
- Place and cancel orders
- Book appointments
- Leave product/service reviews
- Admin panel: add/update/delete products, accept/cancel orders

## Setup
1. Install MySQL and create a database named `PetHub`
2. Import the schema matching the models in `src/main/java/com/MVC/Model`
3. Fill your MySQL credentials in `db.properties`
4. Deploy to a Servlet 6.0-compatible container (e.g. Tomcat 10+)
5. Open `Home1.jsp` in your browser

## Known Issues (being addressed in the Spring Boot rebuild)
- Business logic and DB access mixed into large classes rather than proper DAO/Service layers
- Some queries use string concatenation instead of PreparedStatements
- No build tool (Maven/Gradle) — dependencies are manually added JARs
