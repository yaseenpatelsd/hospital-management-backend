<h1>🏥 Hospital Management System – Backend</h1>

<p>
A <b>production-ready hospital management backend</b> built with <b>Spring Boot</b>,
designed to model <b>real-world healthcare workflows</b>, enforce
<b>strict security</b>, and support <b>cloud-native deployment</b>.
</p>

<p>
This project goes beyond basic CRUD APIs and focuses on
<b>role-based access control</b>, <b>transactional integrity</b>,
and <b>domain-driven business logic</b> similar to systems used in real hospitals.
</p>

<hr>

<h2>🎯 Project Objectives</h2>
<ul>
  <li>Model a real-world hospital backend system</li>
  <li>Implement secure, role-based authorization</li>
  <li>Enforce strict data ownership and access rules</li>
  <li>Support complete user onboarding and lifecycle</li>
  <li>Ensure transactional safety for billing workflows</li>
  <li>Deploy using scalable cloud architecture</li>
</ul>

<hr>

<h2>🧠 Core Features</h2>
<ul>
  <li>Multi-role system: Admin, Doctor, Staff, Patient</li>
  <li>JWT-based authentication and authorization</li>
  <li>Email OTP verification during onboarding</li>
  <li>Role-specific profiles and business logic</li>
  <li>Transaction-safe billing and payment handling</li>
  <li>DTO-based API design for entity protection</li>
  <li>Centralized validation and exception handling</li>
  <li>Cloud deployment with managed external database</li>
</ul>

<hr>

<h2>☁️ Cloud Deployment Architecture</h2>

<h3>🖥️ Backend</h3>
<ul>
  <li>Spring Boot application deployed on <b>AWS EC2</b></li>
  <li>Runs as a standalone backend service</li>
  <li>Inbound traffic controlled via EC2 Security Groups</li>
</ul>

<h3>🗄️ Database</h3>
<ul>
  <li><b>Aiven MySQL</b> (managed cloud database)</li>
  <li>Secure external connection from EC2</li>
  <li>Database lifecycle independent of application</li>
  <li>Automated backups and maintenance</li>
</ul>

<h3>🔐 Security</h3>
<ul>
  <li>Environment-based configuration for sensitive credentials</li>
  <li>Stateless JWT-based authentication</li>
  <li>Strict role and ownership validation at API level</li>
</ul>

<hr>

<h2>🔑 Authentication & User Onboarding Flow</h2>

<h3>1️⃣ Registration</h3>
<ul>
  <li>All users register as a base <b>User</b></li>
  <li>Role assigned via role-specific registration endpoints</li>
  <li>Account remains <b>inactive</b> after registration</li>
</ul>

<h3>2️⃣ Email OTP Verification</h3>
<ul>
  <li>OTP sent to registered email address</li>
  <li>Account activated only after verification</li>
  <li>Unverified users cannot log in</li>
</ul>

<h3>3️⃣ Login & JWT Token</h3>
<ul>
  <li>Verified users authenticate using credentials</li>
  <li>JWT token generated upon successful login</li>
  <li>Token required for all secured endpoints</li>
</ul>

<h3>4️⃣ Role-Based Profile Creation</h3>
<ul>
  <li>Each user creates a role-specific profile</li>
  <li>Doctor → Doctor Profile</li>
  <li>Staff → Staff Profile</li>
  <li>Patient → Patient Profile</li>
  <li>All business logic operates on profile data</li>
</ul>

<hr>

<h2>🔐 Security & Authorization</h2>
<ul>
  <li>Spring Security with JWT authentication</li>
  <li>Role-based authorization using real permissions</li>
  <li>JWT validated before request processing</li>
  <li>Strict ownership checks prevent data leakage</li>
</ul>

<hr>

<h2>🛡️ User Roles & Capabilities</h2>

<h3>👑 ADMIN</h3>
<ul>
  <li>Approve doctor accounts</li>
  <li>Approve staff accounts</li>
  <li>Restrict access for unapproved users</li>
</ul>

