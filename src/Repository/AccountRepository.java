package Repository;

import Feature.Account;
import Feature.Customer;

import java.util.*;

public class AccountRepository {
    private final Map<String, Account> accountsByNumber = new HashMap<>();

    public void save(Account account) {
        accountsByNumber.put(account.getAccountNumber(), account);
    }

    //findAll method created
    public List<Account> findAll() {
        return new ArrayList<>(accountsByNumber.values());
    }

    // findByNumber method created
    public Optional<Account> findByNumber(String accountNumber) {
        return Optional.ofNullable(accountsByNumber.get(accountNumber));
    }

    public List<Account> findByCustomerId(String customerId) {
        List<Account> result = new ArrayList<>();
        for (Account a : accountsByNumber.values()) {
            if (a.getCustomerID().equals(customerId))
                result.add(a);
        }
        return result;
    }
}



