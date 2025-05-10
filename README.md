1. **Secure Authentication System**  
   - Implements **JWT-based security**, allowing two distinct roles: `BIDDER` and `APPROVER`.  
   - Tokens must be passed using the **Authorization header** with `Bearer <token>` format.  

2. **Bid Operations**  
   - **Submit Bid**: Lets bidders place a bid on the project.  
   - **Change Bid Status**: Approvers can mark bids as approved or rejected.  
   - **View Bids**: Retrieve all bids above a particular monetary threshold.  
   - **Remove Bid**: Bidders or approvers are authorized to delete bids under specific conditions.  

---

### 🧩 Entity Overview

#### 🔹 `RoleModel`  
| Field     | Type    | Description                          |  
|-----------|---------|--------------------------------------|  
| id        | Integer | Primary key with auto-generation     |  
| rolename  | String  | Role label (e.g., BIDDER, APPROVER)  |  

#### 🔹 `UserModel`  
| Field        | Type    | Description                          |  
|--------------|---------|--------------------------------------|  
| id           | Integer | Auto-generated unique identifier     |  
| username     | String  | Full name of the user                |  
| companyName  | String  | Company the user belongs to          |  
| email        | String  | User's email (must be unique)        |  
| password     | String  | Stored as a secure hashed string     |  
| role         | Integer | Role foreign key (from `RoleModel`)  |  

#### 🔹 `BiddingModel`  
| Field            | Type    | Description                              |  
|------------------|---------|------------------------------------------|  
| id               | Integer | Unique primary key                      |  
| biddingId        | Integer | User-defined bid ID                     |  
| projectName      | String  | Default: "Metro Phase V 2024"           |  
| bidAmount        | Double  | Value of the bid                        |  
| yearsToComplete  | Double  | Estimated time to finish (in years)     |  
| dateOfBidding    | String  | Format: `dd/MM/yyyy`                    |  
| status           | String  | Defaults to `pending`                   |  
| bidderId         | Integer | User ID of bidder (foreign key)         |  

---

### 📡 API Reference

#### 🔐 `POST /login`  
- **Purpose**: Authenticates the user and returns a JWT.  
- **Input Example**:  
  ```json
  { "email": "user@example.com", "password": "password123" }
  ```  
- **Output**: JWT token string.

---

#### 📝 `POST /bidding/add`  
- **Function**: Allows a user with BIDDER role to submit a new bid.  
- **Input**:  
  ```json
  { "biddingId": 1001, "bidAmount": 5000000, "yearsToComplete": 2.5 }
  ```

---

#### 🔍 `GET /bidding/list?bidAmount=1000000`  
- **Description**: Fetches all bids with an amount greater than the specified value.  
- **Output**: List of matching bids.

---

#### 🛠️ `PATCH /bidding/update/{id}`  
- **Purpose**: Used by an approver to update the bid status.  
- **Input**:  
  ```json
  { "status": "approved" }
  ```

---

#### ❌ `DELETE /bidding/delete/{id}`  
- **Function**: Deletes a specific bid.  
- **Permissions**: Only accessible by the creator of the bid or an approver.

---

### ⚙️ Tech Stack

- **Spring Boot** – Backend REST framework  
- **Spring Security** – Authentication and authorization framework  
- **H2** – Embedded in-memory database for development and testing  
- **JWT (JSON Web Tokens)** – Stateless authentication mechanism  
- **Maven** – Project and dependency management  
