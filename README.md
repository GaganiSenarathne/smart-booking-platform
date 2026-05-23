# 🏨 Smart Booking Platform (Spring Boot + JWT + Security)

A backend system for managing users, organizations, resources, and bookings with secure JWT-based authentication and role-based authorization.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Security (JWT Authentication)
- Spring Data JPA
- MySQL
- Maven
- Lombok

---

## 🔐 Features

### Authentication & Authorization
- User registration (`/auth/addNewUser`)
- Login with JWT token (`/auth/generateToken`)
- Stateless authentication (JWT)
- Role-based access control:
    - ROLE_USER
    - ROLE_ADMIN
    - ROLE_STAFF
    - ROLE_CUSTOMER

### User Management
- Create user with encoded password
- Fetch user profile (secured)
- Update / delete users
- Organization mapping

### Booking System
- Create bookings for resources
- View user-specific bookings
- Pagination support
- Status tracking:
    - CREATED
    - CONFIRMED
    - CANCELLED
    - COMPLETED

---

## 📦 API Endpoints

### Auth APIs

- POST /auth/addNewUser
- POST /auth/generateToken
- GET /auth/welcome

### User APIs

- GET /auth/user/profile

### Booking APIs (secured)

- POST /bookings
- GET /bookings/my

---

## 🔑 JWT Flow

1. User registers via `/auth/addNewUser`
2. User logs in via `/auth/generateToken`
3. JWT token is generated
4. Client sends token in every request:

5. Spring Security validates token using `JwtAuthFilter`
6. User context is available via `Authentication`

---

## 🧠 Architecture Overview

Controller → Service → Repository → Database
↓
JWT Filter → Security Context → Authentication

---

## 🗄️ Database Entities

- UserInfo
- Role
- Organization
- Resource
- Booking
- UserRoles (Many-to-Many)

---

## ⚙️ Setup Instructions

### 1. Clone the project

git clone https://github.com/your-repo/smart-booking-platform.git

---

## 🗄️ Database Entities

- UserInfo
- Role
- Organization
- Resource
- Booking
- UserRoles (Many-to-Many)

---

## ⚙️ Setup Instructions

### 1. Clone the project

git clone https://github.com/your-repo/smart-booking-platform.git

spring.datasource.url=jdbc:mysql://localhost:3306/booking_platform_db
spring.datasource.username=root
spring.datasource.password=your_password

### 3. Run project

mvn spring-boot:run


## 🔐 Security Configuration
- Stateless session (JWT-based)
- CSRF disabled
- Role-based endpoint protection
- Custom JWT filter (OncePerRequestFilter)
- Password encryption using BCrypt

## 🧪 Sample Roles Setup

Automatically initialized on startup:

- ROLE_USER
- ROLE_ADMIN
- ROLE_STAFF
- ROLE_CUSTOMER

## 📌 Example JWT Request

### Request
````
POST /auth/generateToken
Content-Type: application/json

{
  "username": "test@gmail.com",
  "password": "123"
}

````

### Response:

````
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
````

### 🛡️ Security Notes

- Passwords are stored in encrypted form (BCrypt)
- JWT expires after 30 minutes
- Stateless authentication (no session stored in server)
- Role-based access enforced at endpoint level

### 📈 Future Improvements

- Refresh token mechanism
- Email verification
- Booking calendar UI
- Admin dashboard
- Audit logs
- Swagger UI documentation

### 📄 License

This project is for educational/demo purposes.

---

### 👨‍💻 Author

Gagani Senarathne