/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static mealplanning.CatalogView.Catalog;
import static mealplanning.CatalogView.CatalogFoodOnly;
import static mealplanning.MealPlanning.primaryStage;
import static mealplanning.MealPlanning.catalogView;
import static mealplanning.MealPlanning.mealView;
import static mealplanning.MealPlanning.menuView;
import static mealplanning.MealPlanning.shoppingListView;
import static mealplanning.MealPlanning.alert;
import static mealplanning.MealPlanning.print;
import static mealplanning.MealPlanning.save;
import static mealplanning.MealPlanning.currentEntry;
import static mealplanning.MenuView.Menu;
import static mealplanning.MenuView.MenuRecent;
/**
 *
 * @author ethan
 */
public class MakeMealView {
    
    private final int generateCount = 5; //number of meals to generate in list
    private List<Meal> generatedMeals = new ArrayList<Meal>();
    private List<String> stringMeals = new ArrayList<String>();

    MakeMealView() {
        setMakeMeal();
        runMakeMeal();
    }
    //MENU
    private ObservableList<Meal> Menu = MenuView.Menu;

    private CatalogView catalogView = MealPlanning.catalogView;
    private MealView mealView = MealPlanning.mealView;
    private MenuView menuView = MealPlanning.menuView;
    private ShoppingListView shoppingListView = MealPlanning.shoppingListView;
    private AlertBox alert = MealPlanning.alert;
    private Save save = MealPlanning.save;
    private boolean print = MealPlanning.print;
    private int currentEntry = MealPlanning.currentEntry;

    public ObservableList<Meal> Catalog = CatalogView.Catalog;
    public ObservableList<Meal> FoodOnly = CatalogView.CatalogFoodOnly;

    private Resources R = new Resources();
    public final String indent = "     -";
    
        //Temporary Meal Variations
    private ObservableList<Meal> MakeMeal = FXCollections.observableArrayList();
    private ObservableList<Meal> allMeals = FXCollections.observableArrayList();
    private ObservableList<Meal> packableMeals = FXCollections.observableArrayList();
    private ObservableList<Meal> diningMeals = FXCollections.observableArrayList();
    private ObservableList<Meal> packableVegetables = FXCollections.observableArrayList();
    private ObservableList<Meal> packableFruits = FXCollections.observableArrayList();
    private ObservableList<Meal> allCarbs = FXCollections.observableArrayList();
    private ObservableList<Meal> allFruits = FXCollections.observableArrayList();
    private ObservableList<Meal> allVegetables = FXCollections.observableArrayList();


    public void updateMakeMeal() {
        MakeMeal.clear();
        MakeMeal.add(new Meal( -1, "*****Make Meal*****"));
        MakeMeal.add(new Meal( -2, "*******************"));
//        //set all Meal Indicators to false
        breakfast.selectedProperty().set(false);
        mondayLunch.selectedProperty().set(false);
        mondayDinner.selectedProperty().set(false);
        tuesdayLunch.selectedProperty().set(false);
        tuesdayDinner.selectedProperty().set(false);
        wednessdayLunch.selectedProperty().set(false);
        wednessdayDinner.selectedProperty().set(false);
        thursdayLunch.selectedProperty().set(false);
        thursdayDinner.selectedProperty().set(false);
        fridayLunch.selectedProperty().set(false);
        fridayDinner.selectedProperty().set(false);
        saturdayLunch.selectedProperty().set(false);
        saturdayDinner.selectedProperty().set(false);
        sundayLunch.selectedProperty().set(false);
        sundayDinner.selectedProperty().set(false);

        updateAllIndicators();

        menuTable.setItems(null);
        menuTable.setItems(Menu);
        menuTable.refresh();
        
        return;
    }
        
            //***********Indicator Operations****************************************  
     public String makeMealIndicatorsUpdate(Meal status) {
        String current = null;
        boolean skip = false;
        boolean solo = true;
        for(int i=1; i<MakeMeal.size()-1; i++) {
            current = R.updateIndicators(status,MakeMeal.get(i));
            //Adding to Recent Meals
            if(MakeMeal.get(i).getName().charAt(0) != indent.charAt(0)){
                skip = false;
                solo = false;
            }else if(solo) {
                skip = false;
            }else{
                skip = true;
            }

            if(!skip)
                this.addRecent(MakeMeal.get(i));

        }
        return current;
    }

    public void updateAllIndicators() {  //exact copy in MenuView
        try {
            int j = 0;
            for (int i = 0; i < Menu.size(); i++) {
                if (Menu.get(i).getID() < 0) {
                    j++;
                } else if (Menu.get(i).getID() > 0) {
                    indicators.get(j - 1).setText(R.updateIndicators(status.get(j - 1), Menu.get(i)));
                }
            }
        } catch (IndexOutOfBoundsException exe) {
            alert.error("Menu has Invalid negative Meal ID's - IndexOutOfBoundsException - MenuView.updateAllIndicators()");
        }

    }
    
        //***********MakeMeal Operations****************************************    
   public void makeMealAdd(Meal theMeal) {
       if(theMeal == null)
           return;
    try {       
        if (theMeal.Ingredients.isEmpty()) {  //add whole Meal from Catalog, indent at top of day's meal
            makeMealInsert(theMeal, 1, 2);  //add first
        } else {  //adding whole meal including ingredients
            //Add Meal Name title and then following ingredient
            MakeMeal.add(MakeMeal.size()-1, new Meal(false, theMeal));  //add last with header
            makeMealInsert(theMeal, MakeMeal.size()-1, 2);
        }

    } catch (NullPointerException exe) {
    }

    return;
}

    public void makeMealInsert(Meal theMeal, int place, int remainingDepth) {
        if (theMeal.Ingredients.isEmpty() || remainingDepth <= 0) { //adding Meal, no ingredients
            MakeMeal.add(place, new Meal(indent, theMeal));
        } else { //Add All Ingredients of selectedIngredient
            MakeMeal.add(place, new Meal(indent, theMeal.Ingredients.get(theMeal.Ingredients.size() - 1)));
            for (int i = 0; i < theMeal.Ingredients.size() - 1; i++) {
                makeMealInsert(theMeal.Ingredients.get(i), place + 1, --remainingDepth);  //recursion to add sussesive ingredients of ingredients.
            }
        }
        return;
    }
            
