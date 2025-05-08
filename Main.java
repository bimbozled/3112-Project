/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import java.util.Scanner;

/**
 *
 * @author silly
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetManager budgetManager = new BudgetManager();
        boolean running = true;
        
        System.out.println("Welcome to the Budget Manager!");
        System.out.println("\nPlease enter your monthly income: ");
        double monthlyIncome = scanner.nextDouble();
        budgetManager.setMonthlyIncome(monthlyIncome);
        
        while(running){
            displayMenu();
            System.out.println("Enter a choice: "); 
            int choice = scanner.nextInt();
            
            switch(choice){
                case 1:
                    addExpense(scanner, budgetManager);
                    break;
                case 2:
                    addRecurringExpense(scanner, budgetManager);
                    break;
                case 3:
                    adjustRecurringExpenses(budgetManager, scanner);
                    break;
                case 4:
                    viewAllCategories();
                    break;
                case 5:
                    viewAllExpenses(budgetManager);
                    break;
                case 6:
                    stopRecurringExpense(scanner, budgetManager);
                    break;
                case 7:
                    addGoal(budgetManager, scanner);
                    break;
                case 8:
                    adjustGoal(budgetManager, scanner);
                    break;
                case 9:
                    addGoalProgress(budgetManager, scanner);
                    break;
                case 10:
                    viewReport(budgetManager);
                    break;
                case 11:
                    startNewMonth(budgetManager, scanner);
                    break;
                case 12:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
            }
        }
        scanner.close();   
    }   


    private static void displayMenu(){
        System.out.println("\nChoose an option:");
            System.out.println("1. Add an Expense");
            System.out.println("2. Add a Recurring Expense");
            System.out.println("3. Adjust Recurring Expense");
            System.out.println("4. View All Categories");
            System.out.println("5. View All Expenses");
            System.out.println("6. Stop a Recurring Expense");
            System.out.println("7. Add Goal");
            System.out.println("8. Change Goal");
            System.out.println("9. Add Progress to Goal");
            System.out.println("10. View Report");
            System.out.println("11. Add new Month");
            System.out.println("12. Exit");
            System.out.println("==============================");
        }
        //Expense Management Methods
        //Add a one time expense to budget
    private static void addExpense(Scanner scanner, BudgetManager budgetManager) {
        System.out.println("\n Adding a new Expense...");
        scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        if (!Category.isValidCategory(category)) {
            System.out.println("Invalid category. Please use one of the predefined categories.");
            return;
        }
        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        budgetManager.addExpense(category, amount);
        System.out.println("Expense added successfully!"); 
    }

    //Add recurring expense to budget
    private static void addRecurringExpense(Scanner scanner, BudgetManager budgetManager){
        System.out.println("\nAdding a new Recurring Expense...");
        scanner.nextLine();
        System.out.println("Enter a category: ");
        String category  = scanner.nextLine();
        if (!Category.isValidCategory(category)){
            System.out.println("Invalid category, please use one of the predefined categories.");
            return;
        }
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        budgetManager.addRecurringExpense(category, amount);
        System.out.println("Recurring Expense added succesfully!"); 
    }

    //Adjust a recurring expense
    private static void adjustRecurringExpenses(BudgetManager budgetManager, Scanner scanner){
        System.out.println("Adjusting Recurring Expense");
        if (budgetManager.getRecurringExpense().isEmpty()){
            System.out.println("No Recurring Expenses found.");
            return;
        }
        
        System.out.println("Current Recurring Expense:");
        for (RecurringExpense recurringExpense : budgetManager.getRecurringExpense()){
            System.out.println("ID: "+ recurringExpense.getReID() +
                    ", Category: "+recurringExpense.getCategory() +
                    ", Amount: "+ recurringExpense.getAmount()+
                    ", Active: " + recurringExpense.isActive() );
        } 
      System.out.println("Enter the ID of the Recurring Expense you want to adjust: ");
      int reID = scanner.nextInt();
      RecurringExpense expenseToAdjust = budgetManager.findRecurringExpenseID(reID);
      
      if (expenseToAdjust == null){
          System.out.println("No recurring Expense found wit ID: "+ reID);
          return;
      }
      
        System.out.print("Current amount is $" + String.format("%.2f", expenseToAdjust.getAmount()) + 
                        ". Enter new amount: $");
        double newAmount = scanner.nextDouble();
        
        System.out.print("Should this recurring expense stay active? (true/false): ");
        boolean isActive = scanner.nextBoolean();
        
        boolean updated = budgetManager.updateRecurringExpense(reID, newAmount, isActive);
        if (updated) {
            System.out.println("Recurring expense updated successfully.");
        } else {
            System.out.println("Failed to update recurring expense.");
        }
    }

    //Stop a recurring expense
    private static void stopRecurringExpense(Scanner scanner, BudgetManager budgetManager){
        System.out.println("\nStopping Recurring Expense....");
        if (budgetManager.getRecurringExpense().isEmpty()) {
            System.out.println("No recurring expenses found.");
            return;
        }

        System.out.println("Current Recurring Expenses:");
        for (RecurringExpense recurringExpense : budgetManager.getRecurringExpense()) {
            if (recurringExpense.isActive()) {
                System.out.println("ID: " + recurringExpense.getReID() +
                        ", Category: " + recurringExpense.getCategory() +
                        ", Amount: $" + String.format("%.2f", recurringExpense.getAmount()));
            }
        }
        
        System.out.print("Enter ID number to stop: ");
        int reID = scanner.nextInt();
        
        RecurringExpense expenseToStop = budgetManager.findRecurringExpenseID(reID);
        if (expenseToStop != null) {
            expenseToStop.stop();
            System.out.println("Recurring expense stopped successfully!");
        } else {
            System.out.println("No recurring expense found with matching ID.");
        }
    }

    // Goal Management Methods
    //Add a goal to the budget
    public static void addGoal(BudgetManager budgetManager, Scanner scanner){
        System.out.println("Enter target goal amount: ");
        double targetAmount = scanner.nextDouble();

        if( targetAmount <= 0){
            System.out.println("Goal amount must be greater than zero.");
            return;
        } 
        budgetManager.addGoal(targetAmount);
        System.out.println("Your goal of $"+ String.format("%.2f",targetAmount) +" has been added successfully!" );
    }
    //Adjust a goal in the budget
    public static void adjustGoal(BudgetManager budgetManager, Scanner scanner){
        System.out.println("\nAdjusting Goal...");

        if(budgetManager.getGoal() <= 0){
            System.out.println("No Goal has been set yet.");
            System.out.println("Would you like to add a new goal? (yes/no)");
            scanner.nextLine();
            String answer = scanner.nextLine().trim().toLowerCase();
            if(answer.equals("yes")){
                addGoal(budgetManager, scanner);
                return;
            } else{
                System.out.println("Returning to main menu...");
                return;
            }
        }
        System.out.println("Current Goal: $" + String.format(".%.2f",budgetManager.getGoal()));
        System.out.println("Current Progress: $" + String.format(".%.2f",budgetManager.getGoalProgress()));
        System.out.println("Enter new goal amount: ");
        double newAmount = scanner.nextDouble();
        scanner.nextLine();
        if (newAmount <= 0) {
            System.out.println("Goal amount must be greater than zero.");
            return;
        }
        budgetManager.setGoal(newAmount);
    
        System.out.println("Goal  updated to $" + String.format("%.2f", budgetManager.getGoal()));
        
        double progressPercentage = budgetManager.getGoalProgressPercentage();
        System.out.println("Current progress: $" + String.format("%.2f", budgetManager.getGoalProgress()) + 
                          " (" + String.format("%.1f", progressPercentage) + "%)");
    }

    //Add progress to a goal in the budget
    public static void addGoalProgress(BudgetManager budgetManager, Scanner scanner) {
        System.out.println("\nAdding Progress to Goal");
        
        if(budgetManager.getGoal() <= 0){
            System.out.println("No Goal has been set yet");
            System.out.println("Would you like to add a new goal? (yes/no)");
            scanner.nextLine();
            String answer = scanner.nextLine().trim().toLowerCase();
            if(answer.equals("yes")){
                addGoal(budgetManager, scanner);
            } else{
                System.out.println("Returning to main menu...");
                return;
            }
        }
        System.out.println("Current Goal: $" + String.format("%.2f", budgetManager.getGoal()));
        System.out.println("Current Progress: $" + String.format("%.2f", budgetManager.getGoalProgress()) + 
                          " (" + String.format("%.1f", budgetManager.getGoalProgressPercentage()) + "%)");
        
        System.out.print("Enter amount to add to progress: $");
        double progressAmount = scanner.nextDouble();
        scanner.nextLine();
        
        if (progressAmount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
        budgetManager.addGoalProgress(progressAmount);
    
        if (budgetManager.getGoal() <= 0) {
            System.out.println("\nWould you like to set a new savings goal? (yes/no)");
            String answer = scanner.nextLine().trim().toLowerCase();
            
            if (answer.equals("yes")) {
                addGoal(budgetManager, scanner);
                } 
            }
    }

    //Display Methods

    //View all predefined categories
    private static void viewAllCategories(){
        System.out.println("\nPredefined Categories");
        System.out.println(Category.formattedCategories());
    }

    //View all expenses in the budget
    private static void viewAllExpenses(BudgetManager budgetManager){
        System.out.println("\nAll Expenses: ");
        for (Expense expense : budgetManager.getAllExpenses()){
            System.out.println(expense);
        }
    }
    //View Budget Report
    private static void viewReport(BudgetManager budgetManager){
        System.out.println("\nBudget Report");
        
        Report report = budgetManager.generateReport();
        System.out.println(report); 
        
    }

    //Starts a new month in the budget (aka new budget cycle)
    private static void startNewMonth(BudgetManager budgetManager, Scanner scanner) {
        System.out.println("\n Starting new Month...");
        System.out.println("Previous month information saved to 'Budget.txt'");
        System.out.println("Goal and Recurring Expenses will be transfered to next month");
        System.out.println("Are you sure you want to proceed this action cannot be undone. (yes/no):   ");
        scanner.nextLine();
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals("yes")) {
            budgetManager.startNewMonth();
            System.out.println("New month started successfully!");
        } else {
            System.out.println("Action cancelled. No changes made.");
        }
    }
}