package Service.Implementation;


import Exceptions.AccountNotFoundException;
import Exceptions.InsufficientFundException;
import Exceptions.ValidationException;
import Feature.Account;
import Feature.Customer;
import Feature.Transaction;
import Feature.Type;
import Repository.AccountRepository;
import Repository.CustomerRepository;
import Repository.TransactionRepository;
import Service.BankServices;
import util.Validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankServices {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository=new TransactionRepository();
    private final CustomerRepository customerRepository=new CustomerRepository();

    //Adding Validations in the form of lemda
    private final Validation<String> validateName=name-> {
        if(name==null || name.isBlank()) throw new ValidationException("Name is required");
    };
    private final Validation<String> validateEmail=email-> {
        if(email==null || !email.contains("@")) throw new ValidationException("Email is required");
    };
    private final Validation<String> validateType=type-> {
        if(type==null || !(type.equalsIgnoreCase("SAVINGS") || type.contains("CURRENT")))
            throw new ValidationException("Type must be SAVINGS or CURRENT");
    };
    private final Validation<Double> validateAmountPositive=amount-> {
        if(amount==null || amount<0)
            throw new ValidationException("Please enter valid amount");
    };


    // Implements openAccount method
    @Override
    public String openAccount(String name, String email, String accountType) {
        validateName.validate(name);
        validateEmail.validate(email);
        validateType.validate(accountType);

        // InBuild class in Java "Universally Unique Identifier",here we use to generate the unique CustomerID--->
        String customerID = UUID.randomUUID().toString();

        //Create customer
        Customer c = new Customer(email, customerID, name);
        customerRepository.save(c);

        //Change Later-->10+1=AC000001
        //String accountNumber =UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber, accountType, (double) 0, customerID);

        //SAVE-->We store the data in the form of Object like Object of Map.
        accountRepository.save(account); // Save is InBuild method in java.
        return accountNumber;
    }

    //Implements ListAccount method
    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    //Implements deposit method
    @Override
    public void deposit(String accountNumber, Double amount, String note) {

        validateAmountPositive.validate(amount);

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found:" + accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.DEPOSIT);
        transactionRepository.add(transaction);
    }

    //Implements withdraw method
    @Override
    public void withdraw(String accountNumber, Double amount, String note) {

        validateAmountPositive.validate(amount);

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found:" + accountNumber));

        if (account.getBalance().compareTo(amount) < 0)
            throw new InsufficientFundException("Insufficient Balance");
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.WITHDRAW);
        transactionRepository.add(transaction);
    }

    //Implements transfer method
    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {

        validateAmountPositive.validate(amount);

        if (fromAcc.equals(toAcc))
            throw new ValidationException("Cannot transfer to your own account");
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found:" + fromAcc));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found:" + toAcc));
        if (from.getBalance().compareTo(amount) < 0)
            throw new InsufficientFundException("Insufficient Balance");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        transactionRepository.add(
                new Transaction(from.getAccountNumber(),
                        amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.TRANSFER_OUT)
        );

        transactionRepository.add(
                new Transaction(to.getAccountNumber(),
                        amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.TRANSFER_IN)
        );

    }

    //Implements getStatement method for account statement
    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }

     //Implements searchAccountsByCustomerName method
    @Override
    public List<Account> searchAccountsByCustomerName(String q) {
        String query=(q==null)? "" :q.toLowerCase();
      //  List<Account> result= new ArrayList<>();
       // for (Customer c:customerRepository.findAll()){
          //  if(c.getName().toLowerCase().contains(query))
           //     result.addAll(accountRepository.findByCustomerID(c.getId()));
        //}
        //result.sort(Comparator.comparing(Account::getAccountNumber));
        //return result;

        //Another method
        return customerRepository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(query))
                .flatMap(c -> accountRepository.findByCustomerId(c.getId()).stream())
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    private String getAccountNumber() {
        int size=accountRepository.findAll().size()+1;
        return String.format("AC%06d",size);
    }
}

