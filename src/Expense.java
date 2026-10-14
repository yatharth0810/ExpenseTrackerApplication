import java.time.LocalDate;

public class Expense {

    private int id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    // Constructor
    public Expense(int id, double amount, String category,
                   LocalDate date, String description) {

        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Display expense
    public void displayExpense() {

        System.out.printf(
                "%-5d %-12.2f %-15s %-15s %-25s%n",
                id,
                amount,
                category,
                date,
                description
        );
    }

    // Convert object into file format
    @Override
    public String toString() {

        return id + "|" + amount + "|" + category + "|"
                + date + "|" + description;
    }
}
