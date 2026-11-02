# SE331 Component-Based Software Development
## Backend part (Spring boot, Supabase)

---

## 📖 Overview

This repository represents the full backend development sequence for **SE331 Component-Based Software Development** spanning **Labs 06 through 10**. Over this progression, the system transitioned from a simple in-memory Spring Boot REST service into an enterprise-grade backend built with **Spring Boot**, **Spring Data JPA**, **PostgreSQL**, **MapStruct**, **AWS S3 / Supabase Cloud Object Storage**, and secured via **Spring Security 6** with **Stateless JWT (JSON Web Token) Authentication** and **Role-Based Access Control (RBAC)**.

---

## 🧠 What I've Learned (Labs 06 – 10)

### 🔹 Lab 06: Introduction to Spring Boot & Layered Architecture
*Goal: Understand the core architecture of Spring Boot, Dependency Injection, and building standard RESTful APIs.*

- **Layered Architecture & Separation of Concerns**:
  - Structured the backend into distinct architectural tiers:
    - **Controller Layer (`@RestController`)**: Handles incoming HTTP requests, input validation, status codes, and HTTP responses.
    - **Service Layer (`@Service`)**: Encapsulates business logic and orchestration.
    - **Data Access Layer (`@Repository` / DAO)**: Manages low-level data access and persistence abstraction.
    - **Entity Layer (`entity`)**: Represents domain models using Lombok (`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`).
- **Spring Inversion of Control (IoC) & Dependency Injection (DI)**:
  - Leveraged constructor-based dependency injection via `@RequiredArgsConstructor` to decouple classes and facilitate testability.
- **RESTful Endpoints & HTTP Protocols**:
  - Designed endpoints for `Event` and `Organizer` resources (`/events`, `/events/{id}`, `/organizers`).
  - Handled `@GetMapping`, `@PostMapping`, path variables (`@PathVariable`), and query strings (`@RequestParam` for `_limit` and `_page`).
  - Implemented pagination response headers (injecting `x-total-count` into `ResponseEntity.ok().headers(...)`).
  - Handled errors and non-existent entities gracefully using `ResponseStatusException` (`HttpStatus.NOT_FOUND`).
- **In-Memory Data Simulation**:
  - Implemented `EventDaoImpl` and `OrganizerDaoImpl` using thread-safe mock ArrayList storage before introducing a real database.
- **Cross-Origin Resource Sharing (CORS)**:
  - Resolved browser CORS restrictions to enable communication with the Vue 3 frontend using `@CrossOrigin(origins = "http://localhost:5173")` and centralized CORS policies.

---

### 🔹 Lab 07: Spring Data JPA, Relational Databases & Server-Side Pagination
*Goal: Transition from volatile in-memory storage to persistent relational databases using Spring Data JPA and Hibernate.*

- **Database Integration with Docker & PostgreSQL**:
  - Deployed PostgreSQL 16 and Adminer containers using Docker Compose (`docker-compose.yml`).
  - Configured database credentials, JDBC connection pool (`jdbc:postgresql://localhost:5432/selabdb`), and Hibernate schema generation (`spring.jpa.hibernate.ddl-auto: create`) in `application.yml`.
