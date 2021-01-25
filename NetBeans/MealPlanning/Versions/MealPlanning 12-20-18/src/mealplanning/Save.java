/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static mealplanning.CatalogView.Catalog;
import static mealplanning.MenuView.Menu;
import static mealplanning.MenuView.MenuRecent;
import static mealplanning.ShoppingListView.ShoppingList;
import javafx.collections.ObservableList;
import static mealplanning.MealPlanning.alert;


/**
 *
 * @author ethan
 */
public class Save {
    
    private AlertBox alert = MealPlanning.alert;
    private String catalogSaveFile = "Meals.csv";
    private ObservableList<Meal> Catalog = CatalogView.Catalog;
//    private String menuSaveFile = "Menu.csv";
//    private String menuPrintFile = "Menu.txt";
    private ObservableList<Meal> Menu = MenuView.Menu;
    private String recentSaveFile = "Recent.csv";
    private ObservableList<Meal> Recent = MenuView.MenuRecent;
    private String shoppingListPrintFile = "ShoppingList.txt";
    private ObservableList<Meal> ShoppingList = ShoppingListView.ShoppingList;
    private boolean sampleFill = MealPlanning.sampleFill;
    private Resources R = new Resources();

    
    public void importData() {
        if(!sampleFill) {
            catalogImport();
            menuImport("Menu");
            recentImport();
           new AlertBox().notify("><File - Catalog - Menu - Recent - Load Completed>< - Save.importData()");
        }
        return;
}
    
    public void exportData() {
        catalogExport();
        menuExport("Menu");
        recentExport();
        shoppingListExport();
        new AlertBox().notify("><File - Catalog - Menu - Recent - ShoppingList - Save Completed>< - Save.importData()");
        return;
    }
    
    public boolean catalogImport() {
    try {
        File file = new File(catalogSaveFile);
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        int line = 0;
        
        Catalog.clear();

    try {
        while(inputStream.hasNext()) { 
            inputStream.useDelimiter(",");
           lastRead = inputStream.next();
            int id = Integer.parseInt(lastRead);  
            String name = inputStream.next();  
            Boolean meat = Boolean.parseBoolean(inputStream.next());  
            Boolean carb = Boolean.parseBoolean(inputStream.next()); 
            Boolean vegetable = Boolean.parseBoolean(inputStream.next()); 
            Boolean fruit = Boolean.parseBoolean(inputStream.next()); 
            Boolean brand = Boolean.parseBoolean(inputStream.next());
            lastRead=inputStream.next(); //reading in "|"


            Meal newMeal = new Meal(true,id,name,meat,carb,vegetable,fruit,brand);

            while(inputStream.hasNext() && (!((lastRead=inputStream.next()).equals("\n")))) {
                int ingredientID = Integer.parseInt(lastRead); 
                String ingredientName = inputStream.next();  
                newMeal.temporaryIngredients.add(new Meal(false,ingredientID,ingredientName, false, false, false, false,false));
                lastRead=inputStream.next(); //reading in "|"
            }
            Catalog.add(newMeal); 
            line++;            
        }

        inputStream.close();

        //link Ingredients
        for (int i =0; i<Catalog.size(); i++) {
            if(!Catalog.get(i).temporaryIngredients.isEmpty()) {
                Catalog.get(i).addIngredient();
            }
        }
        
        checkIngredients();
        return true;
    } catch(NumberFormatException exe) {
    alert.error("***><><***NumberFormatException - Meal skipped ***><><*** - Save.catalogImport()");
    alert.error("Line: "+line);
    alert.error("LastRead: >"+lastRead+"<");
    String skipped = inputStream.nextLine(); line++;
    alert.error("Skipped Line: "+ skipped);
    }
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.catalogImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.catalogImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.catalogImport()");               
    }
    return false;
    }