    public void makeMealAdding(int startID){
        try {
            int start = getMenuIndex(startID);
            int current = start + 1;  //For Menu list
            int now = 1; //for MakeMeal List

            while(MakeMeal.get(now).getName().charAt(0) == indent.charAt(0)) {  //add whole Meal from Catalog, indent at top of day's meal
                Menu.add(current, new Meal(false, MakeMeal.get(now)));
                now++;
            }

            //adding Meals with Ingredients to end of Menu Meal
            int end = getMenuIndex(startID - 1);
            if (end == 0) //didn't find, last one.
            {
                end = Menu.size() - 1;
            }
            while(now<MakeMeal.size()-1) {
                Menu.add(end, new Meal(false, MakeMeal.get(now)));
                now++;
                end++;
            }
            } catch (NullPointerException exe) {
            }
        return;
    }

    public void makeMealInitiateAdd() {
         if (MakeMeal.size()>2) {
             int none = 0;
                if (breakfast.isSelected()) {
                    Ibreakfast.setText(makeMealIndicatorsUpdate(statusbreakfast));
                    makeMealAdding(-1);
                } else none++;
                if (mondayLunch.isSelected()) {
                    ImondayLunch.setText(makeMealIndicatorsUpdate(statusmondayLunch));
                    makeMealAdding(-2);
                } else none++;
                if (mondayDinner.isSelected()) {
                    ImondayDinner.setText(makeMealIndicatorsUpdate(statusmondayDinner));
                    makeMealAdding(-3);
                } else none++;
                if (tuesdayLunch.isSelected()) {
                    ItuesdayLunch.setText(makeMealIndicatorsUpdate(statustuesdayLunch));
                    makeMealAdding(-4);
                } else none++;
                if (tuesdayDinner.isSelected()) {
                    ItuesdayDinner.setText(makeMealIndicatorsUpdate(statustuesdayDinner));
                    makeMealAdding(-5);
                } else none++;
                if (wednessdayLunch.isSelected()) {
                    IwednessdayLunch.setText(makeMealIndicatorsUpdate(statuswednessdayLunch));
                    makeMealAdding(-6);
                } else none++;
                if (wednessdayDinner.isSelected()) {
                    IwednessdayDinner.setText(makeMealIndicatorsUpdate(statuswednessdayDinner));
                    makeMealAdding(-7);
                } else none++;
                if (thursdayLunch.isSelected()) {
                    IthursdayLunch.setText(makeMealIndicatorsUpdate(statusthursdayLunch));
                    makeMealAdding(-8);
                } else none++;
                if (thursdayDinner.isSelected()) {
                    IthursdayDinner.setText(makeMealIndicatorsUpdate(statusthursdayDinner));
                    makeMealAdding(-9);
                } else none++;
                if (fridayLunch.isSelected()) {
                    IfridayLunch.setText(makeMealIndicatorsUpdate(statusfridayLunch));
                    makeMealAdding(-10);
                } else none++;
                if (fridayDinner.isSelected()) {
                    IfridayDinner.setText(makeMealIndicatorsUpdate(statusfridayDinner));
                    makeMealAdding(-11);
                } else none++;
                if (saturdayLunch.isSelected()) {
                    IsaturdayLunch.setText(makeMealIndicatorsUpdate(statussaturdayLunch));
                    makeMealAdding(-12);
                } else none++;
                if (saturdayDinner.isSelected()) {
                    IsaturdayDinner.setText(makeMealIndicatorsUpdate(statussaturdayDinner));
                    makeMealAdding(-13);
                } else none++;
                if (sundayLunch.isSelected()) {
                    IsundayLunch.setText(makeMealIndicatorsUpdate(statussundayLunch));
                    makeMealAdding(-14);
                } else none++;
                if (sundayDinner.isSelected()) {
                    IsundayDinner.setText(makeMealIndicatorsUpdate(statussundayDinner));
                    makeMealAdding(-15);
                } else none++;
                if(none == 15)
                    new AlertBox().notify("Please select a meal to add to Menu.");
                
            }
        return;
    }

            //***********Menu Operations****************************************  
    public int getMenuIndex(int ID) {
        for (int i = 0; i < Menu.size(); i++) {
            if (Menu.get(i).getID() == ID) {
                return i;
            }
        }
        return 0;  //default first place    
    }

