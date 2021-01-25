/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static mealplanning.CatalogView.Catalog;
import static mealplanning.CatalogView.CatalogFoodOnly;
import static mealplanning.MealPlanning.alert;


/**
 *
 * @author ethan
 */
public class Meal {
    
private boolean print = MealPlanning.print;
private boolean sampleFill = MealPlanning.sampleFill;
private AlertBox alert = MealPlanning.alert;


public ObservableList<Meal> Catalog = CatalogView.Catalog;
public ObservableList<Meal> FoodOnly = CatalogView.CatalogFoodOnly;

    
    Meal(boolean orig, int num, String name, boolean meat, boolean carb, boolean veg, boolean fruit)
    {   
        Original = orig;
        ID = num;
        Name = name;
        Meat = meat;
        Carb = carb;
        Vegetable = veg;
        Fruit = fruit;
        resetQuantity();

        if(Original) {
            //updateFood(true);
            if(sampleFill) {
                if(ID%2==0){
//                    alert.notify("adding ingredients");
                    Ingredients.add(new Meal(false,(num*100)+1,"ingredientOne",true,true,false,false));
                    Ingredients.add(new Meal(false,(num*100)+2,"ingredientTwo",true,true,false,false));
                    Ingredients.add(new Meal(false,(num*100)+3,"ingredientThree",true,true,false,false));
                    Ingredients.add(new Meal(false,(num*100)+4,"ingredientFour",true,true,false,false));
                    Ingredients.add(new Meal(false,(num*100)+5,"ingredientFive",true,true,false,false));
                }
//                else
//                    alert.notify("NOT adding ingredients");

            }
        }
        print(); 
    }
    
//Complete Copy Meal
    public Meal(Meal aMeal)
    {   
        Original = aMeal.isOriginal();
        ID = aMeal.getID();
        Name = aMeal.getName();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        resetQuantity();

        print(); 
    }
    
    //Complete Copy Meal
    public Meal(boolean orig, Meal aMeal)
    {   
        Original = orig;
        ID = aMeal.getID();
        Name = aMeal.getName();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        resetQuantity();
        if(Original)
            //updateFood(true);

        print(); 
    }
    
    //For Menu
    public Meal(String offset, Meal aMeal)
    {   
        Original = false;
        ID = aMeal.getID();
        Name = offset + aMeal.getName();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        //updateFood(true);
        this.resetQuantity();
        
        print(); 
    }
        
    private int ID;
    private String Name;
    
    //Categories:
    private boolean Meat;
    private boolean Carb;
    private boolean Vegetable;
    private boolean Fruit;
    
    private boolean Original;
    private boolean Food;
    private int Quantity;

    public List<Meal> temporaryIngredients = new ArrayList();
  
   public ObservableList<Meal> Ingredients = FXCollections.observableArrayList();
   //public ArrayList<Meal> Ingredients = new ArrayList();
   