    public boolean catalogExport() {
            try {
                FileWriter writer = new FileWriter(catalogSaveFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);                    
  
                for(int i = 0; i<Catalog.size(); i++) {

                     print.print(Integer.toString(Catalog.get(i).getID())+","); //new start w/,
                     print.print(Catalog.get(i).getName()+",");
                     print.print(Boolean.toString(Catalog.get(i).isMeat())+",");
                     print.print(Boolean.toString(Catalog.get(i).isCarb())+",");
                     print.print(Boolean.toString(Catalog.get(i).isVegetable())+",");
                     print.print(Boolean.toString(Catalog.get(i).isFruit())+",");
                     print.print(Boolean.toString(Catalog.get(i).isNameBrand())+",|,");
                     if(!Catalog.get(i).Ingredients.isEmpty()) {
                        Catalog.get(i).Ingredients.forEach(j -> {
                            print.print(Integer.toString(j.getID())+",");
                            print.print(j.getName()+",|,");                     

                        });                 
                     }
                     print.print("\n,");

                }  
                     print.flush();
                     print.close();
                    return true;
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.catalogExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.catalogExport()");
            }
            return false;
    }
    
    public boolean menuImport(String theMenu) {
    try {
        File file = new File(theMenu+".csv");
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        String mealNames[] = {"Breakfast", "Monday Lunch", "Monday Dinner", "Tuesday Lunch", "Tuesday Dinner", "Wednessday Lunch", "Wednessday Dinner", "Thursday Lunch", "Thursday Dinner", "Friday Lunch", "Friday Dinner", "Saturday Lunch", "Saturday Dinner", "Sunday Lunch", "Sunday Dinner", "*************", "ERROR_OverFlow_1x", "ERROR_OverFlow_2x", "ERROR_OverFlow_3x"};
        int currentMealIndex = 0;
        int line = 0;
        
        Menu.clear();
        
    while(inputStream.hasNext()) { 
        try{
            inputStream.useDelimiter(",");
           lastRead = inputStream.next();
            int id = Integer.parseInt(lastRead);  
            String name = inputStream.next();  
            Boolean meat = Boolean.parseBoolean(inputStream.next());  
            Boolean carb = Boolean.parseBoolean(inputStream.next()); 
            Boolean vegetable = Boolean.parseBoolean(inputStream.next()); 
            Boolean fruit = Boolean.parseBoolean(inputStream.next()); 
            Boolean brand = Boolean.parseBoolean(inputStream.next()); 
            lastRead=inputStream.next(); //reading in "|"
            lastRead=inputStream.next(); //reading in "\n|"

            Meal newMeal;
            
            if(id < 0) {
                newMeal = new Meal(false,((currentMealIndex+1)*-1),"*****"+mealNames[currentMealIndex]+"*****",false,false,false,false,false);
                currentMealIndex++;               
            }
            else
                newMeal = new Meal(false,id,name,meat,carb,vegetable,fruit,brand);

            Menu.add(newMeal);
            line++;
            
        } catch(NumberFormatException exe) {
        alert.error("***><><***NumberFormatException - Meal skipped ***><><***- Save.menuImport()");
        alert.error("Line: "+line);
        alert.error("LastRead: >"+lastRead+"<");
        String skipped = inputStream.nextLine(); line++;
        alert.error("Skipped Line: "+ skipped);
         }
    }

    inputStream.close();
    return true;
    
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.menuImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.menuImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.menuImport()");               
    }
    return false;
    }