- **JPA Entity Mapping**:
  - Mapped Java POJOs to relational tables using `@Entity`, `@Table`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`, and `@Column`.
- **Spring Data JPA Repositories**:
  - Built repository interfaces (`EventRepository`, `OrganizerRepository`) extending `JpaRepository<T, ID>`, gaining automated CRUD and pagination methods out of the box without boilerplate SQL.
- **Database DAO Implementation & Polymorphic Swapping**:
  - Developed `EventDaoDbImpl` and `OrganizerDaoDbImpl` implementing `EventDao` and `OrganizerDao`.
  - Swapped between mock in-memory DAOs and database DAOs seamlessly through Spring's component scanning and profiles (`@Profile("db")`).
- **Server-Side Pagination**:
  - Utilized `PageRequest.of(page - 1, pageSize)` with `Page<Event>` to execute optimized SQL `LIMIT` and `OFFSET` queries at the database tier.
  - Calculated total pages dynamically using `page.getTotalElements()` for response headers.
- **Automated Data Seeding**:
  - Implemented `InitApp` with `ApplicationListener<ApplicationReadyEvent>` to automatically seed initial categories, events, and organizers on application boot.

---

### 🔹 Lab 08: Entity Relationships, DTO Pattern & MapStruct
*Goal: Model complex relational associations, eliminate circular JSON recursion, and map data using compile-time DTOs.*

- **Relational Domain Modeling**:
  - **Many-to-One / One-to-Many**: Modeled `Event` and `Organizer` (`@ManyToOne` in `Event`, `@OneToMany(mappedBy = "organizer")` in `Organizer`).
  - **Many-to-Many**: Modeled `Event` and `Participant` using `@ManyToMany` with a custom join table (`@JoinTable(name = "event_participant", ...)`).
  - Managed entity cascading (`CascadeType`), fetch types (`FetchType.LAZY` vs `FetchType.EAGER`), and orphan removal.
- **Solving the Jackson Serialization Recursion Loop**:
  - Resolved bidirectional circular reference infinite loops (where `Event` references `Organizer` which references `Event`...) by decoupling entities from API responses.
- **Data Transfer Object (DTO) Pattern**:
  - Created granular DTOs tailored for specific API views:
    - `EventDTO`, `EventOrganizerDTO`, `EventParticipantDTO`
    - `OrganizerDTO`, `OrganizerOwnEventsDTO`
    - `ParticipantDTO`, `ParticipantEventHistoryDTO`
  - Safeguarded internal database schemas from external exposure.
- **Compile-Time Object Mapping with MapStruct**:
  - Integrated MapStruct (`@Mapper(componentModel = "spring")` in `LabMapper.java`).
  - Generated lightning-fast, boilerplate-free mapping code at compile-time to convert entities to DTOs and vice-versa.
- **Expanded API Endpoints**:
  - Built `ParticipantController`, `ParticipantService`, and `ParticipantDao` to expose participant history and associations.

---

### 🔹 Lab 09: Advanced JPA Queries, Dynamic Filtering & Domain Modeling (Auctions & Bids)
*Goal: Master Spring Data query creation, multi-attribute filtering, and expand the domain to support auction systems.*

- **Spring Data JPA Derived Query Methods**:
  - Implemented sophisticated query methods using Spring Data naming conventions:
    - Partial string match: `findByTitleContaining(title, pageable)`
    - Case-insensitive search: `findByTitleContainingIgnoreCase(title, pageable)`
    - Multi-attribute `Or` conditions: `findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(...)`
    - Association traversing queries: `findByOrganizer_NameIgnoreCaseContaining(name, pageable)`
    - Composite filtering: Combining title, description, and organizer name searches.
- **Auctions & Bids Domain Modeling**:
  - Designed an independent online auction module featuring `AuctionItem` and `Bid`.
  - Established a `@OneToMany` relationship from `AuctionItem` to `Bid` with bidirectional navigation.
  - Implemented database-level filtering:
    - Search by item description (`findByDescriptionContainingIgnoreCase`)
    - Filter by bid amounts, successful bids, and bid counts.
- **Auction API Layer**:
  - Created `AuctionItemController` exposing `/auctions` and `/auctions/{id}` with pagination and keyword filtering.
  - Defined `AuctionItemDTO` and `AuctionItemBidDTO` to structure serialized response data.
- **Full-Stack Search Integration**:
  - Wired backend query endpoints to the frontend search controls, enabling real-time search across entities.

---

### 🔹 Lab 10: Cloud Object Storage (Supabase S3) & Spring Security with JWT
*Goal: Implement production-ready cloud media uploads and secure the entire API using JWT authentication and RBAC.*

- **Cloud Object Storage with Supabase (S3-Compatible)**:
  - Integrated AWS SDK v2 for Java (`software.amazon.awssdk:s3`) customized for Supabase S3 endpoints and credentials in `S3Config.java`.
  - Built `SupabaseStorageService` and `SupabaseController`:
    - **Multipart Uploads (`/uploadFile`, `/uploadImage`)**: Handled binary streams, generated unique UUID filenames, and stored files in the target Supabase storage bucket.
    - **Presigned URLs (`/presignedUrl`)**: Created time-limited signed URLs for secure direct resource access.
    - **Storage Metadata**: Returned `StorageFileDto` containing public image URLs to associate with uploaded events and user avatars.
- **Spring Security 6 Architecture & Filter Chain**:
  - Implemented `SecurityConfiguration` to establish a stateless security filter chain:
    - Disabled CSRF (stateless token-based architecture).
    - Set session policy to `SessionCreationPolicy.STATELESS`.
    - Defined fine-grained authorization rules:
      - Public endpoints: `permitAll()` for `/api/v1/auth/**`, `/presignedUrl`, and `GET` requests to `/events/**`, `/organizers/**`, `/auctions/**`.
      - Protected endpoints: Requiring `hasRole("ROLE_ADMIN")` for administrative operations (e.g., creating/updating events and organizers).
- **User Authentication & Role-Based Access Control (RBAC)**:
  - Designed `User` entity implementing Spring Security's `UserDetails` interface.
  - Created `Role` enumeration (`ROLE_USER`, `ROLE_ADMIN`).
  - Applied BCrypt password hashing via `PasswordEncoder` (`BCryptPasswordEncoder`).
  - Linked `User` to `Organizer` via `@OneToOne` association.
- **Stateless JWT (JSON Web Token) Implementation**:
  - Integrated JJWT (`io.jsonwebtoken`) to generate, sign, and validate HMAC-SHA256 JWT tokens with custom claims (`roles`, subject).
  - Implemented `JwtAuthenticationFilter` (`OncePerRequestFilter`):
    - Intercepts requests, extracts `Bearer <token>` from the `Authorization` header, validates the signature, and populates the `SecurityContextHolder`.
  - Added registration and authentication endpoints (`/api/v1/auth/register`, `/api/v1/auth/authenticate`) in `AuthenticationController`.
- **Token Persistence & Logout Revocation**:
  - Created `Token` entity with `TokenType.BEARER` to persist active tokens and support revocation/expiry.
  - Implemented `LogoutService` to safely invalidate tokens when users sign out.

---

## 🛠️ Technology Stack Summary

| Component | Technology / Library |
|---|---|
| **Language & Runtime** | [Java 21](https://www.oracle.com/java/) |
| **Framework** | [Spring Boot 4.1.1 / 3.x](https://spring.io/projects/spring-boot) |
| **Web & REST** | `spring-boot-starter-web` |
| **Data & ORM** | `spring-boot-starter-data-jpa` (Hibernate) |
| **Database** | [PostgreSQL 16](https://www.postgresql.org/) (Docker) |
| **Security & Auth** | [Spring Security 6](https://spring.io/projects/spring-security), [JJWT 0.13.0](https://github.com/jwtk/jjwt) |
| **Cloud Storage** | [Supabase Storage](https://supabase.com/storage) via [AWS SDK v2 (S3)](https://aws.amazon.com/sdk-for-java/) |
| **Object Mapping** | [MapStruct 1.6.3](https://mapstruct.org/) |
| **Boilerplate Reduction**| [Lombok](https://projectlombok.org/) |
| **Containerization** | [Docker & Docker Compose](https://www.docker.com/) |

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK 21)**
- **Docker Desktop** (or Docker Engine)
- Maven (or use the included `./mvnw` wrapper)

---

### 1. Start Database Container
Launch the PostgreSQL database and Adminer web interface using Docker Compose:
```sh
docker compose up -d
```
- **PostgreSQL**: `localhost:5432` (Database: `selabdb`, Username: `postgres`, Password: `password`)
- **Adminer Web GUI**: `http://localhost:9000`

---

### 2. Configure Cloud Storage & Environment Variables
In `src/main/resources/application.yml` (or via environment variables), ensure the database and Supabase S3 properties are configured:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/selabdb
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: create
```

---

### 3. Run the Backend Application
Run using the Maven wrapper:
- **Windows (PowerShell / Command Prompt)**:
  ```sh
  .\mvnw.cmd spring-boot:run
  ```
- **Linux / macOS**:
  ```sh
  ./mvnw spring-boot:run
  ```
The service will start on port `8080` (accessible at `http://localhost:8080`).

---

### 4. Default Seed Users

On initial startup, `InitApp` populates default accounts with pre-hashed BCrypt passwords:

| Username | Password | Roles | Status |
|---|---|---|---|
| `admin` | `admin` | `ROLE_USER`, `ROLE_ADMIN` | Enabled |
| `user` | `user` | `ROLE_USER` | Enabled |
| `disableUser` | `disableUser` | `ROLE_USER` | Disabled |

---

## 📡 Key API Endpoints

### 🔐 Authentication (`/api/v1/auth`)
- `POST /api/v1/auth/register` — Register a new account
- `POST /api/v1/auth/authenticate` — Sign in and obtain a JWT bearer token

### 📅 Events (`/events`)
- `GET /events?_page=1&_limit=3` — Paginated list of events
- `GET /events/{id}` — Get event details with organizer & participants
- `POST /events` — Create a new event (*Requires `ROLE_ADMIN`*)

### 🏢 Organizers (`/organizers`)
- `GET /organizers` — List organizers
- `GET /organizers/{id}` — Get organizer details and owned events
- `POST /organizers` — Create organizer (*Requires `ROLE_ADMIN`*)

### 🔨 Auctions & Bids (`/auctions`)
- `GET /auctions?description=...` — Search & list auction items
- `GET /auctions/{id}` — Get auction item and bid history

### ☁️ Supabase Cloud Storage
- `POST /uploadImage` — Upload multipart image file to Supabase S3 bucket
- `GET /presignedUrl?fileName=...` — Generate a presigned S3 download URL

---

## 📁 Project Structure

```text
331-Lab10-Supabase/
├── src/
│   ├── main/
│   │   ├── java/se331/lab/
│   │   │   ├── config/             # App initialization & startup seeding (InitApp)
│   │   │   ├── controller/         # REST Controllers (Event, Organizer, Participant, Supabase)
│   │   │   ├── dao/                # Data Access Objects (DB & Mock implementations)
│   │   │   ├── entity/             # JPA Entities & DTOs (Event, Organizer, Participant, Auction)
│   │   │   ├── repository/         # Spring Data JPA Repositories
│   │   │   ├── security/           # Spring Security 6 & JWT Infrastructure
│   │   │   │   ├── auth/           # Auth controllers, login/register request & response DTOs
│   │   │   │   ├── config/         # SecurityFilterChain, JWT filter, ApplicationConfig
│   │   │   │   ├── token/          # Token entity, DAO, and revocation service
│   │   │   │   └── user/           # User entity, UserDetails implementation, Role enum
│   │   │   ├── service/            # Business logic service implementations
│   │   │   └── util/               # MapStruct mapper (LabMapper), S3Config, SupabaseStorageService
│   │   └── resources/
│   │       └── application.yml     # Database & application properties
│   └── test/                       # Unit and integration test suites
├── docker-compose.yml              # PostgreSQL & Adminer container definition
├── mvnw / mvnw.cmd                 # Maven wrapper scripts
└── pom.xml                         # Maven dependencies & build configurations
```