    public void addRecent(Meal theMeal){  //Also exact copy of method indepent in MenuView
        try {
            if (theMeal.getID() != 0) {
                for (int j = 0; j < Catalog.size(); j++) {
                    if (Catalog.get(j).getID() == theMeal.getID()) {
                        Catalog.get(j).increasePopularity();
                        Catalog.get(j).setLastEntry(currentEntry);
                        currentEntry++;
                    MenuRecent.add(0,new Meal(false,Catalog.get(j)));  //copy
//                            MenuRecent.add(0, Catalog.get(j));  //link
                        if (MenuRecent.size() > 1) {
                            for (int l = 1; l < MenuRecent.size(); l++) {
                                if (MenuRecent.get(l).getID() == theMeal.getID()) {
                                    MenuRecent.remove(l);
                                }
                            }
                        }
                        if (MenuRecent.size() > 20) {
                            MenuRecent.remove(MenuRecent.size() - 1);
                        }
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            alert.error("adding/removing from RecentList - IndexOutOfBoundsException - MenuView.addRecent()");
        } catch (NullPointerException e) {
            alert.error("adding/removing from RecentList - NullPointerException - MenuView.addRecent()");
        }
    }

     public void menuRemoveMeal(int mealID) {  //exact Copies in MenuView
        try {
            int start = getMenuIndex(mealID);
            int current = start + 1;
            while ((Menu.get(current).getID() >= 0) && (current < Menu.size() - 1)) {
                Menu.remove(current);
            }
        } catch (NullPointerException exe) {
        }
        return;
    }

    public void menuInitiateRemoveMeal() {  //exact Copies in MenuView
        if (breakfast.isSelected()) {
            Ibreakfast.setText(R.resetIndicators(statusbreakfast));
            menuRemoveMeal(-1);
        }
        if (mondayLunch.isSelected()) {
            ImondayLunch.setText(R.resetIndicators(statusmondayLunch));
            menuRemoveMeal(-2);
        }
        if (mondayDinner.isSelected()) {
            ImondayDinner.setText(R.resetIndicators(statusmondayDinner));
            menuRemoveMeal(-3);
        }
        if (tuesdayLunch.isSelected()) {
            ItuesdayLunch.setText(R.resetIndicators(statustuesdayLunch));
            menuRemoveMeal(-4);
        }
        if (tuesdayDinner.isSelected()) {
            ItuesdayDinner.setText(R.resetIndicators(statustuesdayDinner));
            menuRemoveMeal(-5);
        }
        if (wednessdayLunch.isSelected()) {
            IwednessdayLunch.setText(R.resetIndicators(statuswednessdayLunch));
            menuRemoveMeal(-6);
        }
        if (wednessdayDinner.isSelected()) {
            IwednessdayDinner.setText(R.resetIndicators(statuswednessdayDinner));
            menuRemoveMeal(-7);
        }
        if (thursdayLunch.isSelected()) {
            IthursdayLunch.setText(R.resetIndicators(statusthursdayLunch));
            menuRemoveMeal(-8);
        }
        if (thursdayDinner.isSelected()) {
            IthursdayDinner.setText(R.resetIndicators(statusthursdayDinner));
            menuRemoveMeal(-9);
        }
        if (fridayLunch.isSelected()) {
            IfridayLunch.setText(R.resetIndicators(statusfridayLunch));
            menuRemoveMeal(-10);
        }
        if (fridayDinner.isSelected()) {
            IfridayDinner.setText(R.resetIndicators(statusfridayDinner));
            menuRemoveMeal(-11);
        }
        if (saturdayLunch.isSelected()) {
            IsaturdayLunch.setText(R.resetIndicators(statussaturdayLunch));
            menuRemoveMeal(-12);
        }
        if (saturdayDinner.isSelected()) {
            IsaturdayDinner.setText(R.resetIndicators(statussaturdayDinner));
            menuRemoveMeal(-13);
        }
        if (sundayLunch.isSelected()) {
            IsundayLunch.setText(R.resetIndicators(statussundayLunch));
            menuRemoveMeal(-14);
        }
        if (sundayDinner.isSelected()) {
            IsundayDinner.setText(R.resetIndicators(statussundayDinner));
            menuRemoveMeal(-15);
        }
        return;
    }

    public void menuInsert(Meal theMeal, int place, int remainingDepth) {  //Exact copy in MenuView
        if (theMeal.Ingredients.isEmpty() || remainingDepth <= 0) { //adding Meal, no ingredients
            Menu.add(place, new Meal(indent, theMeal));
        } else { //Add All Ingredients of selectedIngredient
            Menu.add(place, new Meal(indent, theMeal.Ingredients.get(theMeal.Ingredients.size() - 1)));
            for (int i = 0; i < theMeal.Ingredients.size() - 1; i++) {
                menuInsert(theMeal.Ingredients.get(i), place + 1, --remainingDepth);  //recursion to add sussesive ingredients of ingredients.
            }
        }
        return;
    }
    
    
        //***********MakeMeal Calculations and Setups****************************************  
    public String mealToString(Meal aMeal, Meal veg, Meal fruit){
        String S = "";
        if(fruit != null)
            S = S + fruit.getID() + " - " + fruit.getName();
        if(veg != null)
            S = S + "\n" +  veg.getID() + " - " + veg.getName();
        if(aMeal != null) {
                S = S + "\n" + aMeal.getID() + " - " + aMeal.getName();
            for(int i = 0; i<aMeal.Ingredients.size(); i++){
                if(aMeal.Ingredients.get(i).isMeat())
                    S = S + "\n" + indent + aMeal.Ingredients.get(i).getID() + " - " + aMeal.Ingredients.get(i).getName();
                else if(aMeal.Ingredients.get(i).isCarbBase())
                    S = S + "\n" + indent + aMeal.Ingredients.get(i).getID() + " - " + aMeal.Ingredients.get(i).getName();
                else if(aMeal.Ingredients.get(i).isNameBrand())
                    S = S + "\n" + indent + aMeal.Ingredients.get(i).getID() + " - " + aMeal.Ingredients.get(i).getName();
            }
        }
        S = S + "\n"; //blank line
        return S;
    }
    
      public void sortMealList(ObservableList<Meal> L){ //sorts by popularity and lastEntry into sorting value, high to low
    try {
        //sort by popularity
            for(int i = 1; i<L.size(); i++) {  //Insertion Sort Algorithm   //largest to smallest
                int j = i;  
                Meal key;
                while (j > 0 && L.get(j-1).getPopularity()< L.get(j).getPopularity())  {    //move upwards
                    key = L.get(j);
                    L.remove(j);
                    L.add(j-1,key);
                    j--; 
                } 
            }
        //addpopularty rank to sortingValue    
            for(int k=0;k<L.size();k++){
                L.get(k).setSortingValue((double) k+1);                //label smallest to largest, ranking
            }
            
            for(int i = 1; i<L.size(); i++) {  //Insertion Sort Algorithm   //largest to smallest
                int j = i;  
                Meal key;
                while (j > 0 && (currentEntry - L.get(j-1).getLastEntry())< (currentEntry - L.get(j).getLastEntry()))  {    //move upwards, difference is
                    key = L.get(j);
                    L.remove(j);
                    L.add(j-1,key);
                    j--; 
                } 
            }
            
         //addlastEntry rank to sortingValue    
            for(int k=0;k<L.size();k++){
                L.get(k).setSortingValue((double) k+1 +  L.get(k).getSortingValue());         //label smallest to largest, ranking   
            }
            
            for(int i = 1; i<L.size(); i++) {  //Insertion Sort Algorithm   //smallest to largest
                int j = i;  
                Meal key;
                while (j > 0 && L.get(j-1).getSortingValue()> L.get(j).getSortingValue())  {    //move upwards
                    key = L.get(j);
                    L.remove(j);
                    L.add(j-1,key);
                    j--; 
                } 
            }
        }catch(NullPointerException exe) {
            alert.error("Insertion Algorithm Failed sorting - may have lost meals - NullPointerException - MealPlanning.sortMealList()");
        } catch(IndexOutOfBoundsException exe){
           alert.error("<><> Insertion Algorithm Failed sorting - may have lost meals - IndexOutOfBoundsException - <><> - MealPlanning.sortMealList()");
       }
  }
  
public void generateMealVariations(){
    allMeals.clear();
    packableMeals.clear();
    diningMeals.clear();
    packableVegetables.clear();
    packableFruits.clear();
    allCarbs.clear();
    allFruits.clear();
    allVegetables.clear();

    //Figure PackableMeals & DiningMeals

    for(int i=0; i<Catalog.size();i++) {
        allMeals.add(Catalog.get(i));
        if(Catalog.get(i).getID()>=10 && !Catalog.get(i).isCookingIngredient()){
            if(!Catalog.get(i).Ingredients.isEmpty()) {  //Meal
                boolean added = false;
                for(int j=0; j<Catalog.get(i).Ingredients.size(); j++) {
                    if(Catalog.get(i).Ingredients.get(j).isMeat()){
                        System.out.println();
                    for(int k=0; k<Catalog.get(i).Ingredients.size(); k++) {
                        if(Catalog.get(i).Ingredients.get(k).isCarbBase()){  //make so still adds if only has two of three catagories
                            added = true;
                            Meal tempMeal = new Meal(Catalog.get(i), Catalog.get(i).Ingredients.get(j), Catalog.get(i).Ingredients.get(k));
                            diningMeals.add(tempMeal);
                            if(Catalog.get(i).isPackable())
                                packableMeals.add(tempMeal);
                        } 
                    }
                    if(!added) { //meat only match
                        added = true;
                        Meal tempMeal = new Meal(Catalog.get(i), Catalog.get(i).Ingredients.get(j));
                        diningMeals.add(tempMeal);
                        if(Catalog.get(i).isPackable())
                            packableMeals.add(tempMeal);
                    }
                    }
                    if(!added) { //carbBase onlyMatch
                        for(int l=0; l<Catalog.get(i).Ingredients.size(); l++) {
                        if(Catalog.get(i).Ingredients.get(l).isCarbBase()){  //make so still adds if only has two of three catagories
                            added = true;
                            Meal tempMeal = new Meal(Catalog.get(i), Catalog.get(i).Ingredients.get(l));
                            diningMeals.add(tempMeal);
                            if(Catalog.get(i).isPackable())
                                packableMeals.add(tempMeal);
                        } 
                        }
                    }
                }
                if(!added) {  //add solo - only vegetables and fruit meal or customs
                    Meal tempMeal = new Meal(false, Catalog.get(i));
                    diningMeals.add(tempMeal);
                    if(Catalog.get(i).isPackable())
                        packableMeals.add(tempMeal);
                }
            }else{            //Ingredients
                if(Catalog.get(i).isVegetable() && Catalog.get(i).isPackable()) //only vegetable and packable
                   packableVegetables.add(Catalog.get(i));
                if(Catalog.get(i).isFruit() && Catalog.get(i).isPackable()) //only fruit and packable
                    packableFruits.add(Catalog.get(i));
                if(Catalog.get(i).isCarb()) //only carb
                    allCarbs.add(Catalog.get(i));
                if(Catalog.get(i).isFruit()) //only Fruit
                    allFruits.add(Catalog.get(i));
                if(Catalog.get(i).isVegetable()) //only Veggie
                    allVegetables.add(Catalog.get(i));
            }
        }             
    }

    //Sort Lists by Popularity
    this.sortMealList(allMeals);
    this.sortMealList(packableMeals);
    this.sortMealList(diningMeals);
    this.sortMealList(packableVegetables);
    this.sortMealList(packableFruits);
    this.sortMealList(allCarbs);
    this.sortMealList(allFruits);
    this.sortMealList(allVegetables);
    
    return;
    }


public void runMakeMeal() {
    makeMealTypeOptions.clear();
    makeMealTypeOptions.add("Packable");
    makeMealTypeOptions.add("Dining");
    makeMealType.setPrefWidth(250);
    makeMealType.setPromptText("Select Meal Type");
    makeMealVariations.setPrefWidth(250);
    makeMealVegetables.setPrefWidth(250);
    makeMealFruits.setPrefWidth(250);
    makeMealCarbs.setPrefWidth(250);
    makeMealCatalog.setPrefWidth(250);

    makeMealDisplay.setItems(null);
    makeMealDisplay.setItems(MakeMeal);
    updateMakeMeal();
    
    //MENU
    menuRemoveButton.setOnAction(e -> {
        try {
            Meal selectedItem = menuTable.getSelectionModel().getSelectedItem();
            int currentIndex = menuTable.getSelectionModel().getSelectedIndex();
            if (selectedItem.getID() >= 0) {
                menuTable.getItems().remove(selectedItem);  //removing
            }
            while (menuTable.getSelectionModel().getSelectedIndex() < Menu.size() - 1) { //auto select next
                currentIndex = menuTable.getSelectionModel().getSelectedIndex();
                if ((Menu.get(currentIndex + 1).getName().charAt(0) == indent.charAt(0)) //next is ingredient
                        || (menuTable.getSelectionModel().getSelectedItem().getID() < 0) //current is subject
                        || ((menuTable.getSelectionModel().getSelectedItem().getID() < 0)
                        && (Menu.get(currentIndex + 1).getName().charAt(0) != indent.charAt(0))) // Current is meal subject and next is meal title
                        || ((menuTable.getSelectionModel().getSelectedItem().getID() < 0)
                        && (Menu.get(currentIndex + 1).getID() < 0)) //Current and Next are Meal subjects
                        || ((Menu.get(currentIndex).getName().charAt(0) != indent.charAt(0)
                        && (Menu.get(currentIndex + 1).getName().charAt(0) == indent.charAt(0))))) //Current is meal title and next is ingredient
                {
                    menuTable.getSelectionModel().selectNext();  //next
                } else {
                    break;  //stay
                }
            }
        } catch (NullPointerException exe) {
        }
    });

    menuAddCustom.setOnAction(e -> {
        try {
            if (breakfast.isSelected()) {
                menuInsert(new Meal(00, menuAddCustom.getText()), getMenuIndex(-1) + 1, 0);
            }
            if (mondayLunch.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-2) + 1, 0);
            }
            if (mondayDinner.isSelected()) {
                menuInsert(new Meal(00, menuAddCustom.getText()), getMenuIndex(-3) + 1, 0);
            }
            if (tuesdayLunch.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-4) + 1, 0);
            }
            if (tuesdayDinner.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-5) + 1, 0);
            }
            if (wednessdayLunch.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-6) + 1, 0);
            }
            if (wednessdayDinner.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-7) + 1, 0);
            }
            if (thursdayLunch.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-8) + 1, 0);
            }
            if (thursdayDinner.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-9) + 1, 0);
            }
            if (fridayLunch.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-10) + 1, 0);
            }
            if (fridayDinner.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-11) + 1, 0);
            }
            if (saturdayLunch.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-12) + 1, 0);
            }
            if (saturdayDinner.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-13) + 1, 0);
            }
            if (sundayLunch.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-14) + 1, 0);
            }
            if (sundayDinner.isSelected()) {
                menuInsert(new Meal( 00, menuAddCustom.getText()),getMenuIndex(-15) + 1, 0);
            }

            menuAddCustom.clear();
        } catch (NullPointerException exe) {
        }
    });

    menuClearMealButton.setOnAction(e -> {
        menuInitiateRemoveMeal();
    });

    selectAll.setOnAction(e -> {
        mondayLunch.selectedProperty().set(true);
        mondayDinner.selectedProperty().set(true);
        tuesdayLunch.selectedProperty().set(true);
        tuesdayDinner.selectedProperty().set(true);
        wednessdayLunch.selectedProperty().set(true);
        wednessdayDinner.selectedProperty().set(true);
        thursdayLunch.selectedProperty().set(true);
        thursdayDinner.selectedProperty().set(true);
        fridayLunch.selectedProperty().set(true);
        fridayDinner.selectedProperty().set(true);
        saturdayLunch.selectedProperty().set(true);
        saturdayDinner.selectedProperty().set(true);
        sundayLunch.selectedProperty().set(true);
        sundayDinner.selectedProperty().set(true);
    });

    deselectAll.setOnAction(e -> {
        breakfast.selectedProperty().set(false);
        mondayLunch.selectedProperty().set(false);
        mondayDinner.selectedProperty().set(false);
        tuesdayLunch.selectedProperty().set(false);
        tuesdayDinner.selectedProperty().set(false);
        wednessdayLunch.selectedProperty().set(false);
        wednessdayDinner.selectedProperty().set(false);
        thursdayLunch.selectedProperty().set(false);
        thursdayDinner.selectedProperty().set(false);
        fridayLunch.selectedProperty().set(false);
        fridayDinner.selectedProperty().set(false);
        saturdayLunch.selectedProperty().set(false);
        saturdayDinner.selectedProperty().set(false);
        sundayLunch.selectedProperty().set(false);
        sundayDinner.selectedProperty().set(false);
    });
    
    //MakeMeal
    
    makeMealType.setOnAction(e -> {
        this.generateMealVariations();
        makeMealCatalog.setItems(null);
        makeMealCatalog.setItems(allMeals);
        makeMealCarbs.setItems(null);
        makeMealCarbs.setItems(allCarbs);
        if(makeMealType.getSelectionModel().getSelectedItem().equals("Packable")){
            makeMealVariations.setItems(packableMeals);
            makeMealVegetables.setItems(packableVegetables);
            makeMealFruits.setItems(packableFruits);
        }
        else {
            makeMealVariations.setItems(diningMeals);
            makeMealVegetables.setItems(allVegetables);
            makeMealFruits.setItems(allFruits);
        }
        makeMealVariations.setPromptText("Select Meal Variation");
        makeMealVegetables.setPromptText("Select Vegetable");
        makeMealFruits.setPromptText("Select Fruit");
        makeMealCarbs.setPromptText("Select Carbohydrate");
        makeMealCatalog.setPromptText("Select From Catalog");
    });
    makeMealVariations.setOnAction(e -> {
        makeMealAdd(makeMealVariations.getSelectionModel().getSelectedItem());
    });
    makeMealVegetables.setOnAction(e -> {
        makeMealAdd(makeMealVegetables.getSelectionModel().getSelectedItem());
    });
    makeMealFruits.setOnAction(e -> {
        makeMealAdd(makeMealFruits.getSelectionModel().getSelectedItem());
    });
    makeMealCarbs.setOnAction(e -> {
        makeMealAdd(makeMealCarbs.getSelectionModel().getSelectedItem());
    });
    makeMealCatalog.setOnAction(e -> {
            makeMealAdd(makeMealCatalog.getSelectionModel().getSelectedItem());
    });
    makeMealCustomAdd.setOnAction(e -> {
        makeMealAdd(new Meal(00, makeMealCustomAdd.getText()));
        makeMealCustomAdd.clear();
    });
    makeMealRemoveButton.setOnAction(e -> {
        try {
                Meal selectedItem = makeMealDisplay.getSelectionModel().getSelectedItem();
                int currentIndex = makeMealDisplay.getSelectionModel().getSelectedIndex();
                if (selectedItem.getID() >= 0) {
                    makeMealDisplay.getItems().remove(selectedItem);  //removing
                }
                while (makeMealDisplay.getSelectionModel().getSelectedIndex() < MakeMeal.size() - 1) { //auto select next
                    currentIndex = makeMealDisplay.getSelectionModel().getSelectedIndex();
                    if ((MakeMeal.get(currentIndex + 1).getName().charAt(0) == indent.charAt(0)) //next is ingredient
                            || (makeMealDisplay.getSelectionModel().getSelectedItem().getID() < 0) //current is subject
                            || ((makeMealDisplay.getSelectionModel().getSelectedItem().getID() < 0)
                            && (MakeMeal.get(currentIndex + 1).getName().charAt(0) != indent.charAt(0))) // Current is meal subject and next is meal title
                            || ((makeMealDisplay.getSelectionModel().getSelectedItem().getID() < 0)
                            && (MakeMeal.get(currentIndex + 1).getID() < 0)) //Current and Next are Meal subjects
                            || ((MakeMeal.get(currentIndex).getName().charAt(0) != indent.charAt(0)
                            && (MakeMeal.get(currentIndex + 1).getName().charAt(0) == indent.charAt(0))))) //Current is meal title and next is ingredient
                    {
                        makeMealDisplay.getSelectionModel().selectNext();  //next
                    } else {
                        break;  //stay
                    }
                }
            } catch (NullPointerException exe) {
            }
    });
    makeMealClearButton.setOnAction(e -> {
        MakeMeal.clear();
        MakeMeal.add(new Meal( -1, "*****Make Meal*****"));
        MakeMeal.add(new Meal( -2, "*******************"));
    });   

    addMade.setOnAction(e -> {
        if(catalogView.IDset()){
            makeMealInitiateAdd();
   }});
    
    makeMealRecommendButton.setOnAction(e -> {
        try {
        this.generatedMeals.clear();
        this.stringMeals.clear();  
        for(int i=0; i<this.generateCount;i++){
            double topPercent = (1.0/(generateCount+1)) *(i+1);
            if(makeMealType.getSelectionModel().getSelectedItem().equals("Packable")){
                if(!packableFruits.isEmpty())
                  generatedMeals.add(packableFruits.get(R.random(0, (int) (packableFruits.size()*topPercent))));
                else 
                  generatedMeals.add(null);
                if(!packableVegetables.isEmpty())
                    generatedMeals.add(packableVegetables.get(R.random(0, (int) (packableVegetables.size()*topPercent))));
                else 
                  generatedMeals.add(null);
                if(!packableMeals.isEmpty())
                    generatedMeals.add(packableMeals.get(R.random(0, (int) (packableMeals.size()*topPercent))));
                else 
                  generatedMeals.add(null);               
            }
            else { //dining
                if(!allFruits.isEmpty())
                    generatedMeals.add(allFruits.get(R.random(0, (int) (allFruits.size()*topPercent))));
                else 
                  generatedMeals.add(null);
                if(!allVegetables.isEmpty())
                    generatedMeals.add(allVegetables.get(R.random(0, (int) (allVegetables.size()*topPercent))));
                else 
                  generatedMeals.add(null);
                if(!diningMeals.isEmpty())
                    generatedMeals.add(diningMeals.get(R.random(0, (int) (diningMeals.size()*topPercent))));
                else 
                  generatedMeals.add(null);                
            }      

            stringMeals.add(this.mealToString(generatedMeals.get(generatedMeals.size()-1), generatedMeals.get(generatedMeals.size()-2), generatedMeals.get(generatedMeals.size()-3)));

        }
        int selection = new AlertBox().getSelection(stringMeals, "Select a Recommended Meal");
        if(selection!=0 && !generatedMeals.isEmpty()){
            selection = 3*selection;
            makeMealAdd(generatedMeals.get(selection-1));
            makeMealAdd(generatedMeals.get(selection-2));
            makeMealAdd(generatedMeals.get(selection-3));
        }
        } catch (NullPointerException exe) {
        new AlertBox().notify("Please select a Meal Type to Begin.");
        } catch(IndexOutOfBoundsException exe){
        alert.error("IndexOutOfBoundsException - MenuView.makeMealRandomButton.setOnAction()");
        }
    });
    
    makeMealRandomButton.setOnAction(e -> {
        try {
        this.generatedMeals.clear();
        this.stringMeals.clear();       
        for(int i=0; i<this.generateCount;i++){
            int nextID = generatedMeals.size();
            if(makeMealType.getSelectionModel().getSelectedItem().equals("Packable")){
                if(!packableFruits.isEmpty())
                  generatedMeals.add(packableFruits.get(R.random(0, packableFruits.size()-1)));
                else 
                  generatedMeals.add(null);
                if(!packableVegetables.isEmpty())
                    generatedMeals.add(packableVegetables.get(R.random(0,packableVegetables.size()-1)));
                else 
                  generatedMeals.add(null);
                if(!packableMeals.isEmpty())
                    generatedMeals.add(packableMeals.get(R.random(0,packableMeals.size()-1)));
                else 
                  generatedMeals.add(null);
            }
            else { //dining
                if(!allFruits.isEmpty())
                    generatedMeals.add(allFruits.get(R.random(0,allFruits.size()-1)));
                else 
                  generatedMeals.add(null);
                if(!allVegetables.isEmpty())
                    generatedMeals.add(allVegetables.get(R.random(0,allVegetables.size()-1)));
                else 
                  generatedMeals.add(null);
                if(!diningMeals.isEmpty())
                    generatedMeals.add(diningMeals.get(R.random(0,diningMeals.size()-1)));
                else 
                  generatedMeals.add(null);
            }      

            stringMeals.add(this.mealToString(generatedMeals.get(generatedMeals.size()-1), generatedMeals.get(generatedMeals.size()-2), generatedMeals.get(generatedMeals.size()-3)));
        }
        int selection = new AlertBox().getSelection(stringMeals, "Select a Random Meal");
        if(selection!=0 && !generatedMeals.isEmpty()){
            selection = 3*selection;
            makeMealAdd(generatedMeals.get(selection-1));
            makeMealAdd(generatedMeals.get(selection-2));
            makeMealAdd(generatedMeals.get(selection-3));
        }
        } catch (NullPointerException exe) {
        new AlertBox().notify("Please select a Meal Type to Begin.");
        } catch(IndexOutOfBoundsException exe){
        alert.error("IndexOutOfBoundsException - MenuView.makeMealRandomButton.setOnAction()");
        }
    });
               
    return; 
}