<h3>🧑‍⚕️ DOCTOR</h3>
<ul>
  <li>View assigned appointments</li>
  <li>Cancel appointments</li>
  <li>Check medicine availability</li>
  <li>Create prescriptions</li>
  <li>Mark prescriptions as <b>Completed</b> or <b>Invalid</b></li>
</ul>

<h3>🧑‍💼 STAFF</h3>
<ul>
  <li>Confirm appointments</li>
  <li>Assign doctors</li>
  <li>Manage medicines and stock</li>
  <li>Generate bills</li>
  <li>Mark bills as <b>Paid</b></li>
</ul>

<h3>🧑‍🤝‍🧑 PATIENT</h3>
<ul>
  <li>Book and cancel appointments</li>
  <li>View personal prescriptions</li>
  <li>View personal bills</li>
</ul>

<hr>

<h2>🔒 Role-Based Endpoint Access</h2>

<table border="1" cellpadding="8" cellspacing="0">
  <thead>
    <tr>
      <th>Endpoint</th>
      <th>Allowed Roles</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>/admin/**</td><td>ADMIN</td></tr>
    <tr><td>/appointment/book</td><td>PATIENT</td></tr>
    <tr><td>/appointment/assign</td><td>STAFF</td></tr>
    <tr><td>/prescription/**</td><td>DOCTOR</td></tr>
    <tr><td>/bill/**</td><td>STAFF, ADMIN</td></tr>
    <tr><td>/medicine/**</td><td>STAFF, DOCTOR</td></tr>
  </tbody>
</table>

<hr>

<h2>📘 API Documentation</h2>

<h3>🧭 Swagger (OpenAPI)</h3>
<ul>
  <li>Auto-generated API documentation using <b>Springdoc OpenAPI</b></li>
  <li>Interactive UI for testing endpoints</li>
  <li>Supports JWT authentication via Authorization header</li>
</ul>

<p>
<b>Swagger UI:</b><br>
<code>http://localhost:8081/swagger-ui.html</code>
</p>

<h3>📮 Postman Collection</h3>
<ul>
  <li>Complete Postman collection covering all major APIs</li>
  <li>Includes authentication and role-based workflows</li>
  <li>Useful for manual testing and backend validation</li>
</ul>

<p>
<b>Postman Collection:</b><br>
<code>Hospital-Management.postman_collection.json</code>
</p>

<p>
<b>Recommended:</b> Use <b>Swagger</b> for exploration and <b>Postman</b> for end-to-end testing.
</p>

<hr>

<h2>✅ Reliability & Data Safety</h2>

<h3>Validation</h3>
<ul>
  <li>Jakarta Validation annotations (@NotNull, @NotBlank, @Size)</li>
</ul>

<h3>Exception Handling</h3>
<ul>
  <li>Centralized exception handling using <b>@ControllerAdvice</b></li>
  <li>Custom business exceptions for consistent API responses</li>
</ul>

<h3>Transaction Management</h3>
<ul>
  <li>Billing and payment flows are transactional</li>
  <li>Prevents partial updates</li>
  <li>Ensures data consistency</li>
</ul>

<hr>

<h2>🔁 Sample End-to-End Workflow</h2>
<ol>
  <li>Patient registers and verifies OTP</li>
  <li>Patient logs in and books an appointment</li>
  <li>Staff assigns a doctor</li>
  <li>Doctor creates a prescription</li>
  <li>Staff generates a bill</li>
  <li>Patient views and pays the bill</li>
</ol>

<hr>

<h2>⚡ Local Setup</h2>
<ol>
  <li>Clone the repository</li>
  <li>Configure environment variables (DB, Mail, JWT)</li>
  <li>Start the application</li>
  <li>Open Swagger UI</li>
  <li>Register → Verify OTP → Login</li>
  <li>Use JWT token to access secured APIs</li>
</ol>

<hr>

<h2>🚀 Why This Project Stands Out</h2>
<ul>
  <li>Realistic hospital workflows</li>
  <li>Production-grade security design</li>
  <li>Cloud deployment on AWS EC2</li>
  <li>Managed database with Aiven MySQL</li>
  <li>Clean, scalable, maintainable architecture</li>
</ul>
