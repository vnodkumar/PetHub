# PetHub — Spring Boot Rebuild (WIP)

This is a ground-up rebuild of PetHub, moving from the original Servlet/JSP/JDBC version
(see the `main` branch) to a proper layered Spring Boot REST API, paired with a React
frontend (not yet started).

**Status:backend complete.**  Business logic, validation, exception handling, and Security (JWT auth, role-based access, ownership checks) are all built and tested via Postman. CORS is configured. The React frontend has been started.

## Why rebuild
The original PetHub (`main` branch) was built with plain Servlets, JSP, and JDBC, with all
business logic and data access mixed into a few large classes. This rebuild fixes that with
proper separation into Controller → Service → Repository → Entity layers, uses
Spring Data JPA instead of hand-written JDBC/SQL, replaces hardcoded DB credentials and
SQL-injection-prone string-concatenated queries with parameterized queries by default, and
adds input validation and consistent error handling throughout.

## Tech Stack
- Java, Spring Boot
- Spring Data JPA / Hibernate
- Spring Security + JWT (stateless auth, role-based access control)
- MySQL
- REST API (`@RestController`) — not Thymeleaf; frontend will be a separate React app
- Bean Validation (`@Valid`, `@NotBlank`, etc.)
- BCrypt password hashing (via Spring Security's `PasswordEncoder`)
- Lombok

## What's implemented so far
- **User** — registration, profile lookup, profile update (full-replace), BCrypt password hashing
- **Product** — CRUD, category filtering, soft-delete via an `active` flag (products are
  never hard-deleted, to preserve order history integrity), duplicate name+category prevention, write operations (create/update/delete) restricted to ADMIN role
- **CartItem** — add/view/update/delete, upsert-on-add (adding an existing product updates
  its quantity rather than duplicating), unique constraint on (user, product), ownership checks on update/delete
- **Order / OrderItem** — checkout flow that validates cart contents (active + in-stock)
  before creating an order, snapshots price at time of purchase, decrements product stock,
  clears the cart on success, supports order cancellation (restores stock), and order history, ownership checks on update/cancel/view
- **Exceptions** - Centralized exception handling (`@ControllerAdvice`) with consistent JSON error responses
  across all of the above
- **Security** — JWT-based stateless authentication, login endpoint, custom filter validating tokens and populating the authenticated principal (id, email, role), role-based access via @PreAuthorize, expired/invalid tokens handled at the filter level with proper 401 responses

## Not yet implemented
- React frontend
- Deployment

## Setup
1. Install MySQL and create a database (see `application.properties` for the expected name)
2. Copy your local DB credentials into `application.properties` (or set them as environment
   variables, per whatever local setup you're using — do not commit real credentials)
3. Run the Spring Boot application
4. Since there's no frontend yet, test via Postman/Insomnia against `http://localhost:8080`
5. 5. Register a user via /api/v1/user/register, then log in via /api/v1/auth/login to get a JWT for testing protected endpoints (send it as Authorization: Bearer <token>)

## Endpoints (implemented so far)
- `POST /api/v1/auth/login`
- `POST /api/v1/user/register`, `GET /api/v1/user/profile`, `PUT /api/v1/user`
- `GET /api/v1/products`, `GET /api/v1/products?category=...`, `GET /api/v1/products/{id}`, `POST /api/v1/products` (admin), `PUT /api/v1/products/{id}` (admin), `DELETE /api/v1/products/{id}` (admin)
- `POST /api/v1/cart`, `GET /api/v1/cart`, `PUT /api/v1/cart/{id}`, `DELETE /api/v1/cart/{id}`
- `POST /api/v1/orders`, `GET /api/v1/orders`, `GET /api/v1/orders/{id}`, `PUT /api/v1/orders/{id}`, `PUT /api/v1/orders/{id}/cancel`


## Branch note
This work lives on the `rebuild` branch. `main` holds the original v1 (Servlet/JSP) version,
left untouched for comparison.