//CheckBoxes
    private CheckBox breakfast = new CheckBox("Breakfast");
    private CheckBox mondayLunch = new CheckBox("Monday Lunch");
    private CheckBox mondayDinner = new CheckBox("Monday Dinner");
    private CheckBox tuesdayLunch = new CheckBox("Tuesday Lunch");
    private CheckBox tuesdayDinner = new CheckBox("Tuesday Dinner");
    private CheckBox wednessdayLunch = new CheckBox("Wednesday Lunch");
    private CheckBox wednessdayDinner = new CheckBox("Wednesday Dinner");
    private CheckBox thursdayLunch = new CheckBox("Thursday Lunch");
    private CheckBox thursdayDinner = new CheckBox("Thursday Dinner");
    private CheckBox fridayLunch = new CheckBox("Friday Lunch");
    private CheckBox fridayDinner = new CheckBox("Friday Dinner");
    private CheckBox saturdayLunch = new CheckBox("Saturday Lunch");
    private CheckBox saturdayDinner = new CheckBox("Saturday Dinner");
    private CheckBox sundayLunch = new CheckBox("Sunday Lunch");
    private CheckBox sundayDinner = new CheckBox("Sunday Dinner");
//Indicators Status Meals:
    private Meal statusbreakfast = new Meal(R.theBlankMeal);
    private Meal statusmondayLunch = new Meal(R.theBlankMeal);
    private Meal statusmondayDinner = new Meal(R.theBlankMeal);
    private Meal statustuesdayLunch = new Meal(R.theBlankMeal);
    private Meal statustuesdayDinner = new Meal(R.theBlankMeal);
    private Meal statuswednessdayLunch = new Meal(R.theBlankMeal);
    private Meal statuswednessdayDinner = new Meal(R.theBlankMeal);
    private Meal statusthursdayLunch = new Meal(R.theBlankMeal);
    private Meal statusthursdayDinner = new Meal(R.theBlankMeal);
    private Meal statusfridayLunch = new Meal(R.theBlankMeal);
    private Meal statusfridayDinner = new Meal(R.theBlankMeal);
    private Meal statussaturdayLunch = new Meal(R.theBlankMeal);
    private Meal statussaturdayDinner = new Meal(R.theBlankMeal);
    private Meal statussundayLunch = new Meal(R.theBlankMeal);
    private Meal statussundayDinner = new Meal(R.theBlankMeal);
    private List<Meal> status = new ArrayList();

