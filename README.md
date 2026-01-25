<hr>

<h2>🎯 Why This Project?</h2>

<p>
This project was built to simulate a <b>real-world hospital backend system</b>,
focusing on security, role-based access, and real business workflows rather than simple CRUD APIs.
</p>

<ul>
  <li>Multi-role access control (Admin, Doctor, Staff, Patient)</li>
  <li>Secure authentication using JWT</li>
  <li>Account lifecycle with OTP verification</li>
  <li>Strict data ownership (patients access only their data)</li>
  <li>Transactional operations for billing & payment</li>
  <li>Validation and centralized exception handling</li>
</ul>

<p>
The goal was to design a backend that behaves like a <b>production-grade system</b>,
not just a demo application.
</p>

<hr>

<h2>🧠 What This Project Demonstrates</h2>

<ul>
  <li>Spring Security with JWT authentication</li>
  <li>Role-based authorization with real permissions</li>
  <li>Separation of authentication and domain data</li>
  <li>Clean architecture (Controller, Service, Repository)</li>
  <li>DTO-based communication to protect entities</li>
  <li>Global exception handling using <b>@ControllerAdvice</b></li>
  <li>Request validation using Jakarta Validation</li>
  <li>Transaction management for critical operations</li>
  <li>Real-world domain modeling (appointments, prescriptions, billing)</li>
</ul>

<hr>

<h2>🔑 Authentication & User Onboarding Flow</h2>

<p>
This project follows a <b>multi-step authentication and onboarding flow</b>
where a <b>User account acts as a base identity</b>, and all business logic
is driven by role-specific profiles.
</p>

<h3>🧾 Step 1: User Registration</h3>
<ul>
  <li>All users register as a base <b>User</b></li>
  <li>Role (ADMIN / DOCTOR / STAFF / PATIENT) is assigned based on API used</li>
  <li>Account remains <b>inactive</b> initially</li>
</ul>

<h3>📧 Step 2: Email OTP Verification</h3>
<ul>
  <li>OTP is sent to the registered email</li>
  <li>User must verify OTP to activate the account</li>
  <li>Unverified users cannot log in</li>
</ul>

<h3>🔐 Step 3: Login & JWT Generation</h3>
<ul>
  <li>Verified users can log in</li>
  <li>JWT token is generated on successful authentication</li>
  <li>JWT is required for all secured APIs</li>
</ul>

<h3>👤 Step 4: Profile Creation</h3>
<ul>
  <li>Users create a <b>role-specific profile</b></li>
  <li>Doctor → Doctor Profile</li>
  <li>Staff → Staff Profile</li>
  <li>Patient → Patient Profile</li>
  <li>All business logic operates on profile data</li>
</ul>

<hr>

<h2>🔐 Security & Access Control</h2>

<p>
The application uses <b>JWT-based authentication</b> combined with
<b>role-based authorization</b>.
</p>

<p>
JWT tokens are validated by a security filter <b>before allowing access</b>
to secured endpoints.
</p>

<hr>

<h2>🛡️ User Roles & Responsibilities</h2>

<h3>👑 ADMIN</h3>
<ul>
  <li>Approve Doctor accounts</li>
  <li>Approve Staff accounts</li>
  <li>Doctors and staff cannot operate without admin approval</li>
</ul>

<h3>🧑‍⚕️ DOCTOR</h3>
<ul>
  <li>View assigned appointments</li>
  <li>Cancel appointments</li>
  <li>Check medicine stock availability</li>
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

<table>
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
  <li>@NotNull</li>
  <li>@NotBlank</li>
  <li>@Email</li>
  <li>@Size</li>
</ul>

<h3>Global Exception Handling</h3>
<ul>
  <li>ResourceNotFoundException</li>
  <li>DuplicateResourceException</li>
  <li>UnauthorizedAccessException</li>
  <li>InvalidRequestException</li>
</ul>

<h3>Transaction Management</h3>
<ul>
  <li>Billing and payment operations are transactional</li>
  <li>Prevents partial updates</li>
  <li>Ensures data consistency</li>
</ul>

<hr>

<h2>⚡ Quick Start (Local Testing)</h2>

<ol>
  <li>Start the application</li>
  <li>Open Swagger UI</li>
  <li>Register → Verify OTP → Login</li>
  <li>Use JWT to access secured APIs</li>
</ol>

<hr>

<h2>🔁 Sample End-to-End Flow</h2>

<ol>
  <li>Patient registers and logs in</li>
  <li>Patient books appointment</li>
  <li>Staff assigns doctor</li>
  <li>Doctor creates prescription</li>
  <li>Staff generates bill</li>
  <li>Patient views bill and pays</li>
</ol>

<hr>

<h2>🔍 How to Verify This Project</h2>

<ul>
  <li><b>Security</b> → <code>security</code> package</li>
  <li><b>Validation</b> → DTO classes</li>
  <li><b>Exceptions</b> → <code>exception</code> package</li>
  <li><b>Transactions</b> → Billing service layer</li>
</ul>

<hr>

<h2>⚠️ Common Error Scenarios Handled</h2>

<ul>
  <li>Unauthorized API access</li>
  <li>Invalid role access</li>
  <li>Duplicate appointments</li>
  <li>Multiple bills for same appointment</li>
  <li>Accessing another patient’s data</li>
</ul>

<hr>
