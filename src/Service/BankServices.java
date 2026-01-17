package Service;

import Feature.Account;
import Feature.Transaction;

import java.util.List;

public interface BankServices {
    String openAccount(String name, String email, String accountType);//Accept the data which is collected from the console
    List<Account>listAccounts();

    //Create deposit method
    void deposit(String accountNumber, Double amount, String note);
    //Create withdraw method
    void withdraw(String accountNumber, Double amount, String withdrawal);
    //Create transfer method
    void transfer(String from, String to, Double amount, String transfer);
    //Create getStatement method
    List<Transaction> getStatement(String account);
    //Create searchAccountsByCustomerName method
    List<Account> searchAccountsByCustomerName(String q);


}