//Indicators
    private Label Ibreakfast = new Label(R.updateIndicators(statusbreakfast, R.theBlankMeal));
    private Label ImondayLunch = new Label(R.updateIndicators(statusmondayLunch, R.theBlankMeal));
    private Label ImondayDinner = new Label(R.updateIndicators(statusmondayDinner, R.theBlankMeal));
    private Label ItuesdayLunch = new Label(R.updateIndicators(statustuesdayLunch, R.theBlankMeal));
    private Label ItuesdayDinner = new Label(R.updateIndicators(statustuesdayDinner, R.theBlankMeal));
    private Label IwednessdayLunch = new Label(R.updateIndicators(statuswednessdayLunch, R.theBlankMeal));
    private Label IwednessdayDinner = new Label(R.updateIndicators(statuswednessdayDinner, R.theBlankMeal));
    private Label IthursdayLunch = new Label(R.updateIndicators(statusthursdayLunch, R.theBlankMeal));
    private Label IthursdayDinner = new Label(R.updateIndicators(statusthursdayDinner, R.theBlankMeal));
    private Label IfridayLunch = new Label(R.updateIndicators(statusfridayLunch, R.theBlankMeal));
    private Label IfridayDinner = new Label(R.updateIndicators(statusfridayDinner, R.theBlankMeal));
    private Label IsaturdayLunch = new Label(R.updateIndicators(statussaturdayLunch, R.theBlankMeal));
    private Label IsaturdayDinner = new Label(R.updateIndicators(statussaturdayDinner, R.theBlankMeal));
    private Label IsundayLunch = new Label(R.updateIndicators(statussundayLunch, R.theBlankMeal));
    private Label IsundayDinner = new Label(R.updateIndicators(statussundayDinner, R.theBlankMeal));
    private List<Label> indicators = new ArrayList();

    private Button selectAll = R.makeButton("ALL", 75, false);
    private Button deselectAll = R.makeButton("NONE", 75, false);

    public HBox selectButtons = new HBox(15);
    public VBox selectActions = new VBox(15);
    public VBox mealBoxes = new VBox(8.5);
    
    public TableView<Meal> menuTable = R.makeLeftReferenceTable();
    public TextField menuAddCustom = new TextField();
    public Button menuClearMealButton = R.makeButton("CLEAR", 115, false);
    public Button menuRemoveButton = R.makeButton("REMOVE", 115, false);
    public HBox menuControls = new HBox(15); //Spacing 
    public VBox menuBox = R.fillReferenceTable("Menu", menuTable, menuAddCustom, menuControls);
    
    
