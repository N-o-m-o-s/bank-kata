# Bank Account Kata

A simple domain‐driven implementation of a bank account, supporting deposits, withdrawals, and printing an account statement.  
No UI, no persistence, no frameworks - just plain Java, Maven & JUnit 5.

---

## Features

- **Deposit** and **Withdrawal** of arbitrary amounts with two‐decimal precision
- **Immutable** `Transaction` records with:
    - Date (`LocalDate`)
    - Type (`DEPOSIT` / `WITHDRAWAL`)
    - Signed amount (`BigDecimal`, scale = 2)
    - Resulting balance (`BigDecimal`, scale = 2)
- **Statement printing** via an interface + console implementation
- **Clock injection** for testable dates
- **Validation** of positive amounts, throwing `InvalidAmountException`

---

## Prerequisites

- Java 11 or higher
- Maven 3.6+

---

## Getting Started

```bash
# Clone the repo
git clone https://github.com/N-o-m-o-s/bank-kata.git
cd bank-kata

# Compile & run all tests
mvn clean test
```
## Project Structure
```
bank-kata
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── org
    │           └── example
    │               └── bank
    │                   ├── domain
    │                   │   ├── BankAccount.java
    │                   │   ├── Transaction.java
    │                   │   └── TransactionType.java
    │                   ├── exception
    │                   │   └── InvalidAmountException.java
    │                   ├── factory
    │                   │   └── TransactionFactory.java
    │                   ├── service
    │                   │   ├── StatementPrinter.java
    │                   │   └── ConsoleStatementPrinter.java
    │                   └── util
    │                       ├── AmountValidator.java
    │                       └── Main.java
    └── test
        └── java
            └── org
                └── example
                    └── bank
                        ├── AmountValidatorTest.java
                        ├── BankAccountTest.java
                        ├── ConsoleStatementPrinterTest.java
                        └── TransactionTest.java
```

## Usage Example
### Code
```java
import org.example.bank.domain.BankAccount;
import org.example.bank.service.ConsoleStatementPrinter;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(); // uses system clock

        account.deposit(new BigDecimal("100.00"));
        account.withdraw(new BigDecimal("30.50"));

        String statement = new ConsoleStatementPrinter()
            .print(account.getTransactions());

        System.out.println(statement);
    }
}
```
### Sample Output
```
DATE       | TYPE       | AMOUNT  | BALANCE
2025-06-24 | DEPOSIT    | +100.00 | 100.00
2025-06-24 | WITHDRAWAL | -30.50  |  69.50
```
