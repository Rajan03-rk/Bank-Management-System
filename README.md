# Bank Management System

A Java-based banking application that simulates core banking operations such as account creation, deposits, withdrawals, transfers, PIN changes, and viewing transaction history. This system replicates real-world banking workflows with a simple, user-friendly interface.

---

## 📚 Project Description

The Bank Management System manages customer data and transactions in a secure, organized way. It provides:
- **New User Registration:** Collects personal, additional, and account details in a step-by-step process.
- **Account Management:** Automatically generates account numbers and PINs, and allows deposits upon account creation.
- **Banking Operations:** Deposit, Withdraw, Fast Cash, Fund Transfer, PIN Change, Balance Enquiry, and Mini Statement.
- **Authentication:** Uses account number and PIN for secure login.

---

## 🗂 Database Design

The system uses five main tables:

| Table         | Purpose                                  |
|---------------|------------------------------------------|
| `Signup`      | Stores customer personal details.        |
| `Signuptwo`   | Stores additional customer details.      |
| `Signupthree` | Stores account details and facilities.   |
| `Login`       | Handles account login credentials.       |
| `Bank`        | Records transaction history.             |

**Relationships:**  
- `Signup` ↔ `Signuptwo` ↔ `Signupthree`: One-to-one.  
- `Login` ↔ `Bank`: One-to-many (one account can have many transactions).

---

## 🖥 Screens / Features

- **Login Page:** Existing users enter account number and PIN.  
- **Sign-Up Pages:** Three-step process for new users.  
- **Deposit Page:** Enter amount to deposit with confirmation popup.  
- **Withdrawal / Fast Cash Pages:** Allows quick cash withdrawal with balance validation.  
- **Mini Statement Page:** Displays transaction history.  
- **PIN Change Page:** Enables users to reset PIN.  
- **Balance Page:** Displays current account balance.  
- **Transfer Page:** Transfer funds between accounts securely.

---

## ⚙️ How to Run

1. **Database Setup:**  
   - Create a database (MySQL/Oracle/etc.).  
   - Use the schema from the documentation to create the five tables.  

2. **Configure the Project:**  
   - Update the database connection credentials in the project source code.  

3. **Compile and Run:**  
   - Open the project in an IDE (NetBeans / Eclipse / IntelliJ).  
   - Compile and run `Main_Class.java` (ATM Machine).  

4. **Usage:**  
   - **New User:** Click *Sign Up* → Fill details → Deposit amount.  
   - **Existing User:** Enter Account Number + PIN → Access banking features.

---

## 🔐 Security Notes

- PIN numbers are stored as VARCHAR in the database (consider hashing for production).  
- Transaction amounts and dates should ideally be stored in DECIMAL/DATE fields instead of VARCHAR for accuracy.  

---

## 📝 Credits

Developed as a banking simulation project to demonstrate database integration, GUI design, and CRUD operations.

