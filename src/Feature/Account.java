package Feature;

public class Account {
  // Fields in Account Class
    private String accountNumber;
    private String customerID;
    private Double balance;
    private String accountType;

    // Constructor of the Account Class so that we can construct its object later.
    public Account(String accountNumber,String accountType,Double balance,String customerID) {
        this.accountNumber = accountNumber;
        this.accountType=accountType;
        this.balance=balance;
        this.customerID=customerID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
