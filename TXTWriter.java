/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author silly
 */
public class TXTWriter {
    public static void writeBudgetToTXT(BudgetManager budgetManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Budget Month (ex January):   ");
        String month = scanner.nextLine();
        System.out.println("Enter Budget Year (ex 2023):   ");
        String year = scanner.nextLine();
        String fileName = "Budget_"+month+"_"+year+".txt";

        try (
            FileOutputStream fs = new FileOutputStream(fileName);
            PrintWriter outFS = new PrintWriter(fs)) {
            
            System.out.println("Writing budget information to "+fileName +"...");
            outFS.println("Finalized Budget for "+month +" "+ year+" \n");
            outFS.println("Monthly Income: $" + String.format("%.2f", budgetManager.getMonthlyIncome()));
            outFS.println("Total Spent: $" + String.format("%.2f", budgetManager.getTotalSpent()));
            outFS.println("Remaining Budget: $" + String.format("%.2f", budgetManager.getRemainingBudget()) + "\n");
            
            outFS.println("Savings Goal: $" + String.format("%.2f", budgetManager.getGoal()));
            outFS.println("Progress towards Goal: $" + String.format("%.2f", budgetManager.getGoalProgress()));
            
            double percentage = 0.0;
            if (budgetManager.getGoal() > 0) {
                percentage = (budgetManager.getGoalProgress() / budgetManager.getGoal()) * 100;
            }
            outFS.println("Goal Completion: " + String.format("%.1f%%", percentage) + "\n");
            
            outFS.println("Category Budgets:");
            String[] categories = {
                Category.GROCERIES, Category.BILLS, Category.GAS, Category.FOOD,
                Category.NECESSITIES, Category.RENT, Category.OTHER
            };
            
            for (String category : categories) {
                double budget = budgetManager.getCategoryBudget(category);
                outFS.println(category + ": $" + String.format("%.2f", budget));
            }
            outFS.println();

            outFS.println("Recurring Expenses:");
            List<RecurringExpense> recurringExpenses = budgetManager.getRecurringExpense(); // Fixed method name
            for (RecurringExpense recurringExpense : recurringExpenses) {
                outFS.println("ID: " + recurringExpense.getReID() +
                        ", Category: " + recurringExpense.getCategory() +
                        ", Amount: $" + String.format("%.2f", recurringExpense.getAmount()) +
                        ", Active: " + recurringExpense.isActive());
            }
            outFS.println();

            outFS.println("One-Time Expenses:");
            List<Expense> allExpenses = budgetManager.getAllExpenses();
            for (Expense expense : allExpenses) {
                if (expense instanceof RecurringExpense) {
                    continue;
                }
                outFS.println("Category: " + expense.getCategory() +
                        ", Amount: $" + String.format("%.2f", expense.getAmount()));
            }
            outFS.println();

            System.out.println("Budget information successfully written to 'Budget.txt'");

        } catch (IOException ex) {
            System.out.println("Error: An IOException occurred when writing to 'Budget.txt'. Please try again.");
            ex.printStackTrace();
        }
    }
}