Perfect! Here's your **exact same content** but with beautiful formatting and visual enhancements:

***

# 🏥 **Hospital Management System – Backend API**

<div align="center">
  <img src="https://img.shields.io/badge/Java-21-brightgreen?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-Production-orange?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/JWT%20Security-blue?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-Database-brightblue?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/Swagger-Docs-yellow?style=for-the-badge&logo=swagger&logoColor=black">
</div>

***

## 🚀 **Tech Stack**
```
Java 21 • Spring Boot • Spring Security + JWT 
Hibernate/JPA • MySQL • Swagger (OpenAPI) 
Postman • Maven
```

***

## ✨ **Features**

### 👤 **User Module**
```
✅ User registration and login
✅ OTP verification  
✅ Password reset
✅ JWT authentication
```

### 🧑‍⚕️ **Doctor Module**
```
✅ Doctor profile management
✅ Duty status (Available/Busy)
✅ View assigned appointments
✅ Create/update prescriptions
```

### 🧑‍💼 **Staff Module**
```
✅ Staff registration/verification
✅ Assign doctors to appointments
✅ Generate bills
✅ Manage appointments
```

### 🧑‍🤝‍🧑 **Patient Module**
```
✅ Create patient profile
✅ Book/cancel appointment
✅ View appointment status
```

### 💊 **Medicine Module**
```
✅ Add new medicine
✅ Update medicine stock
✅ Fetch medicine details
```

### 📄 **Prescription Module**
```
✅ Create prescription
✅ Mark completed/invalid
✅ Linked with appointments/medicines
```

### 💰 **Billing Module**
```
✅ Generate bill
✅ Prevent duplicate billing
✅ Process payment
✅ Generate invoice data
```

***

## 🔐 **Security & Authentication**

### **🔑 JWT Authentication Flow**
```
User logs in → Server validates → 
JWT token generated → Token in response → 
Client sends token with each request
```

**Authorization header:**
```http
Authorization: Bearer <JWT_TOKEN>
```

**JWT Filter:** Validates token on every secured request ❌ Rejects invalid/expired tokens

***

## 🛡️ **Role-Based Authorization**

| **Role** | **Permissions** |
|----------|-----------------|
| **ADMIN** | Verify doctors/staff |
| **STAFF** | Billing, Doctor assignment |
| **DOCTOR** | Prescriptions, View appointments |
| **PATIENT** | Book appointments |

**🔒 Secured Endpoints:**
```
 /bill/** → STAFF, ADMIN
/prescription/** → DOCTOR  
/appointment/book → PATIENT
```

***

## ✅ **Request Validation**
**Jakarta Validation (@Valid):**
```
@NotNull @NotBlank @Email @Size
→ HTTP 400 + Clear messages
```

### **🚨 Global Exception Handling**
**@ControllerAdvice handles:**
```
ResourceNotFoundException
DuplicateResourceException  
UnauthorizedAccessException
InvalidRequestException
```

**📌 Standard Error Response:**
```json
{
  "timestamp": "2026-01-25T12:30:45",
  "status": 400,
  "error": "Bad Request",
  "message": "Appointment already exists",
  "path": "/appointment/book"
}
```

***

## 🔄 **Transaction Management**
```
✅ Billing/payment operations transactional
✅ Prevents partial updates
✅ Ensures data integrity
```

***

## 📂 **Project Structure**
```
hospital-management-system/
├── controller     ├── service
├── repository     ├── dto  
├── entity         ├── enums
├── exception      ├── security
├── config         └── resources
```

***

## 🧾 **Swagger API Documentation**
```
🌐 http://localhost:8080/swagger-ui/index.html
✅ All APIs documented & testable
```

## 🔥 **Important APIs**

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/appointment/book` | Book appointment |
| `POST` | `/prescription/create/{doctorId}` | Create prescription |
| `POST` | `/bill/staff/create` | Create bill |
| `POST` | `/bill/payment` | Make payment |
| `GET` | `/bill/{billId}` | Get bill |

**🧪 Postman:** `/postman/Hospital_Management_System.postman_collection.json`

***

## ⚙️ **How to Run**

```bash
# 1. Clone
git clone https://github.com/your-username/hospital-management-system.git

# 2. Configure DB (application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=yourpassword

# 3. Run
mvn spring-boot:run

# 4. Swagger UI
http://localhost:8080/swagger-ui/index.html
```

***

## 🔄 **Application Flow**
```
1. User registers → Verifies OTP
2. Admin verifies doctors/staff
3. Patient books appointment
4. Staff assigns doctor
5. Doctor completes → Prescription
6. Bill created → Payment processed
```

***

## 🧠 **Highlights**
```
✅ Clean Architecture
✅ DTO Communication
✅ JWT Role-Based Security
✅ Global Exception Handling
✅ Request Validation
✅ Transaction Management
✅ Swagger Documentation
✅ Real-world workflows
```

***

## 🚀 **Future Enhancements**
```
🔄 Online payment gateway
🔄 Email notifications
🔄 PDF invoice generation
🔄 Admin dashboard
🔄 Frontend (React/Angular)
```

***

## 👨‍💻 **Author**

**Yaseen Patel**  
*Backend Developer*

**Skills:** Java -  Spring Boot  
**Email:** [yaseenpatelsd@gmail.com](mailto:yaseenpatelsd@gmail.com)  
**GitHub:** [yaseenpatelsd](https://github.com/yaseenpatelsd)

***

<div align="center">
  
**⭐ Star the repository**  
**🔄 Fork the project**  
**📢 Share with others**



</div>

***

**All your original content preserved exactly!** Just made it visually stunning 🎨✨
