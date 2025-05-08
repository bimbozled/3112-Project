/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import java.util.Map;

/**
 *
 * @author silly
 */
public class Report {
    private double monthlyIncome;
    private double totalSpent;
    private double totalRemaining;
    private Map<String, Double> categorySpending;
    private double goalAmount;
    private double goalProgress;
    
    public Report(double monthlyIncome, double totalSpent, double totalRemaining, 
                 Map<String, Double> categorySpending, double goalAmount, double goalProgress) {
        this.monthlyIncome = monthlyIncome;
        this.totalSpent = totalSpent;
        this.totalRemaining = totalRemaining;
        this.categorySpending = categorySpending;
        this.goalAmount = goalAmount;
        this.goalProgress = goalProgress;
    }
    
    public Report(double monthlyIncome, double totalSpent, double totalRemaining, 
                 Map<String, Double> categorySpending) {
        this(monthlyIncome, totalSpent, totalRemaining, categorySpending, 0.0, 0.0);
    }
    
    public double getMonthlyIncome() {
        return monthlyIncome;
    }
    
    public double getTotalSpent() {
        return totalSpent;
    }
    
    public double getTotalRemaining() {
        return totalRemaining;
    }
    
    public Map<String, Double> getCategorySpending() {
        return categorySpending;
    }
    public double getGoalAmount() {
        return goalAmount;
    }
    
    public double getGoalProgress() {
        return goalProgress;
    }
    
    public double getGoalProgressPercentage() {
        if (goalAmount > 0) {
            return (goalProgress / goalAmount) * 100;
        }
        return 0.0;
    }
    
    @Override
    public String toString() {
        StringBuilder report = new StringBuilder();
        report.append("Financial Report:\n");
        report.append("Total Income: $").append(String.format("%.2f", monthlyIncome)).append("\n");
        report.append("Total Spent: $").append(String.format("%.2f", totalSpent)).append("\n");
        report.append("Total Remaining: $").append(String.format("%.2f", totalRemaining)).append("\n");
        
        report.append("\nSavings Goal:\n");
        if (goalAmount > 0) {
            report.append("Goal Amount: $").append(String.format("%.2f", goalAmount)).append("\n");
            report.append("Current Progress: $").append(String.format("%.2f", goalProgress)).append("\n");
            
            double progressPercentage = getGoalProgressPercentage();
            report.append("Goal Completion: ").append(String.format("%.1f%%", progressPercentage)).append("\n");
            
            double remaining = goalAmount - goalProgress;
            if (remaining > 0) {
                report.append("Remaining to Goal: $").append(String.format("%.2f", remaining)).append("\n");
            } else {
                report.append("Goal Achieved! \n");
            }
        } else {
            report.append("No savings goal set.\n");
        }
        
        report.append("\nCategory Spending Breakdown:\n");
        for (String category : categorySpending.keySet()) {
            double spending = categorySpending.get(category);
            report.append(category).append(": $").append(String.format("%.2f", spending)).append("\n");
        }
        
        return report.toString();
    }
}