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
import java.util.NoSuchElementException;
import java.util.Scanner;
import static mealplanning.CatalogView.Catalog;
import static mealplanning.MenuView.Menu;
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
    private String menuSaveFile = "Menu.csv";
    private String menuPrintFile = "Menu.txt";
    private ObservableList<Meal> Menu = MenuView.Menu;
    private String shoppingListPrintFile = "ShoppingList.txt";
    private ObservableList<Meal> ShoppingList = ShoppingListView.ShoppingList;

    private boolean sampleFill = MealPlanning.sampleFill;

    
    public void importData() {
        if(!sampleFill) {
            catalogImport();
            menuImport();
        }
        return;
}
    
    public void exportData() {
        catalogExport();
        menuExport();
        shoppingListExport();
        return;
    }
    
    public void catalogImport() {
    try {
        File file = new File(catalogSaveFile);
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        int line = 0;

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
            lastRead=inputStream.next(); //reading in "|"


            Meal newMeal = new Meal(true,id,name,meat,carb,vegetable,fruit);

            while(inputStream.hasNext() && (!((lastRead=inputStream.next()).equals("\n")))) {
                int ingredientID = Integer.parseInt(lastRead); 
                String ingredientName = inputStream.next();  
                newMeal.temporaryIngredients.add(new Meal(false,ingredientID,ingredientName, false, false, false, false));
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
        new AlertBox().notify("><File - Catalog - Sucessfully Loaded>< - Save.catalogImport()");

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
    }

    public void catalogExport() {
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
                     print.print(Boolean.toString(Catalog.get(i).isFruit())+",|,");
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
                     new AlertBox().notify("><File - Catalog - Sucessfully Saved>< - Save.catalogExport()");

            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.catalogExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.catalogExport()");
            }
    }
    
    public void menuImport() {
    try {
        File file = new File(menuSaveFile);
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        String mealNames[] = {"Breakfast", "Monday Lunch", "Monday Dinner", "Tuesday Lunch", "Tuesday Dinner", "Wednessday Lunch", "Wednessday Dinner", "Thursday Lunch", "Thursday Dinner", "Friday Lunch", "Friday Dinner", "Saturday Lunch", "Saturday Dinner", "Sunday Lunch", "Sunday Dinner", "*************", "ERROR_OverFlow_1x", "ERROR_OverFlow_2x", "ERROR_OverFlow_3x"};
        int currentMealIndex = 0;
        int line = 0;
        try {
           
        while(inputStream.hasNext()) { //not eof
            lastRead = inputStream.next();
            int id = Integer.parseInt(lastRead);  
            String name = inputStream.next();
            inputStream.next();  //  |  = next element
            Meal newMeal;
            
            if(id < 0) {
                newMeal = new Meal(false,((currentMealIndex+1)*-1),"*****"+mealNames[currentMealIndex]+"*****",false,false,false,false);
                currentMealIndex++;               
            }
            else
                newMeal = new Meal(false,id,name,false,false,false,false);

            Menu.add(newMeal);
            line++;
        }

        inputStream.close();
        new AlertBox().notify("><File - Menu - Sucessfully Loaded>< - Save.menuImport()");

    } catch(NumberFormatException exe) {
    alert.error("***><><***NumberFormatException - Meal skipped ***><><***- Save.menuImport()");
    alert.error("Line: "+line);
    String skipped = inputStream.nextLine();     
    alert.error("--->lastRead: "+lastRead+" -> "+skipped);
    }
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.menuImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.menuImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.menuImport()");               
    }
    }

    public void menuExport() {
            try {
                //Write saving file Menu.csv
                FileWriter writer = new FileWriter(menuSaveFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

                for(int i = 0; i<Menu.size(); i++) {
                    print.print(Integer.toString(Menu.get(i).getID())+",");                              
                    print.print(Menu.get(i).getName()+",\n,");

                }  
                     print.flush();
                     print.close();
                
                //write printing file Menu.txt
                FileWriter writer2 = new FileWriter(menuPrintFile, false);
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
                     new AlertBox().notify("><File - Menu - Sucessfully Saved><");

            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><***- Menu -");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><***- Menu -");
            }
    }
    public void shoppingListExport() {
            try {
                FileWriter writer = new FileWriter(shoppingListPrintFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

                print.println("\t----------S-H-O-P-P-I-N-G---L-I-S-T----------");
                print.println();
                print.println("QUANTITY:\tNAME:\t\tID:");
                for(int i = 0; i<ShoppingList.size(); i++) {
                     print.print(ShoppingList.get(i).getQuantity()+"\t\t");
                     print.print(ShoppingList.get(i).getName()+"\t\t");
                     print.print(Integer.toString(ShoppingList.get(i).getID()));
                     print.println();
                }  
                     print.flush();
                     print.close();
                     new AlertBox().notify("><File - ShoppingList - Sucessfully Saved>< - Save.shoppingListExport()");

            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.shoppingListExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.shoppingListExport()");
            }
    }
    
    private void checkIngredients() {
    for (int i = 0; i<Catalog.size(); i++) {
        if(!Catalog.get(i).Ingredients.isEmpty())
            return;
    }
    alert.error("No Ingredients were detected in any Catalog Meals - checkIngredients()");
    return;
}
}