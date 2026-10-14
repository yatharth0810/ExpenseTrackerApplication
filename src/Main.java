import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        ExpenseManager manager = new ExpenseManager();

        while (true) {

            System.out.println("\n================================");
            System.out.println("       EXPENSE TRACKER");
            System.out.println("================================");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Total Spending");
            System.out.println("6. Category-wise Spending");
            System.out.println("7. Exit");
            System.out.println("================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addExpense(manager);
                    break;

                case 2:
                    manager.viewExpenses();
                    break;

                case 3:
                    updateExpense(manager);
                    break;

                case 4:
                    deleteExpense(manager);
                    break;

                case 5:
                    showTotal(manager);
                    break;

                case 6:
                    manager.categoryWiseSpending();
                    break;

                case 7:
                    System.out.println(
                            "Thank you for using Expense Tracker!"
                    );
                    sc.close();
                    return;

                default:
                    System.out.println(
                            "Invalid choice! Enter 1-7."
                    );
            }
        }
    }

    // Add Expense
    private static void addExpense(ExpenseManager manager) {

        System.out.println("\n--------- ADD EXPENSE ---------");

        double amount;

        while (true) {

            amount = readDouble("Enter amount: ₹");

            if (amount > 0) {
                break;
            }

            System.out.println(
                    "Amount must be greater than 0."
            );
        }

        String category;

        while (true) {

            System.out.print("Enter category: ");

            category = sc.nextLine().trim();

            if (!category.isEmpty()) {
                break;
            }

            System.out.println(
                    "Category cannot be empty."
            );
        }

        LocalDate date = readDate();

        System.out.print("Enter description: ");

        String description = sc.nextLine();

        manager.addExpense(
                amount,
                category,
                date,
                description
        );
    }

    // Update Expense
    private static void updateExpense(
            ExpenseManager manager) {

        System.out.println("\n--------- UPDATE EXPENSE ---------");

        int id = readInt("Enter Expense ID: ");

        Expense oldExpense = manager.findExpense(id);

        if (oldExpense == null) {

            System.out.println("Expense not found.");
            return;
        }

        System.out.println("\nCurrent Expense:");

        oldExpense.displayExpense();

        double amount;

        while (true) {

            amount = readDouble("Enter new amount: ₹");

            if (amount > 0) {
                break;
            }

            System.out.println(
                    "Amount must be greater than 0."
            );
        }

        System.out.print("Enter new category: ");

        String category = sc.nextLine();

        LocalDate date = readDate();

        System.out.print("Enter new description: ");

        String description = sc.nextLine();

        boolean updated =
                manager.updateExpense(
                        id,
                        amount,
                        category,
                        date,
                        description
                );

        if (updated) {

            System.out.println(
                    "Expense updated successfully!"
            );

        } else {

            System.out.println(
                    "Expense could not be updated."
            );
        }
    }

    // Delete Expense
    private static void deleteExpense(
            ExpenseManager manager) {

        System.out.println("\n--------- DELETE EXPENSE ---------");

        int id = readInt("Enter Expense ID: ");

        Expense expense = manager.findExpense(id);

        if (expense == null) {

            System.out.println("Expense not found.");
            return;
        }

        expense.displayExpense();

        System.out.print(
                "Are you sure you want to delete? (y/n): "
        );

        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("y")) {

            boolean deleted =
                    manager.deleteExpense(id);

            if (deleted) {

                System.out.println(
                        "Expense deleted successfully!"
                );
            }

        } else {

            System.out.println("Delete cancelled.");
        }
    }

    // Show total spending
    private static void showTotal(
            ExpenseManager manager) {

        double total =
                manager.getTotalSpending();

        System.out.println(
                "\n--------- TOTAL SPENDING ---------"
        );

        System.out.printf(
                "Total Spending: ₹%.2f%n",
                total
        );
    }

    // Read integer
    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        sc.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    // Read double
    private static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        sc.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid amount."
                );
            }
        }
    }

    // Read date
    private static LocalDate readDate() {

        while (true) {

            try {

                System.out.print(
                        "Enter date (YYYY-MM-DD): "
                );

                return LocalDate.parse(
                        sc.nextLine()
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date format!"
                );

                System.out.println(
                        "Example: 2026-10-09"
                );
            }
        }
    }
}