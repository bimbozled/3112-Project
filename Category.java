/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author silly
 */
public class Category {
    public static String GROCERIES = "Groceries";
    public static String BILLS = "Bills";
    public static String GAS = "Gas";
    public static String FOOD = "Food";
    public static String NECESSITIES = "Necessities";
    public static String OTHER = "other";
    public static String RENT = "Rent";
    
    
    public static boolean isValidCategory(String category){
        String trimmedCategory = category.trim();
        return trimmedCategory.equalsIgnoreCase(GROCERIES) ||
        trimmedCategory.equalsIgnoreCase(BILLS) ||
        trimmedCategory.equalsIgnoreCase(GAS) ||
        trimmedCategory.equalsIgnoreCase(FOOD) ||
        trimmedCategory.equalsIgnoreCase(NECESSITIES) ||
        trimmedCategory.equalsIgnoreCase(OTHER) ||
        trimmedCategory.equalsIgnoreCase(RENT);

    }
    
    public static String formattedCategories(){
        return GROCERIES + ", " + BILLS + ", "+GAS+", "+FOOD+", "+NECESSITIES+", "+OTHER+", "+RENT+".";
    }
    
          
}
