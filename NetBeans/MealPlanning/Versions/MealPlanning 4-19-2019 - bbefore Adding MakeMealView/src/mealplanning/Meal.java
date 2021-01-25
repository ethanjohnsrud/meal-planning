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

    
    Meal(boolean orig, int num, String name, int pop, boolean meat, boolean carb, boolean veg, boolean fruit, boolean brand, boolean pack, boolean base)
    {   
        Original = orig;
        ID = num;
        Name = name;
        Popularity = pop;
        Meat = meat;
        Carb = carb;
        Vegetable = veg;
        Fruit = fruit;
        NameBrand = brand;
        Packable = pack;
        CarbBase = base;
        resetServings();

        if(Original) {
            //updateFood(true);
            if(sampleFill) {
                if(ID%2==0){
//                    alert.notify("adding ingredients");
                    Ingredients.add(new Meal(false,(num*100)+1,"ingredientOne",0,true,true,false,false,false,false,false));
                    Ingredients.add(new Meal(false,(num*100)+2,"ingredientTwo",0,true,true,false,false,false,false,false));
                    Ingredients.add(new Meal(false,(num*100)+3,"ingredientThree",0,true,true,false,false,false,false,false));
                    Ingredients.add(new Meal(false,(num*100)+4,"ingredientFour",0,true,true,false,false,false,false,false));
                    Ingredients.add(new Meal(false,(num*100)+5,"ingredientFive",0,true,true,false,false,false,false,false));
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
        Popularity = aMeal.getPopularity();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        NameBrand = aMeal.isNameBrand();
        Packable = aMeal.isPackable();
        CarbBase = aMeal.isCarbBase();

        resetServings();
        for(int i=0; i<aMeal.Ingredients.size(); i++){
            this.addIngredient(aMeal.Ingredients.get(i).getID());
        }

        print(); 
    }
    
    //Complete Copy Meal
    public Meal(boolean orig, Meal aMeal)
    {   
        Original = orig;
        ID = aMeal.getID();
        Name = aMeal.getName();
        Popularity = aMeal.getPopularity();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        NameBrand = aMeal.isNameBrand();
        Packable = aMeal.isPackable();
        CarbBase = aMeal.isCarbBase();
        resetServings();
        for(int i=0; i<aMeal.Ingredients.size(); i++){   //custom
            if(aMeal.Ingredients.get(i).getID()==0)
                Ingredients.add(new Meal(false,0,aMeal.Ingredients.get(i).getName(),0,false,false,false,false,false,false,false));
            else
                this.addIngredient(aMeal.Ingredients.get(i).getID());
        }
        //if(Original)
            //updateFood(true);

        print(); 
    }
    
    //Complete Copy Meal && Combine Names
    public Meal(Meal aMeal, Meal anotherMeal, Meal otherMeal)
    {   
        Original = false;
        ID = aMeal.getID();
        Name = aMeal.getName()+" - "+anotherMeal.getName()+" - "+otherMeal.getName();
        Popularity = aMeal.getPopularity();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        NameBrand = aMeal.isNameBrand();
        Packable = aMeal.isPackable();
        CarbBase = aMeal.isCarbBase();
        resetServings();
        this.addIngredient(anotherMeal.getID());
        this.addIngredient(otherMeal.getID());
        for(int i=0; i<aMeal.Ingredients.size(); i++){
            if(aMeal.Ingredients.get(i).getID()==0) //custom
                Ingredients.add(new Meal(false,0,aMeal.Ingredients.get(i).getName(),aMeal.getPopularity(),false,false,false,false,false,false,false));
            else if(!aMeal.Ingredients.get(i).isMeat() && !aMeal.Ingredients.get(i).isCarbBase())
                this.addIngredient(aMeal.Ingredients.get(i).getID());
        }
        //if(Original)
            //updateFood(true);

        print(); 
    }
    
    //Complete Copy Meal && Combine Names
    public Meal(Meal aMeal, Meal anotherMeal)
    {   
        Original = false;
        ID = aMeal.getID();
        Name = aMeal.getName()+" - "+anotherMeal.getName();
        Popularity = aMeal.getPopularity();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        NameBrand = aMeal.isNameBrand();
        Packable = aMeal.isPackable();
        CarbBase = aMeal.isCarbBase();
        resetServings();
        this.addIngredient(anotherMeal.getID());
        for(int i=0; i<aMeal.Ingredients.size(); i++){
            if(aMeal.Ingredients.get(i).getID()==0)  //custom
                Ingredients.add(new Meal(false,0,aMeal.Ingredients.get(i).getName(),aMeal.getPopularity(),false,false,false,false,false,false,false));
            else if(!aMeal.Ingredients.get(i).isMeat() && !aMeal.Ingredients.get(i).isCarbBase())
                this.addIngredient(aMeal.Ingredients.get(i).getID());
        }
        //if(Original)
            //updateFood(true);

        print(); 
    }
    
    //For Menu
    public Meal(String offset, Meal aMeal)
    {   
        Original = false;
        ID = aMeal.getID();
        Name = offset + aMeal.getName();
        Popularity = aMeal.getPopularity();
        Meat = aMeal.isMeat();
        Carb = aMeal.isCarb();
        Vegetable = aMeal.isVegetable();
        Fruit = aMeal.isFruit();
        NameBrand = aMeal.isNameBrand();
        Packable = aMeal.isPackable();
        CarbBase = aMeal.isCarbBase();
        //updateFood(true);
        this.resetServings();
        
        //does not copy ingredients
        
        print(); 
    }
        
    private int ID;
    private String Name;
    private int Popularity;
    
    //Categories:
    private boolean Meat;
    private boolean Carb;
    private boolean Vegetable;
    private boolean Fruit;
    private boolean NameBrand;
    private boolean Packable;
    private boolean CarbBase;

    public void setPackable(boolean Packable) {
        this.Packable = Packable;
    }

    public void setCarbBase(boolean CarbBase) {
        this.CarbBase = CarbBase;
    }

   
    public boolean isPackable() {
        return Packable;
    }

    public boolean isCarbBase() {
        return CarbBase;
    }

    
    public boolean isNameBrand() {
        return NameBrand;
    }

    public void setNameBrand(boolean NameBrand) {
        this.NameBrand = NameBrand;
    }

    public int getPopularity() {
        return Popularity;
    }

    public void setPopularity(int Popularity) {
        this.Popularity = Popularity;
    }
    
    public void increasePopularity() {
        this.Popularity++;
    }
    
    
    private boolean Original;
    private boolean Food;
    private int Servings;

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
    if(NameBrand)
        System.out.print("- Name_Brand");
    else
        System.out.print("- Generic");
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
                        if(this.isOriginal())  //link to Catalog
                          Ingredients.add(j,Catalog.get(i));
                        else //don't link to Catalog
                            Ingredients.add(j,new Meal(Catalog.get(i)));
                        added = true;
                        return;
                    }
            }
            if(!added){
                if(this.isOriginal())  //link to Catalog
                    Ingredients.add(Catalog.get(i));
                else //don't link to Catalog
                    Ingredients.add(new Meal(Catalog.get(i)));
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
                              Ingredients.add(j,Catalog.get(i));  //link to catalog
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
            if(temporaryIngredients.get(q).getID()!=0)
                alert.error("Meal not found in Catalogue - Added Anyways as Custom - " + Integer.toString(ID) + " - " + Name + "- Import -> Meal.addIngredient()");
            temporaryIngredients.get(q).setID(0);
            Ingredients.add(new Meal(temporaryIngredients.get(q)));  //copy
            temporaryIngredients.get(q).print();
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
    
    public void increaseServings() {
        Servings++;
    }
    
    public void decreaseServings() {
        if(Servings > 1)
           Servings--;
    }
    
    public void resetServings() {
        Servings = 1;
    }

    public int getServings() {
        return Servings;
    }
    
    public String toString(){
        return ID + " - " + Name;
    }
    
   
}
