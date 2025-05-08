/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author silly
 */
public class Goal {
    public double amount;
    public double progress;
    
    public Goal(double amount, double progress){
        if(amount < 0 ){
            throw new IllegalArgumentException("Target amount must be greater than zero.");
        }
        this.amount = amount;
        this.progress = progress;
    }
    public double getProgress(){
        return progress;
    }
    public double getGoal(){
        return amount;
    }
    
    public void addProgress(double amount){
        if(amount < 0){
            throw new IllegalArgumentException("Target amount must be greater than zero");
        }
        this.progress += amount;
    }
    
    public void setGoal(double amount){
        if(amount < 0 ){
            throw new IllegalArgumentException("Target amount must be greater than zero.");
        }
        this.amount = amount;
    }
    @Override 
    public String toString(){
        return String.format("Target: $.2f, Progress $.2f", amount, progress);
    }
    
}