//makeMeal Window
private Label makeMealTitle = new Label("MAKE MEAL");
private ObservableList<String> makeMealTypeOptions = FXCollections.observableArrayList();
private ComboBox<String> makeMealType = new ComboBox();
private ComboBox<Meal> makeMealVariations = new ComboBox();
private ComboBox<Meal> makeMealVegetables = new ComboBox();
private ComboBox<Meal> makeMealFruits = new ComboBox();
private ComboBox<Meal> makeMealCarbs = new ComboBox();
private ComboBox<Meal> makeMealCatalog = new ComboBox();
private TableView<Meal> makeMealDisplay = R.makeLeftReferenceTable();
private TextField makeMealCustomAdd = new TextField();
private Button makeMealClearButton = R.makeButton("CLEAR", 115, false);
private Button makeMealRemoveButton = R.makeButton("REMOVE", 115, false);
private Button makeMealRecommendButton = R.makeButton("R-E-C-O-M-M-E-N-D", 250, false);
private Button makeMealRandomButton = R.makeButton("R-A-N-D-O-M", 250, false);
public Button addMade = R.makeButton("A-D-D   M-A-D-E", 245, false);
private HBox makeMealRemoveButtonsBox = new HBox(15);
private HBox makeMealControlsBox = new HBox(25);
private VBox makeMealSelectionsBox = new VBox(43);
private VBox makeMealDisplayBox = R.fillReferenceTable(makeMealDisplay, makeMealCustomAdd, makeMealRemoveButtonsBox);
private HBox makeMealMiddleBox = new HBox(25); 
private VBox makeMealWithTypeBox = new VBox(25);
private HBox makeMealControlsButtonsBox = new HBox(50);
private HBox makeMealAllBox = new HBox(25); 
public VBox wholeMakeMeal = new VBox(15);

