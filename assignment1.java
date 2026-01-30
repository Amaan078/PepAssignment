import java.util.ArrayList;

public class BankManager {
    public static void main(String[] args) {
        ArrayList<BankAccount> accounts = new ArrayList<>();

        SavingsAccount savings = new SavingsAccount("55557468S658", "Alice", 0.02);
        CheckingAccount checking = new CheckingAccount("7684C2059757", "Bob", 500.00);

        accounts.add(savings);
        accounts.add(checking);

        for (BankAccount acc : accounts) {
            System.out.println(acc.getAccountDetails());
        }

        savings.deposit(200.0);
        checking.withdraw(100.0);
        savings.applyInterest();

        System.out.println("\nAfter Transactions:");
        for (BankAccount acc : accounts) {
            System.out.println(acc.getAccountDetails());
        }
    }
}

class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        }
    }

    public String getAccountDetails() {
        return "Account #" + accountNumber + ", Balance: $" + balance;
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolderName, double interestRate) {
        super(accountNumber, accountHolderName);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
    }

    @Override
    public String getAccountDetails() {
        return "Savings Account No: #" + getAccountNumber() + ",Name: " + getaccountHolderName() + ", Balance:$ " + getBalance() + ", Rate: " + (interestRate * 100) + "%";
    }
}

class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderName, double overdraftLimit) {
        super(accountNumber, accountHolderName);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount >= -overdraftLimit) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrew: $" + amount);
        } else {
            System.out.println("Overdraft limit exceeded.");
        }
    }

    @Override
    public String getAccountDetails() {
        return "Checking Account No: #" + getAccountNumber() + ", Balance: $" + getBalance() + ", Limit: $" + overdraftLimit;
    }
}
