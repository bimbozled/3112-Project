/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


/**
 *
 * @author silly
 */
public class Expense {
    private String category;
    private double amount;
    
    public Expense(String category, double amount){
    if (amount < 0){
        throw new IllegalArgumentException("Expense must be greater than 0");
    }
    this.category = category;
    this.amount = amount;
    }
    
    public String getCategory(){
        return category;
    }
    
    public double getAmount(){
        return amount;
    }

    
    public void setCategory(String category){
        this.category = category;
    }
    
    public void setAmount(double amount){
        if (amount < 0){
            throw new IllegalArgumentException("Expense cannot be negative.");
        }
        this.amount = amount;
    }
    
    @Override 
    public String toString(){
        return String.format("Expense[Category: %s, Amount: $%.2f]",
                category, amount);
    }

}
