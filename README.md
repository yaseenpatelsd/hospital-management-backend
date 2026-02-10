<h1>🏥 Hospital Management System – Backend</h1>

<p>
A <b>production-grade hospital management backend system</b> built using
<b>Spring Boot</b>, designed to model <b>real-world healthcare workflows</b>,
strong <b>security practices</b>, and <b>cloud deployment architecture</b>.
</p>

<p>
This project goes beyond basic CRUD APIs and focuses on
<b>role-based access control</b>, <b>transaction safety</b>,
and <b>real business logic</b>, similar to systems used in real hospitals.
</p>

<hr>

<h2>🎯 Project Goals</h2>
<ul>
  <li>Simulate a real-world hospital backend system</li>
  <li>Implement secure, role-based access control</li>
  <li>Enforce strict data ownership and authorization</li>
  <li>Handle complete user onboarding lifecycle</li>
  <li>Support transactional billing and payment workflows</li>
  <li>Deploy the application in a cloud environment</li>
</ul>

<hr>

<h2>🧠 Key Features</h2>
<ul>
  <li>Multi-role system: Admin, Doctor, Staff, Patient</li>
  <li>JWT-based authentication & authorization</li>
  <li>OTP-based email verification during onboarding</li>
  <li>Role-specific profiles and business logic</li>
  <li>Transaction-safe billing & payment handling</li>
  <li>DTO-based API design for entity protection</li>
  <li>Centralized validation and exception handling</li>
  <li>Cloud deployment with external managed database</li>
</ul>

<hr>

<h2>☁️ Cloud Deployment Architecture</h2>

<h3>🖥️ Backend Hosting</h3>
<ul>
  <li>Spring Boot application deployed on <b>AWS EC2</b></li>
  <li>Runs as a standalone backend service</li>
  <li>Public access controlled via EC2 Security Groups</li>
</ul>

<h3>🗄️ Database</h3>
<ul>
  <li><b>Aiven MySQL</b> (managed cloud database service)</li>
  <li>External database accessed securely from EC2</li>
  <li>Ensures persistence independent of application lifecycle</li>
  <li>Managed backups and maintenance handled by Aiven</li>
</ul>

<h3>🔐 Security</h3>
<ul>
  <li>Environment-based configuration for sensitive credentials</li>
  <li>JWT-based stateless authentication</li>
  <li>Strict role and ownership checks at API level</li>
</ul>

<hr>

<h2>🔑 Authentication & User Onboarding Flow</h2>

<h3>Step 1: User Registration</h3>
<ul>
  <li>All users register as a base <b>User</b></li>
  <li>Role assigned via role-specific registration endpoints</li>
  <li>Account remains <b>inactive</b> after registration</li>
</ul>

<h3>Step 2: Email OTP Verification</h3>
<ul>
  <li>OTP sent to registered email address</li>
  <li>Account activated only after OTP verification</li>
  <li>Unverified users cannot log in</li>
</ul>

<h3>Step 3: Login & JWT Token</h3>
<ul>
  <li>Verified users authenticate using credentials</li>
  <li>JWT token generated upon successful login</li>
  <li>Token required for all secured endpoints</li>
</ul>

<h3>Step 4: Role-Based Profile Creation</h3>
<ul>
  <li>Users create a role-specific profile</li>
  <li>Doctor → Doctor Profile</li>
  <li>Staff → Staff Profile</li>
  <li>Patient → Patient Profile</li>
  <li>All business logic operates on profile data</li>
</ul>

<hr>

<h2>🔐 Security & Access Control</h2>
<ul>
  <li>Spring Security with JWT authentication</li>
  <li>Role-based authorization using real permissions</li>
  <li>JWT validated before request processing</li>
  <li>Strict ownership checks prevent data leakage</li>
</ul>

<hr>

<h2>🛡️ User Roles & Responsibilities</h2>

<h3>👑 ADMIN</h3>
<ul>
  <li>Approve Doctor accounts</li>
  <li>Approve Staff accounts</li>
  <li>Prevent unapproved users from operating</li>
</ul>

<h3>🧑‍⚕️ DOCTOR</h3>
<ul>
  <li>View assigned appointments</li>
  <li>Cancel appointments</li>
  <li>Check medicine stock</li>
  <li>Create prescriptions</li>
  <li>Mark prescriptions as <b>Completed</b> or <b>Invalid</b></li>
</ul>

<h3>🧑‍💼 STAFF</h3>
<ul>
  <li>Confirm appointments</li>
  <li>Assign doctors</li>
  <li>Manage medicines (Add / Update / Stock)</li>
  <li>Generate bills</li>
  <li>Mark bills as <b>Paid</b></li>
</ul>

<h3>🧑‍🤝‍🧑 PATIENT</h3>
<ul>
  <li>Book appointments</li>
  <li>Cancel appointments</li>
  <li>View own prescriptions</li>
  <li>View own bills</li>
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

<h2>✅ Reliability & Data Safety</h2>

<h3>Request Validation</h3>
<ul>
  <li>Jakarta Validation annotations (@NotNull, @NotBlank, @Size)</li>
</ul>

<h3>Global Exception Handling</h3>
<ul>
  <li>Centralized exception handling using <b>@ControllerAdvice</b></li>
  <li>Custom business exceptions for consistent API responses</li>
</ul>

<h3>Transaction Management</h3>
<ul>
  <li>Billing and payment operations are transactional</li>
  <li>Prevents partial updates</li>
  <li>Ensures data consistency</li>
</ul>

<hr>

<h2>🔁 Sample End-to-End Flow</h2>
<ol>
  <li>Patient registers and verifies OTP</li>
  <li>Patient logs in and books an appointment</li>
  <li>Staff assigns a doctor</li>
  <li>Doctor creates a prescription</li>
  <li>Staff generates a bill</li>
  <li>Patient views and pays the bill</li>
</ol>

<hr>

<h2>⚡ Local Setup (Quick Start)</h2>
<ol>
  <li>Clone the repository</li>
  <li>Configure environment variables (DB, mail, JWT)</li>
  <li>Start the application</li>
  <li>Open Swagger UI</li>
  <li>Register → Verify OTP → Login</li>
  <li>Use JWT token to access secured APIs</li>
</ol>

<hr>

<h2>🚀 Why This Project Stands Out</h2>
<ul>
  <li>Models real hospital workflows</li>
  <li>Production-grade security and authorization</li>
  <li>Cloud deployment on AWS EC2</li>
  <li>Managed external database using Aiven MySQL</li>
  <li>Designed for scalability and maintainability</li>
</ul>
