/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
/**
 *
 * @author silly
 */

 public class RecurringExpense extends Expense{
    private boolean isActive;
    private int reID; 
    private static int idCounter =0;
    
     public RecurringExpense(String category, double amount){
         super(category, amount);
         if (category == null || amount < 0){
             throw new IllegalArgumentException("Cannot be null or negative.");
         }
         this.isActive = true;
         this.reID = idCounter++;
     }
     
     public boolean isActive(){
         return isActive;
     }
     public void stop(){
         this.isActive = false;
     }
     
     public void activate(){
         this.isActive = true;
     }
     
     public int getReID(){
         return reID;
     }
     
    @Override 
    public String toString() {
        return String.format(
            "RecurringExpense{"+
            "ID num: " + reID+
            ", Category: "+ getCategory()+ '\''+
            ", Amount: "+ getAmount() +
            ", Active: "+ isActive + '}');
     }        
}
    
