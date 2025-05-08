/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author anna
 */
public class BudgetManager {
        
        private double monthlyIncome;
        private double totalSpent;
        private double totalRemaining;

        private ArrayList<Expense> expenses;
        private List<RecurringExpense> recurringExpenses;
        private Map<String, Double> categoryBudget;

        private boolean isBudgetcreated;
        private boolean editBudgetPlan;

        private Goal budgetGoal;
        
            /**
            * Constructor to initialize the BudgetManager.
            */
        public BudgetManager(){
            //Initialize financial values
            this.monthlyIncome = 0.0;
            this.totalSpent = 0.0;
            this.totalRemaining = 0.0;
            //Initialize lists and maps
            this.expenses = new ArrayList<>();
            this.recurringExpenses = new ArrayList<>();
            this.categoryBudget = new HashMap<>();
            //Initialize budget goal
            this.budgetGoal = new Goal(0.0, 0.0);
            //Initialize budget creation status
            this.isBudgetcreated = false;
            this.isBudgetcreated = true;

            //Initialize Category Budgets
            categoryBudget.put(Category.GROCERIES, 0.0);
            categoryBudget.put(Category.BILLS ,0.0);
            categoryBudget.put(Category.GAS ,0.0);
            categoryBudget.put(Category.FOOD ,0.0);
            categoryBudget.put(Category.NECESSITIES ,0.0);
            categoryBudget.put(Category.RENT ,0.0);
            categoryBudget.put(Category.OTHER,0.0);

        }
        
        //Income management methods
        //Set Income
        public void setMonthlyIncome(double income){
            this.monthlyIncome = income;
            calculateRemainingBudget();
           }

        //Get Income
        public  double getMonthlyIncome(){
            return monthlyIncome;
        }

        //Expense Management Methods
        //Add Expense
        public void addExpense(String category, double amount) {
            if (!isBudgetcreated){
                System.out.println("No Budgets have been created yet.");
                return;
            }
            if(!Category.isValidCategory(category)){
                System.out.println("Invalid category: "+ category);
                return;
            }
            Expense expense = new Expense(category, amount);
            expenses.add(expense);
            totalSpent += amount; 
            calculateRemainingBudget();
            System.out.println("Expense added: $" + String.format("$.2f",amount));
        }

        //Get Amount Spent
        public double getTotalSpent(){
            return totalSpent;
        }

        //Get Remaining Amount
        public double getRemainingBudget(){
            return totalRemaining;
        }

        //Get all Expenses
        public List<Expense> getAllExpenses(){
            List<Expense> allExpenses = new ArrayList<>(expenses);
            for (RecurringExpense recurringExpense : recurringExpenses){
                if (recurringExpense.isActive()){
                    allExpenses.add(recurringExpense);
                }
            }
            return allExpenses;
        }

        //Recurring Expense Management Methods
        //Add Recurring Expense
        public void addRecurringExpense(String category, double amount){
            if (!isBudgetcreated){
                System.out.println("No Budgets have been created yet.");
                return;
            }
            if(!Category.isValidCategory(category)){
                System.out.println("Invalid category: "+ category);
                return;
            }
            RecurringExpense recurringExpense = new RecurringExpense(category, amount);
            this.recurringExpenses.add(recurringExpense);
            totalSpent += amount;
            calculateRemainingBudget();
            System.out.println("Recurring expense added: " + recurringExpense);

        }
        
        //Get Recurring Expenses
        public List<RecurringExpense> getRecurringExpense(){
            return recurringExpenses;
        }

        //Get Recurring Expense by ID
        public RecurringExpense findRecurringExpenseID(int reID){
            for (RecurringExpense recurringExpense : recurringExpenses){
                if(recurringExpense.getReID() == reID){
                    return recurringExpense; 
                } 
            }
            return null;  
        }

        //Update Recurring Expense
        public boolean updateRecurringExpense(int reID, double newAmount, Boolean isActive){
            for (RecurringExpense recurringExpense : recurringExpenses){
                if (recurringExpense.getReID() == reID){
                    recurringExpense.setAmount(newAmount);
                }
                if(isActive != null){
                    if (isActive){
                        recurringExpense.activate();
                    }else{
                        recurringExpense.stop();
                    }
                    return true;
                }
            }
            return false;
        }

        //Category management methods
        //get category budget
        public double getCategoryBudget(String category){
            if (categoryBudget.containsKey(category)){
                return categoryBudget.get(category);
            }else{
                System.out.println("Category not found:" + category);
                return 0.0;
            }  
        }

        //Set Category Budget
        public void setCategoryBudget(String category, double budget){
            if (categoryBudget.containsKey(category)){
                categoryBudget.put(category, budget);
                System.out.println("Budget for "+ category + " updated to $"+budget);
            }else {
                System.out.println("Invalid Category: "+ category);
            }
        }

        //Print Categroy Budgets
        public void printAllCategoryBudgets(){
            System.out.println("Category Budgets:");
            for (String category : categoryBudget.keySet()){
                System.out.println(category + ": $"+ categoryBudget.get(category));
            }
        }

        //Goal Management Methods
        //Add  Goal
        public void addGoal(double amount){
            if (!isBudgetcreated){
                System.out.println("No Budgets have been created yet.");
                return;
            }
            this.budgetGoal = new Goal(amount, 0.0);
            System.out.println("Budget Goal set to: $" + amount);
        }
        
        //Get Goal
        public double getGoal(){
            return this.budgetGoal.getGoal();
        }

