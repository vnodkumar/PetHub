# PetHub — Spring Boot Rebuild (WIP)

This is a ground-up rebuild of PetHub, moving from the original Servlet/JSP/JDBC version
(see the `main` branch) to a proper layered Spring Boot REST API, paired with a React
frontend (not yet started).

**Status: actively in progress.** Backend core business logic is complete and tested via
Postman. Security, CORS, and the frontend are not yet built — there is currently no UI,
and all endpoints are open (no authentication) since Spring Security is only wired in for
password hashing so far.

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
- MySQL
- REST API (`@RestController`) — not Thymeleaf; frontend will be a separate React app
- Bean Validation (`@Valid`, `@NotBlank`, etc.)
- BCrypt password hashing (via Spring Security's `PasswordEncoder`)
- Lombok

## What's implemented so far
- **User** — registration, profile lookup, profile update (full-replace), BCrypt password hashing
- **Product** — CRUD, category filtering, soft-delete via an `active` flag (products are
  never hard-deleted, to preserve order history integrity), duplicate name+category prevention
- **CartItem** — add/view/update/delete, upsert-on-add (adding an existing product updates
  its quantity rather than duplicating), unique constraint on (user, product)
- **Order / OrderItem** — checkout flow that validates cart contents (active + in-stock)
  before creating an order, snapshots price at time of purchase, decrements product stock,
  clears the cart on success, supports order cancellation (restores stock), and order history
- Centralized exception handling (`@ControllerAdvice`) with consistent JSON error responses
  across all of the above

## Not yet implemented
- Spring Security / JWT authentication — currently only a `PasswordEncoder` bean exists;
  all endpoints are open and unauthenticated
- Ownership checks (e.g. a user accessing another user's cart/orders by guessing an id) —
  deferred until Security is added
- CORS configuration
- React frontend
- Deployment

## Setup
1. Install MySQL and create a database (see `application.properties` for the expected name)
2. Copy your local DB credentials into `application.properties` (or set them as environment
   variables, per whatever local setup you're using — do not commit real credentials)
3. Run the Spring Boot application
4. Since there's no frontend yet, test via Postman/Insomnia against `http://localhost:8080`

## Endpoints (implemented so far)
- `POST /api/users`, `GET /api/users/{id}`, `PUT /api/users/{id}`
- `GET /api/products`, `GET /api/products?category=...`, `GET /api/products/{id}`,
  `POST /api/products`, `PUT /api/products/{id}`
- `POST /api/cart`, `GET /api/cart/{userId}`, `PUT /api/cart/{cartItemId}`,
  `DELETE /api/cart/{cartItemId}`
- `POST /api/orders`, `GET /api/orders/user/{userId}`, `GET /api/orders/{id}`,
  `PUT /api/orders/{id}/cancel`, `PUT /api/orders/{id}`

## Branch note
This work lives on the `rebuild` branch. `main` holds the original v1 (Servlet/JSP) version,
left untouched for comparison.
