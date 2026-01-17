package app;

import Feature.Account;
import Service.BankServices;
import Service.Implementation.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BankServices bankServices= new BankServiceImpl();
        boolean running = true;
        System.out.println("Welcome to Console Bank");
        while (running) {
            //PreFormatted String used to show Multi-line String so we use triple quotes
            System.out.println(""" 
                    1)Open Account
                    2)Deposit
                    3)Withdraw
                    4)Transfer
                    5)Account Statement
                    6)List Accounts
                    7)Search Accounts by Customer Name
                    8)Exit 
                     """);
            System.out.print("CHOOSE YOUR OPTION:");
            String choice = input.nextLine().trim();
            System.out.println("CHOICE:" +choice);

            // To select the Options
            switch (choice) {
                case "1" -> openAccount(input,bankServices);//These are the methods which we built like openAccount,Deposit etc.
                case "2" -> Deposit(input,bankServices);
                case "3" -> Withdraw(input,bankServices);
                case "4" -> Transfer(input,bankServices);
                case "5" -> AccountStatement(input,bankServices);
                case "6" -> ListAccounts(input,bankServices);
                case "7" -> SearchAccounts(input,bankServices);
                case "0" -> running = false;
            }
        }
    }

    private static void openAccount(Scanner input,BankServices bankServices) {
        System.out.println("Customer name:");
        String name=input.nextLine().trim();
        System.out.println("Customer email:");
        String email=input.nextLine().trim();
        System.out.println("Account Type (SAVINGS/CURRENT):");
        String type=input.nextLine().trim();
        System.out.println("Initial deposit (optional,blank for 0):");
        String amountStr=input.nextLine().trim();
        if(amountStr.isBlank()) amountStr="0";
        Double initial= Double.valueOf(amountStr);// Wrapper Class

        String accountNumber=bankServices.openAccount(name,email,type);
        if(initial>0)
            bankServices.deposit(accountNumber,initial,"Initial Deposit");
        System.out.println("Account Opened:"+accountNumber);
    }

    private static void Deposit(Scanner input,BankServices bankServices) {
        System.out.println("Account number:");
        String accountNumber=input.nextLine().trim();
        System.out.println("Amount:");
        Double amount=Double.valueOf(input.nextLine().trim());
        bankServices.deposit(accountNumber,amount,"Deposit");
        System.out.println("Deposited");
    }

    private static void Withdraw(Scanner input,BankServices bankServices) {
        System.out.println("Account number:");
        String accountNumber=input.nextLine().trim();
        System.out.println("Amount:");
        Double amount=Double.valueOf(input.nextLine().trim());
        bankServices.withdraw(accountNumber,amount,"Withdrawal");
        System.out.println("Withdrawn");
        
    }

    private static void Transfer(Scanner input,BankServices bankServices){
        System.out.println("From Account:");
        String from=input.nextLine().trim();
        System.out.println("To Account:");
        String to= input.nextLine().trim();
        System.out.println("Amount:");
        Double amount=Double.valueOf(input.nextLine().trim());
        bankServices.transfer(from,to,amount,"Transfer");
        
    }

    private static void AccountStatement(Scanner input,BankServices bankServices) {
        System.out.println("Account number:");
        String account=input.nextLine().trim();
        bankServices.getStatement(account).forEach(t->{
            System.out.println(t.getTimestamp() +"|"+ t.getType() +"|"+ t.getAmount() +"|"+ t.getNote());
        });
        
    }

    private static void ListAccounts(Scanner input,BankServices bankServices) {
        bankServices.listAccounts().forEach(a->{
            System.out.println(a.getAccountNumber() +"|" + a.getAccountType() +"|"+ a.getBalance());
        });
    }

    private static void SearchAccounts(Scanner input, BankServices bankServices) {
        System.out.println("Customer name contains:");
        String q=input.nextLine().trim();
        bankServices.searchAccountsByCustomerName(q).forEach(account ->
                System.out.println(account.getAccountNumber() +"|"+ account.getAccountType() +"|"+ account.getBalance())
        );
        
    }


}
