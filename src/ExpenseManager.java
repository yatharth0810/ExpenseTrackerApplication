import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ExpenseManager {

    private ArrayList<Expense> expenses;

    private final String FILE_NAME = "expenses.txt";

    // Constructor
    public ExpenseManager() {

        expenses = new ArrayList<>();

        loadExpenses();
    }

    // Generate new ID
    private int getNextId() {

        int maxId = 0;

        for (Expense e : expenses) {

            if (e.getId() > maxId) {
                maxId = e.getId();
            }
        }

        return maxId + 1;
    }

    // Add Expense
    public void addExpense(double amount,
                           String category,
                           LocalDate date,
                           String description) {

        int id = getNextId();

        Expense expense = new Expense(
                id,
                amount,
                category,
                date,
                description
        );

        expenses.add(expense);

        saveExpenses();

        System.out.println("Expense added successfully!");
        System.out.println("Expense ID: " + id);
    }

    // View all expenses
    public void viewExpenses() {

        if (expenses.isEmpty()) {

            System.out.println("No expenses found.");
            return;
        }

        System.out.println("\n---------------- ALL EXPENSES ----------------");

        System.out.printf(
                "%-5s %-12s %-15s %-15s %-25s%n",
                "ID",
                "Amount",
                "Category",
                "Date",
                "Description"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Expense e : expenses) {

            e.displayExpense();
        }
    }

    // Find expense by ID
    public Expense findExpense(int id) {

        for (Expense e : expenses) {

            if (e.getId() == id) {

                return e;
            }
        }

        return null;
    }

    // Update Expense
    public boolean updateExpense(int id,
                                 double amount,
                                 String category,
                                 LocalDate date,
                                 String description) {

        Expense expense = findExpense(id);

        if (expense == null) {

            return false;
        }

        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(date);
        expense.setDescription(description);

        saveExpenses();

        return true;
    }

    // Delete Expense
    public boolean deleteExpense(int id) {

        Expense expense = findExpense(id);

        if (expense == null) {

            return false;
        }

        expenses.remove(expense);

        saveExpenses();

        return true;
    }

    // Calculate total spending
    public double getTotalSpending() {

        double total = 0;

        for (Expense e : expenses) {

            total += e.getAmount();
        }

        return total;
    }

    // Category-wise spending
    public void categoryWiseSpending() {

        if (expenses.isEmpty()) {

            System.out.println("No expenses found.");
            return;
        }

        HashMap<String, Double> categoryTotal =
                new HashMap<>();

        for (Expense e : expenses) {

            String category = e.getCategory();

            double currentAmount =
                    categoryTotal.getOrDefault(category, 0.0);

            categoryTotal.put(
                    category,
                    currentAmount + e.getAmount()
            );
        }

        System.out.println("\n--------- CATEGORY-WISE SPENDING ---------");

        for (Map.Entry<String, Double> entry :
                categoryTotal.entrySet()) {

            System.out.printf(
                    "%-20s ₹%.2f%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }

    // Save expenses to file
    private void saveExpenses() {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME)
                    );

            for (Expense e : expenses) {

                writer.write(e.toString());
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving expenses: "
                            + e.getMessage()
            );
        }
    }

    // Load expenses from file
    private void loadExpenses() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                try {

                    String[] data = line.split("\\|");

                    int id =
                            Integer.parseInt(data[0]);

                    double amount =
                            Double.parseDouble(data[1]);

                    String category = data[2];

                    LocalDate date =
                            LocalDate.parse(data[3]);

                    String description = data[4];

                    Expense expense =
                            new Expense(
                                    id,
                                    amount,
                                    category,
                                    date,
                                    description
                            );

                    expenses.add(expense);

                } catch (Exception e) {

                    System.out.println(
                            "Invalid record found in file."
                    );
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error loading expenses: " + e.getMessage()
            );
        }
    }
}