   //old now recreating Food Only List in MealPlanning.updateMenu();
public void updateFood(boolean isFood) {
    if(isFood) { //add to FoodOnly List
        boolean found=false;
        for(int i=0; i<FoodOnly.size(); i++){
            if(FoodOnly.get(i).ID==ID){
                found=true;
                break;
            }
        }
        if(!found) {
            boolean added=false;
            for(int j=0; j<FoodOnly.size(); j++){
                if(j!=(FoodOnly.size()-1)) {
                    if(ID<FoodOnly.get(j+1).ID) {
                        FoodOnly.add(j+1,this);
                        added = true;
                        break;
                    }
                }
            }
            if(!added){
                FoodOnly.add(this);
            }
        }
        Food = true;
        
    } else { //Remove from FoodOnly List
        boolean found = false;
        for(Meal i : FoodOnly){
            if(i.ID==ID){
                FoodOnly.remove(i);
                found = true;
                break;
            }
        }
        Food = false;
        if(!found)   //add to Catalog
           alert.error("Meal not found in CatalogFoodOnly - "+Integer.toString(ID)+" - " + Name +" - No action taken - Meal.//updateFood()");
        Food = false;
    }
    return;
}

public void print()
{
     if(!print)
        return;
     if(Original)
        System.out.print("*Catalog Meal: "+Integer.toString(ID)+" - "+Name);
    else
        System.out.print("Meal: "+Integer.toString(ID)+" - "+Name);
    if(Meat)
        System.out.print("- Meat ");
    if(Carb)
        System.out.print("- Carb ");
    if(Vegetable)
        System.out.print("- Vegetable ");
    if(Fruit)
        System.out.print("- Fruit ");
    if(!Meat&&!Carb&&!Vegetable&&!Fruit)
        System.out.println("--No Food Matches--");
    else
        System.out.print("\n");
    if(!Ingredients.isEmpty()){
        System.out.print("-Ingredients: ");
        Ingredients.forEach(e->{System.out.print(Integer.toString(e.ID)+" - "+e.Name+" | ");});
        System.out.print("\n");}
    return;
    }

//regular
public void addIngredient(int id) {
    if(ID == id) {//can't have a meal inside itself.
       alert.error("May not add Meal to itself - " + Integer.toString(ID)  + " - "+Name+" - Meal.AddIngredient()");
        return;
    }
    boolean found=false;
    for(int i=0; i<Catalog.size(); i++){
        if(Catalog.get(i).ID==id){
            found=true;
//            if(Ingredients.isEmpty())
                //updateFood(false);
            boolean added=false;
            for(int j=0; j<Ingredients.size(); j++){
                    if(id<Ingredients.get(j).getID()) {
                          Ingredients.add(j,Catalog.get(i));
                        added = true;
                        return;
                    }
            }
            if(!added){
                Ingredients.add(Catalog.get(i));
            }
            return;
        }
    }
    if(!found)
       alert.error("Meal not found in Catalogue - "+Integer.toString(id)+" - Meal.addIngredient()");
    return;
}


//For inport:
public void addIngredient() {
    if(temporaryIngredients.isEmpty()) {
        alert.error("***INVALID call - temporaryIngredients is NULL of - " + Integer.toString(ID) + " - " + Name + " - Inport -> Meal.addIngredient()");
        return;
    }
    Ingredients.clear();
    //updateFood(false);

    for(int q =0; q<this.temporaryIngredients.size(); q++) {
        if(ID == temporaryIngredients.get(q).getID()) //can't have a meal inside itself.
            break;
        boolean found=false;
        for(int i=0; i<Catalog.size(); i++){
            if(Catalog.get(i).ID==temporaryIngredients.get(q).getID()){
                found=true;
                boolean added=false;
                for(int j=0; j<Ingredients.size(); j++){
                        if(temporaryIngredients.get(q).getID()<Ingredients.get(j).ID) {
                              Ingredients.add(j,Catalog.get(i));
                          added = true;
                          break;
                        }
                }
                if(!added){
                    Ingredients.add(Catalog.get(i));
                    break;
                }
            }
        }
        if(!found) {
            Ingredients.add(temporaryIngredients.get(q));
            if(print) {
                alert.error("Meal not found in Catalogue - Added Anyways to - " + Integer.toString(ID) + " - " + Name + "- Inport -> Meal.addIngredient()");
                temporaryIngredients.get(q).print();
            }
        }
    }
   
}



   public void removeIngredient(int id) {
       boolean found = false;
       for(Meal i : Ingredients){
       if(i.ID==id){
           Ingredients.remove(i);
           found = true;
           if(Ingredients.isEmpty())
               //updateFood(true);
           return;
        }
       }
   if(!found)
       alert.error("Meal not found in Ingredients - "+Integer.toString(ID)+" - " + Name +" - Meal.removeIngredient()");
    return;
   }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

   
   
    public boolean isMeat() {
        return Meat;
    }

    public void setMeat(boolean Meat) {
        this.Meat = Meat;
    }

    public boolean isCarb() {
        return Carb;
    }

    public void setCarb(boolean Carb) {
        this.Carb = Carb;
    }

    public boolean isVegetable() {
        return Vegetable;
    }

    public void setVegetable(boolean Vegetable) {
        this.Vegetable = Vegetable;
    }

    public boolean isFruit() {
        return Fruit;
    }

    public void setFruit(boolean Fruit) {
        this.Fruit = Fruit;
    }
    
    public boolean isFood() {
        return Ingredients.isEmpty();
    }
      
    public boolean isOriginal() {
        return Original;
    }
    
    public void increaseQuantity() {
        Quantity++;
    }
    
    public void decreaseQuantity() {
        if(Quantity > 1)
           Quantity--;
    }
    
    public void resetQuantity() {
        Quantity = 1;
    }

    public int getQuantity() {
        return Quantity;
    }
    
    
   
}
