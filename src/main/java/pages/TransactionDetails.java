package pages;

public class TransactionDetails {
    private String transactionId;
    private String date;
    private String description;
    private String type;
    private String amount;

    // Конструктор
    public TransactionDetails(String transactionId, String date, String description, String type, String amount) {
        this.transactionId = transactionId;
        this.date = date;
        this.description = description;
        this.type = type;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "ID='" + transactionId + '\'' +
                ", Date='" + date + '\'' +
                ", Desc='" + description + '\'' +
                ", Type='" + type + '\'' +
                ", Amount='" + amount + '\'' +
                '}';

    }
}