private void setMakeMeal() {
    generateMealVariations();
    makeMealType.setItems(makeMealTypeOptions);
    
    indicators.add(Ibreakfast);
    indicators.add(ImondayLunch);
    indicators.add(ImondayDinner);
    indicators.add(ItuesdayLunch);
    indicators.add(ItuesdayDinner);
    indicators.add(IwednessdayLunch);
    indicators.add(IwednessdayDinner);
    indicators.add(IthursdayLunch);
    indicators.add(IthursdayDinner);
    indicators.add(IfridayLunch);
    indicators.add(IfridayDinner);
    indicators.add(IsaturdayLunch);
    indicators.add(IsaturdayDinner);
    indicators.add(IsundayLunch);
    indicators.add(IsundayDinner);

    status.add(statusbreakfast);
    status.add(statusmondayLunch);
    status.add(statusmondayDinner);
    status.add(statustuesdayLunch);
    status.add(statustuesdayDinner);
    status.add(statuswednessdayLunch);
    status.add(statuswednessdayDinner);
    status.add(statusthursdayLunch);
    status.add(statusthursdayDinner);
    status.add(statusfridayLunch);
    status.add(statusfridayDinner);
    status.add(statussaturdayLunch);
    status.add(statussaturdayDinner);
    status.add(statussundayLunch);
    status.add(statussundayDinner);

    //Menu Table:
    menuAddCustom.setMaxWidth(250);
    menuControls.setAlignment(Pos.CENTER);
    menuControls.getChildren().addAll(menuClearMealButton, menuRemoveButton);

    //Boxes
    selectButtons.setAlignment(Pos.CENTER_LEFT);
    selectButtons.getChildren().addAll(selectAll, deselectAll);
    selectActions.setAlignment(Pos.CENTER_LEFT);
    selectActions.getChildren().addAll(selectButtons);
    mealBoxes.setAlignment(Pos.CENTER);
    mealBoxes.getChildren().addAll(R.getIndicatorsBox(Ibreakfast, breakfast), R.getIndicatorsBox(ImondayLunch, mondayLunch), R.getIndicatorsBox(ImondayDinner, mondayDinner),
            R.getIndicatorsBox(ItuesdayLunch, tuesdayLunch), R.getIndicatorsBox(ItuesdayDinner, tuesdayDinner), R.getIndicatorsBox(IwednessdayLunch, wednessdayLunch), R.getIndicatorsBox(IwednessdayDinner, wednessdayDinner),
            R.getIndicatorsBox(IthursdayLunch, thursdayLunch), R.getIndicatorsBox(IthursdayDinner, thursdayDinner), R.getIndicatorsBox(IfridayLunch, fridayLunch), R.getIndicatorsBox(IfridayDinner, fridayDinner),
            R.getIndicatorsBox(IsaturdayLunch, saturdayLunch), R.getIndicatorsBox(IsaturdayDinner, saturdayDinner), R.getIndicatorsBox(IsundayLunch, sundayLunch), R.getIndicatorsBox(IsundayDinner, sundayDinner), selectActions);

        
    makeMealTitle.setFont(R.TitleFont);
    makeMealTitle.setAlignment(Pos.CENTER);
    makeMealSelectionsBox.getChildren().clear();
    makeMealSelectionsBox.getChildren().addAll(makeMealVariations, makeMealVegetables, makeMealFruits, makeMealCarbs, makeMealCatalog);
    makeMealSelectionsBox.getChildren().addAll(makeMealRecommendButton, makeMealRandomButton);
    makeMealSelectionsBox.setAlignment(Pos.CENTER);
    makeMealDisplayBox.getChildren().add(addMade);
    makeMealMiddleBox.getChildren().clear();
    makeMealMiddleBox.getChildren().addAll(makeMealSelectionsBox, makeMealDisplayBox);
    makeMealMiddleBox.setAlignment(Pos.CENTER);
    makeMealWithTypeBox.getChildren().clear();
    makeMealWithTypeBox.getChildren().addAll(makeMealType, makeMealMiddleBox);
    makeMealWithTypeBox.setAlignment(Pos.CENTER);
    makeMealRemoveButtonsBox.getChildren().clear();
    makeMealRemoveButtonsBox.getChildren().addAll(makeMealClearButton, makeMealRemoveButton);
    makeMealRemoveButtonsBox.setAlignment(Pos.BOTTOM_CENTER);
    makeMealAllBox.getChildren().clear();
    makeMealAllBox.getChildren().addAll(menuBox, makeMealWithTypeBox, mealBoxes);
    makeMealAllBox.setAlignment(Pos.CENTER);
    makeMealControlsBox.getChildren().clear();
    makeMealControlsBox.setAlignment(Pos.BOTTOM_CENTER);
    wholeMakeMeal.getChildren().clear();
//    wholeMakeMeal.setPrefWidth(580);
    wholeMakeMeal.getChildren().addAll(makeMealTitle, makeMealAllBox, makeMealControlsBox);
    wholeMakeMeal.setAlignment(Pos.CENTER);

    return;
    }
    
}