        //Get Goal Progress
        public double getGoalProgress(){
            return this.budgetGoal.getProgress();
        }

        //Get Goal Progress Percentage
        public double getGoalProgressPercentage(){
            if (budgetGoal.getGoal() > 0){
                return (budgetGoal.getProgress()/budgetGoal.getGoal())*100;
            }else {
                return 0.0;
            }
        }

        //Set Goal
        public void setGoal(double amount){
            this.budgetGoal.setGoal(amount);
        }
        
        //Set Goal Progress
        public double setGoalProgress(){
            return this.budgetGoal.getProgress();
        }

        //Add Goal Progress
        public void addGoalProgress(double progAmount){
            this.budgetGoal.addProgress(progAmount);
            this.totalSpent += progAmount;
            calculateRemainingBudget();

            double progressPercent = getGoalProgressPercentage();
            if (progressPercent >= 100){
                System.out.println("Congratulations! You have reached your goal of $" + this.budgetGoal.getGoal() + "!");
                double achievedAmount = this.budgetGoal.getGoal();
                this.budgetGoal.setGoal(0.0);
            }
            System.out.println("Added $" + progAmount + " to goal progress!");
            System.out.println("Current progress: $" + String.format(".%.2f",this.budgetGoal.getProgress()) + 
                       " of $" + String.format("%.2f",this.budgetGoal.getGoal()) + "("+String.format("%.1f",getGoalProgressPercentage()) + "%)");
        }

        //Report Methods
        //Generate Report
        public Report generateReport(){
        Map<String, Double> categorySpending = new HashMap<>();
            for(Expense expense : expenses){
                    String category = expense.getCategory();
                    double amount = expense.getAmount();
                    categorySpending.put(category, categorySpending.getOrDefault(category, 0.0) + amount);
            }
            for(RecurringExpense recurringExpense : recurringExpenses){
                if (recurringExpense.isActive()){
                    String category = recurringExpense.getCategory();
                    double amount = recurringExpense.getAmount();
                categorySpending.put(category,categorySpending.getOrDefault(category, 0.0) + amount);
                }
            }
            if(budgetGoal.getProgress() > 0){
                categorySpending.put("Savings Goal", budgetGoal.getProgress());
            }
            double totalSpent = categorySpending.values().stream().mapToDouble(Double::doubleValue).sum();
            double totalRemaining = monthlyIncome - totalSpent;
            return new Report(monthlyIncome, totalSpent, totalRemaining, categorySpending, budgetGoal.getGoal(), budgetGoal.getProgress());
        }
        
        //Get Budget Summary
        public String getBudgetSummary(){
            if (!isBudgetcreated){
                return "No Budgets have been created yet.";
            }
            
            StringBuilder summary = new StringBuilder();
            summary.append("Monthly Income: $").append(String.format("%.2f",monthlyIncome)).append("\n");
            summary.append("Total Spent: $").append(String.format("%.2f",totalSpent)).append("\n");
            summary.append("Total Remaining: $").append(String.format("%.2f",totalRemaining)).append("\n");
            summary.append("Goal: $").append(String.format("%.2f",budgetGoal.getGoal())).append("\n");
            summary.append("Progress towards Goal: $").append(String.format("%.2f",budgetGoal.getProgress()));
            summary.append(" (").append(String.format("%.1f", 
                           (budgetGoal.getGoal() > 0 ? (budgetGoal.getProgress()/budgetGoal.getGoal())*100 : 0))).append("%)\n");
            summary.append("\nCategory Breakdown:\n");
            for (String category : categoryBudget.keySet()){
                double budget = categoryBudget.get(category);
                summary.append(category).append(": Budget $").append(String.format("%.2f",budget)).append("\n");
            }
            return summary.toString();
        }

        //New Month Management
        //Start New Month
        public void startNewMonth(){
            TXTWriter.writeBudgetToTXT(this);
            List<RecurringExpense> activeRecurringExpenses = new ArrayList<>();
            for (RecurringExpense expense : recurringExpenses) {
                if (expense.isActive()) {
                    activeRecurringExpenses.add(expense);
                }
            }
            this.expenses.clear();
            this.totalSpent = 0.0;

            for(RecurringExpense expense : activeRecurringExpenses){
                this.totalSpent += expense.getAmount();
            }
            calculateRemainingBudget();

            System.out.println("New month started. All expenses have been cleared and recurring expenses have been added to the new month.");
            System.out.println("Monthly Income: $" + String.format("%.2f", monthlyIncome));
            System.out.println("Recurring Expenses: $"+String.format("%.2f", totalSpent));
            System.out.println("Total Remaining: $"+String.format("%.2f", totalRemaining));
            System.out.println("Goal Progress: $"+String.format("%.2f", budgetGoal.getProgress()));
        } 
        

        //Utility methods
        //Edit Budget
        public void editBudget(String category, double newBudget){
            if (!isBudgetcreated){
                System.out.println("No Budgets have been created yet.");
                return;
            }
            if(categoryBudget.containsKey(category)){
                categoryBudget.put(category, newBudget);
                System.out.println("Budget for " + category + "has been updated to $" + newBudget);
            }else {
                System.out.println("Invalid category: "+ category);
            }
        }
        
        //Check if budget is created
        public boolean isBudgetCreated(){
            return isBudgetcreated;
        }
        
        //Calculate remaining budget
        private void calculateRemainingBudget(){
            totalRemaining = monthlyIncome - totalSpent;
        }
}
