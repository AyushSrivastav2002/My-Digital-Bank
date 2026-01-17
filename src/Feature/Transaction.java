package Feature;

import java.time.LocalDateTime;

public class Transaction  {
    //Fields in Transaction class
    private String id;
    private Type type;
    private String accountNumber;
    private Double amount;
    private LocalDateTime timestamp; //Inbuilt class of Java which shows the Timestamp
    private String note;

    //Constructor of above class so that its object can be constructed later
    public Transaction(String accountNumber, Double amount, String id, String note, LocalDateTime timestamp, Type type) {
        this.accountNumber = accountNumber;
        this.id=id;
        this. type=type;//InBuild class in java used to show the fixed thing called Enum.
        this.amount= amount;
        this.timestamp=timestamp;
        this.note=note;
    }
       //Getter and Setter
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