    public boolean menuExport(String theMenu) {
            try {
                //Write saving file Menu.csv
                FileWriter writer = new FileWriter(theMenu+".csv", false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

                for(int i = 0; i<Menu.size(); i++) {

                     print.print(Integer.toString(Menu.get(i).getID())+","); //new start w/,
                     print.print(Menu.get(i).getName()+",");
                     print.print(Boolean.toString(Menu.get(i).isMeat())+",");
                     print.print(Boolean.toString(Menu.get(i).isCarb())+",");
                     print.print(Boolean.toString(Menu.get(i).isVegetable())+",");
                     print.print(Boolean.toString(Menu.get(i).isFruit())+",");
                     print.print(Boolean.toString(Menu.get(i).isNameBrand())+",|,");
                     
                     print.print("\n,");

                }  
                     print.flush();
                     print.close();
                
                //write printing file Menu.txt
                FileWriter writer2 = new FileWriter(theMenu+".txt", false);
                BufferedWriter buffer2 = new BufferedWriter(writer2);
                PrintWriter print2 = new PrintWriter(buffer2);
               
                print2.println("\t----------M-E-N-U----------");
                print2.println();
                print2.println("ID:\t\tNAME:");
                for(int i = 0; i<Menu.size(); i++) {
                    if(Menu.get(i).getID() < 0)
                        print2.println();
                    else
                        print2.print(Integer.toString(Menu.get(i).getID())+"\t");                              
                    print2.print(Menu.get(i).getName());
                    print2.println();

                }  
                     print2.flush();
                     print2.close();
                     return true;
                     
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><***- Menu -");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><***- Menu -");
            }
            return false;
    }
    public boolean shoppingListExport() {
            try {
                FileWriter writer = new FileWriter(shoppingListPrintFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

                print.println("\t----------S-H-O-P-P-I-N-G---L-I-S-T----------");
                print.println();
                print.println("QUANTITY:\tNAME:\t\tID:");
                for(int i = 0; i<ShoppingList.size(); i++) {
                     print.print(ShoppingList.get(i).getServings()+"\t\t");
                     print.print(ShoppingList.get(i).getName()+"\t\t");
                     print.print(Integer.toString(ShoppingList.get(i).getID()));
                     print.println();
                }  
                     print.flush();
                     print.close();
                     return true;
                     
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.shoppingListExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.shoppingListExport()");
            }
            return false;
    }
    
    public boolean recentImport() {
    try {
        File file = new File(recentSaveFile);
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        int line = 0;
        
        Recent.clear();

    while(inputStream.hasNext()) { //not eof
        try{
            lastRead = inputStream.next();
            int id = Integer.parseInt(lastRead);  
            String name = inputStream.next();
            inputStream.next();  //  |  = next element
            Meal newMeal;
            
            newMeal = new Meal(false,id,name,false,false,false,false,false);

            Recent.add(newMeal);
            line++;

        } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException - Meal skipped ***><><*** - Save.recentImport()");
            alert.error("Line: "+line);
            alert.error("LastRead: >"+lastRead+"<");
            String skipped = inputStream.nextLine(); line++;
            alert.error("Skipped Line: "+ skipped);
        }
    }

    inputStream.close();
    return true;
    
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.recentImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.recentImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.recentImport()");               
    }
    return false;
    }
    
    public boolean recentExport() {
            try {
                FileWriter writer = new FileWriter(recentSaveFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);                    
  
                for(int i = 0; i<Recent.size(); i++) {
                    print.print(Integer.toString(Recent.get(i).getID())+",");                              
                    print.print(Recent.get(i).getName()+",\n,");

                }  
                print.flush();
                print.close(); 
                return true;    
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.recentExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.recentExport()");
            }
            return false;
    }
    
    public void checkIngredients() {
        if(sampleFill)  //doesn't check
            return;
        
        boolean mealsErrorFound = false;
        boolean ingredientsErrorFound = false;

        String missingIngredients = "";
        String wrongIngredients = "";

    for (int i = 0; i<Catalog.size(); i++) {
        if(Catalog.get(i).getID()>R.specialLast && Catalog.get(i).getID()>=R.mealsFirst && Catalog.get(i).getID()<=R.mealsLast) {
            if(Catalog.get(i).Ingredients.isEmpty()) {    //Is Meal
                mealsErrorFound = true;
                missingIngredients = missingIngredients + Integer.toString(Catalog.get(i).getID())+"\t"+Catalog.get(i).getName()+"\n";
            }
        }
        else if(Catalog.get(i).getID()>R.specialLast&& (Catalog.get(i).getID()<R.mealsFirst || Catalog.get(i).getID()>R.mealsLast)) {
            if(!Catalog.get(i).Ingredients.isEmpty()) {   //Is Ingredient
                ingredientsErrorFound = true;
                wrongIngredients = wrongIngredients + Integer.toString(Catalog.get(i).getID())+"\t"+Catalog.get(i).getName()+"\n";
            }
        }
    }
    
    if(mealsErrorFound || ingredientsErrorFound) {
        String errorMessage = "";
        if(mealsErrorFound)
            errorMessage = "\nThe following Meals were found to be missing Ingredients:\n"+missingIngredients;
        if(ingredientsErrorFound)
            errorMessage = errorMessage+"\nThe following Ingredients were found to have Ingredients:\n"+wrongIngredients;
        if(new AlertBox().actionRequest("INGREDIENTS ERROR DETECTED:\n"+R.catalogIDRequirements(Catalog)+errorMessage, "Reload Catalog.\nNote: All changes to Catalog will be lost.")) {
            catalogImport();
            new AlertBox().notify("Catalog has been cleared and Re-Imported.");
        }
    }
    return;
}
}