/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

/**
 *
 * @author ethan
 */
public class MenuView {
    
    MenuView() {
        setMenu();
        runMenu();
    }
    
    //MENU
    static final ObservableList<Meal> Menu = FXCollections.observableArrayList();
    
    //MenuRecent
    static final ObservableList<Meal> MenuRecent = FXCollections.observableArrayList();


    private CatalogView catalogView = MealPlanning.catalogView;
    private MealView mealView = MealPlanning.mealView;
    private MenuView menuView = MealPlanning.menuView;
    private ShoppingListView shoppingListView = MealPlanning.shoppingListView;
    private AlertBox alert = MealPlanning.alert;
    private Save save = MealPlanning.save;
    private boolean print = MealPlanning.print;
        
    public ObservableList<Meal> Catalog = CatalogView.Catalog;
    public ObservableList<Meal> FoodOnly = CatalogView.CatalogFoodOnly;
    
    private Resources R = new Resources();

    
//*****MENU*****   
public void updateMenu() {
    //set all Meal Indicators to false
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
    
    menuIngredientsTable.setItems(null);
    menuIngredientsTable.setItems(MenuRecent);
    menuIngredientsTable.refresh();
    menuIngredientsTitle.setText("Recent");

    
    menuCatalogTable.setItems(null);
    menuCatalogTable.setItems(Catalog);
    menuCatalogTable.refresh();
    
    return;
}


public void runMenu() {
  
    menuRemoveButton.setOnAction(e -> {
        try{
        Meal selectedItem = menuTable.getSelectionModel().getSelectedItem();
        int currentIndex = menuTable.getSelectionModel().getSelectedIndex();
        if(selectedItem.getID() >= 0)    
           menuTable.getItems().remove(selectedItem);  //removing
        while(menuTable.getSelectionModel().getSelectedIndex() < Menu.size()-1) { //auto select next
                currentIndex = menuTable.getSelectionModel().getSelectedIndex();
                if((Menu.get(currentIndex+1).getName().charAt(0) == indent.charAt(0))                   //next is ingredient
                        || (menuTable.getSelectionModel().getSelectedItem().getID() < 0 )               //current is subject
                        || ((menuTable.getSelectionModel().getSelectedItem().getID() < 0) 
                             && (Menu.get(currentIndex+1).getName().charAt(0) != indent.charAt(0)))     // Current is meal subject and next is meal title
                        || ((menuTable.getSelectionModel().getSelectedItem().getID() < 0)
                             && (Menu.get(currentIndex+1).getID() < 0))                                 //Current and Next are Meal subjects
                        || ((Menu.get(currentIndex).getName().charAt(0) != indent.charAt(0)
                            && (Menu.get(currentIndex+1).getName().charAt(0) == indent.charAt(0)))))        //Current is meal title and next is ingredient
                    menuTable.getSelectionModel().selectNext();  //next
                else
                    break;  //stay
        }
        }catch(NullPointerException exe){}       
     });
        
    menuAddCustom.setOnAction( e -> {
        try {
        if(breakfast.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-1)+1,0);
        if(mondayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-2)+1,0);
        if(mondayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-3)+1,0);
        if(tuesdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-4)+1,0);
        if(tuesdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-5)+1,0);
        if(wednessdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-6)+1,0);
        if(wednessdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-7)+1,0);
        if(thursdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-8)+1,0);
        if(thursdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-9)+1,0);
        if(fridayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-10)+1,0);
        if(fridayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-11)+1,0);
        if(saturdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-12)+1,0);
        if(saturdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-13)+1,0);
        if(sundayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-14)+1,0);
        if(sundayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false,false), getMenuIndex(-15)+1,0);
       
        menuAddCustom.clear();
        }catch(NullPointerException exe){}
    });
    
    menuResetButton.setOnAction( e -> {
        resetMenu();
    });
    
    menuClearMealButton.setOnAction( e -> {
        menuInitiateRemoveMeal();    
     });
    
    
    
    menuIngredientsAddButton.setOnAction( e -> {
        try{
            if(menuIngredientsTitle.getText().equals("Ingredients")) {
                Meal selectedItem = menuIngredientsTable.getSelectionModel().getSelectedItem();
                menuInitiateAdd(selectedItem);
            }
        }catch(NullPointerException exe){}
        });
    
    menuIngredientsAddAllButton.setOnAction( e -> {
      try{
        Meal selectedItem = menuCatalogTable.getSelectionModel().getSelectedItem();
        menuInitiateAdd(selectedItem);
        }catch(NullPointerException exe){}
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
    
        
    //menuIngredientseDIT id
        menuIngredientsTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
    try{
     if(menuIngredientsTitle.getText().equals("Recent")) {  //only execute if Recent
        if (newSelection  !=  null) {
            Meal selectedItem = menuIngredientsTable.getSelectionModel().getSelectedItem();
            menuIngredientsEditID.setText(Integer.toString(selectedItem.getID()));
            menuCatalogTable.setItems(Catalog);  //full catalog view
            menuCatalogSearch.setText("");
            boolean found = false;
                for(int i=0; i<Catalog.size(); i++) {
                    if(Catalog.get(i).getID()==selectedItem.getID()) {
                        menuCatalogTable.getSelectionModel().select(Catalog.get(i));   //*********throws IndexOutOfBoundsException  - doesn't catch**********
                        found = true;
                        break;
                    }
                }
           
            if(!found)
                alert.error("Meal Not Found in Catalog - "+selectedItem.getID()+" - "+selectedItem.getName()+" - MenuView.menuIngredientsTable.addListener()");
//            if(menuCatalogTable.getSelectionModel().getSelectedItem().Ingredients.isEmpty()) {  //directly referencing menuCatalogTable Listings
//                menuIngredientsTitle.setText("Recent");
//                menuIngredientsTable.setItems(MenuRecent);
//            } else {
//                menuIngredientsTitle.setText("Ingredients");
//                menuIngredientsTable.setItems(menuCatalogTable.getSelectionModel().getSelectedItem().Ingredients);
//            }
        }
     }
    }catch(IndexOutOfBoundsException e){
        alert.error("IndexOutOfBoundsException - MenuView.menuIngredientsTable.addListener()");
    }
    });

        menuIngredientsEditID.setOnAction( e -> {
        try{
            if(print)
                  System.out.println("mennuIngredientsEditID - entry is: "+Integer.toString(getMenuEditIDEntry()));
        if(getMenuEditIDEntry() > 0){
             boolean found=false;
             for(int i=0; i<Catalog.size(); i++){
                 if(Catalog.get(i).getID()==getMenuEditIDEntry()){
                     found=true;
                     menuInitiateAdd(Catalog.get(i));
                     break;
                 }
             }
            if(!found) {
                alert.error("Meal not found in Catalog - "+Integer.toString(getMenuEditIDEntry())+" - MenuView.menuIngredientsEditID.setOnAction()--(adding ingredient)");
                menuIngredientsEditID.setText("");
            }
        }  
        }catch(NullPointerException exe){}
    });
    
    
    //menuCatalogEditButton in start, because must switch scenes
    
    //menuCatalogNewButton in start, because must switch scenes

    
    //Catalog Edit ID
        menuCatalogTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
    try{
        if (newSelection  !=  null) {
        Meal selectedItem = menuCatalogTable.getSelectionModel().getSelectedItem();
        catalogView.catalogTable.getSelectionModel().select(selectedItem);
        catalogView.catalogEditID.setText(Integer.toString(selectedItem.getID()));
        catalogView.catalogChangeID.setText(Integer.toString(selectedItem.getID()));
       // catalogView.catalogIngredientsTable.setItems(selectedItem.Ingredients);
        menuCatalogSearch.setText(Integer.toString(selectedItem.getID()));
        if(selectedItem.Ingredients.isEmpty()) {
            menuIngredientsTitle.setText("Recent");
            menuIngredientsTable.setItems(MenuRecent);
        } else {
            menuIngredientsTitle.setText("Ingredients");
            menuIngredientsTable.setItems(selectedItem.Ingredients);
        }
        }
    }catch(NullPointerException e){
        alert.error("NullPointerException - MenuView.menuCatalogTable.addListener()");
    }catch(IndexOutOfBoundsException ex){
         alert.error("IndexOutOfBoundsException - MenuView.menuCatalogTable.addListener()");
    }
    });
        
        
    menuCatalogSearch.setOnAction( e -> {
        try {
            if(menuCatalogSearch.getText().equals("")) {
                menuCatalogTable.setItems(null);
                menuCatalogTable.setItems(Catalog);
            } else {
                MenuSearch.clear();
                for(int i=0; i<Catalog.size(); i++) {
                    boolean match = false;
                    for(int j=0;j<menuCatalogSearch.getText().length();j++) {
                        if(Catalog.get(i).getName().charAt(j) == menuCatalogSearch.getText().charAt(j))
                            match = true;
                        else {
                            match = false;
                            break;
                        }
                    }
                    if(match)
                        MenuSearch.add(new Meal(false,Catalog.get(i)));
                }
                menuCatalogTable.setItems(null);
                menuCatalogTable.setItems(MenuSearch);
            }
        }catch(NullPointerException exe){}
    });
    
    menuRecentButton.setOnAction(e -> {
        menuIngredientsTable.setItems(MenuRecent);
        menuIngredientsTitle.setText("Recent");
    });
 

       return;
}  


//Convert editID text to Integer
public  int getMenuEditIDEntry(){
 int entry = 0;
    try{
        if(Integer.parseInt(menuIngredientsEditID.getText()) > 0)
            entry = Integer.parseInt(menuIngredientsEditID.getText());
        else {
            entry = 0;
            alert.error("Input is not a valid integer - getMenuEditIDEntry()");
            }
      } catch(NumberFormatException ex) {
          entry = 0;
       alert.error("Input is not a valid integer - getMenuEditIDEntry()");
     }
return entry;
}


public  int getMenuIndex(int ID) {
    for(int i = 0; i<Menu.size(); i++) {
        if(Menu.get(i).getID() == ID)
            return i;
    }
    return 0;  //default first place    
}

public  final String indent = "     -";

public  void menuAdd(Meal selected, int startID) {
try {
    Meal selectedMeal = menuCatalogTable.getSelectionModel().getSelectedItem();
    int start = getMenuIndex(startID);
    int current = start+1;
    int end = getMenuIndex(startID-1);
    if(end == 0) //didn't find, last one.
        end = Menu.size()-1;
    
    if((selectedMeal.getID() == selected.getID()) && (selected.Ingredients.isEmpty())) {  //add whole Meal from Catalog, indent at top of day's meal
        menuInsert(selected, current, 2);
    } else {  //adding ingredient or whole meal including ingredients
        while(current != end) {
        if(Menu.get(current).getID()==selectedMeal.getID()) {
            current++;
            break;
        }  
        current++;
        }
        if(current == end) {  //didn't find, new meal 
           //Add Meal Name title and then following ingredient
                Menu.add(current, new Meal(false, selectedMeal));
                current++;
                menuInsert(selected, current, 2);
        } else //meal match
            menuInsert(selected, current,2 );
    }
    //add selectedMeal to stats
    
 }catch(NullPointerException exe){}
    return;
} 

public  void menuInsert(Meal theMeal, int place, int remainingDepth){
        if(theMeal.Ingredients.isEmpty() || remainingDepth <= 0) { //adding Meal, no ingredients
        Menu.add(place, new Meal(indent, theMeal));
    } else { //Add All Ingredients of selectedIngredient
        Menu.add(place, new Meal(indent, theMeal.Ingredients.get(theMeal.Ingredients.size()-1)));
        for(int i = 0; i<theMeal.Ingredients.size()-1;i++) {
        menuInsert(theMeal.Ingredients.get(i), place+1, --remainingDepth);  //recursion to add sussesive ingredients of ingredients.
        }
    }
    return;
}

public  void menuInitiateAdd(Meal selectedItem) {
        if(selectedItem != null) {
            //Adding to Recent Meals
            try{
                MenuRecent.add(0,new Meal(false,selectedItem));  //copy
                for(int i=1;i<MenuRecent.size();i++) {
                    if(MenuRecent.get(i).getID()==selectedItem.getID())
                        MenuRecent.remove(i);
                }
                if(MenuRecent.size()>20)
                    MenuRecent.remove(MenuRecent.size()-1);
            }catch(IndexOutOfBoundsException exe) {
            alert.error("adding/removing from RecentList - IndexOutOfBoundsException - MenuView.menuInitiateAdd()");
            }
            if(breakfast.isSelected()) {
                Ibreakfast.setText(R.updateIndicators(statusbreakfast, selectedItem));
                menuAdd(selectedItem,-1); }
            if(mondayLunch.isSelected()) {
                ImondayLunch.setText(R.updateIndicators(statusmondayLunch, selectedItem));
                menuAdd(selectedItem,-2); }
            if(mondayDinner.isSelected())  {
                ImondayDinner.setText(R.updateIndicators(statusmondayDinner, selectedItem));
                menuAdd(selectedItem,-3); }
            if(tuesdayLunch.isSelected()) {
                ItuesdayLunch.setText(R.updateIndicators(statustuesdayLunch, selectedItem));
                menuAdd(selectedItem,-4); }
            if(tuesdayDinner.isSelected()) {
                ItuesdayDinner.setText(R.updateIndicators(statustuesdayDinner, selectedItem));
                menuAdd(selectedItem,-5); }
            if(wednessdayLunch.isSelected())  {
                IwednessdayLunch.setText(R.updateIndicators(statuswednessdayLunch, selectedItem));
                menuAdd(selectedItem,-6); }
            if(wednessdayDinner.isSelected()) {
                IwednessdayDinner.setText(R.updateIndicators(statuswednessdayDinner, selectedItem));
                menuAdd(selectedItem,-7); }
            if(thursdayLunch.isSelected()) {
                IthursdayLunch.setText(R.updateIndicators(statusthursdayLunch, selectedItem));
                menuAdd(selectedItem,-8); }
            if(thursdayDinner.isSelected()) {
                IthursdayDinner.setText(R.updateIndicators(statusthursdayDinner, selectedItem));
                menuAdd(selectedItem,-9); }
            if(fridayLunch.isSelected()) {
                IfridayLunch.setText(R.updateIndicators(statusfridayLunch, selectedItem));
                menuAdd(selectedItem,-10); }
            if(fridayDinner.isSelected()) {
                IfridayDinner.setText(R.updateIndicators(statusfridayDinner, selectedItem));
                menuAdd(selectedItem,-11); }
            if(saturdayLunch.isSelected()) {
                IsaturdayLunch.setText(R.updateIndicators(statussaturdayLunch, selectedItem));
                menuAdd(selectedItem,-12); }
            if(saturdayDinner.isSelected()) {
                IsaturdayDinner.setText(R.updateIndicators(statussaturdayDinner, selectedItem));
                menuAdd(selectedItem,-13); }
            if(sundayLunch.isSelected()) {
                IsundayLunch.setText(R.updateIndicators(statussundayLunch, selectedItem));
                menuAdd(selectedItem,-14); }
            if(sundayDinner.isSelected()) {
                IsundayDinner.setText(R.updateIndicators(statussundayDinner, selectedItem));
                menuAdd(selectedItem,-15); }
        }
    
    return;
}

public  void menuRemoveMeal(int mealID) {
        try{
            int start = getMenuIndex(mealID);
            int current = start+1;
            while((Menu.get(current).getID() >= 0) && (current < Menu.size()-1)) {
                Menu.remove(current);
            }
         }catch(NullPointerException exe){} 
            return;
}

public  void menuInitiateRemoveMeal() {
            if(breakfast.isSelected()) {
                Ibreakfast.setText(R.resetIndicators(statusbreakfast));
                menuRemoveMeal(-1); }
            if(mondayLunch.isSelected()) {
                ImondayLunch.setText(R.resetIndicators(statusmondayLunch));
                menuRemoveMeal(-2); }
            if(mondayDinner.isSelected())  {
                ImondayDinner.setText(R.resetIndicators(statusmondayDinner));
                menuRemoveMeal(-3); }
            if(tuesdayLunch.isSelected()) {
                ItuesdayLunch.setText(R.resetIndicators(statustuesdayLunch));
                menuRemoveMeal(-4); }
            if(tuesdayDinner.isSelected()) {
                ItuesdayDinner.setText(R.resetIndicators(statustuesdayDinner));
                menuRemoveMeal(-5); }
            if(wednessdayLunch.isSelected())  {
                IwednessdayLunch.setText(R.resetIndicators(statuswednessdayLunch));
                menuRemoveMeal(-6); }
            if(wednessdayDinner.isSelected()) {
                IwednessdayDinner.setText(R.resetIndicators(statuswednessdayDinner));
                menuRemoveMeal(-7); }
            if(thursdayLunch.isSelected()) {
                IthursdayLunch.setText(R.resetIndicators(statusthursdayLunch));
                menuRemoveMeal(-8); }
            if(thursdayDinner.isSelected()) {
                IthursdayDinner.setText(R.resetIndicators(statusthursdayDinner));
                menuRemoveMeal(-9); }
            if(fridayLunch.isSelected()) {
                IfridayLunch.setText(R.resetIndicators(statusfridayLunch));
                menuRemoveMeal(-10); }
            if(fridayDinner.isSelected()) {
                IfridayDinner.setText(R.resetIndicators(statusfridayDinner));
                menuRemoveMeal(-11); }
            if(saturdayLunch.isSelected()) {
                IsaturdayLunch.setText(R.resetIndicators(statussaturdayLunch));
                menuRemoveMeal(-12); }
            if(saturdayDinner.isSelected()) {
                IsaturdayDinner.setText(R.resetIndicators(statussaturdayDinner));
                menuRemoveMeal(-13); }
            if(sundayLunch.isSelected()) {
                IsundayLunch.setText(R.resetIndicators(statussundayLunch));
                menuRemoveMeal(-14); }
            if(sundayDinner.isSelected()) {
                IsundayDinner.setText(R.resetIndicators(statussundayDinner));
                menuRemoveMeal(-15); }
    return;
}

public void updateAllIndicators() {
    try {
        int j=0;
        for(int i=0; i<Menu.size(); i++) {
            if(Menu.get(i).getID()<0)
                j++;
            else if(Menu.get(i).getID()>0) 
                indicators.get(j-1).setText(R.updateIndicators(status.get(j-1), Menu.get(i)));   
        }
    }catch(IndexOutOfBoundsException exe) {
    alert.error("Menu has Invalid negative Meal ID's - IndexOutOfBoundsException - MenuView.updateAllIndicators()");
    }
    
    
}

public void resetMenu() {
    Menu.clear();
    
    Menu.add(new Meal(false,-1,"*****Breakfast*****",false,false,false,false,false));
    Menu.add(new Meal(false,-2,"*****Monday Lunch*****",false,false,false,false,false));
    Menu.add(new Meal(false,-3,"*****Monday Dinner*****",false,false,false,false,false));
    Menu.add(new Meal(false,-4,"*****Tuesday Lunch*****",false,false,false,false,false));
    Menu.add(new Meal(false,-5,"*****Tuesday Dinner*****",false,false,false,false,false));
    Menu.add(new Meal(false,-6,"*****Wednessday Lunch*****",false,false,false,false,false));
    Menu.add(new Meal(false,-7,"*****Wednessday Dinner*****",false,false,false,false,false));
    Menu.add(new Meal(false,-8,"*****Thursday Lunch*****",false,false,false,false,false));
    Menu.add(new Meal(false,-9,"*****Thursday Dinner*****",false,false,false,false,false));
    Menu.add(new Meal(false,-10,"*****Friday Lunch*****",false,false,false,false,false));
    Menu.add(new Meal(false,-11,"*****Friday Dinner*****",false,false,false,false,false));
    Menu.add(new Meal(false,-12,"*****Saturday Lunch*****",false,false,false,false,false));
    Menu.add(new Meal(false,-13,"*****Saturday Dinner*****",false,false,false,false,false));
    Menu.add(new Meal(false,-14,"*****Sunday Lunch*****",false,false,false,false,false));
    Menu.add(new Meal(false,-15,"*****Sunday Dinner*****",false,false,false,false,false));
    Menu.add(new Meal(false,-16,"***********************",false,false,false,false,false));
    
    Ibreakfast.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
ImondayLunch.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
ImondayDinner.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
ItuesdayLunch.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
ItuesdayDinner.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IwednessdayLunch.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IwednessdayDinner.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IthursdayLunch.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IthursdayDinner.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IfridayLunch.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IfridayDinner.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IsaturdayLunch.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IsaturdayDinner.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IsundayLunch.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));
IsundayDinner.setText(R.updateIndicators(R.theBlankMeal, R.theBlankMeal));

    return;    
}

//CheckBoxes
private CheckBox breakfast = new CheckBox("Breakfast");
private CheckBox mondayLunch = new CheckBox("Monday Lunch");
private CheckBox mondayDinner = new CheckBox("Monday Dinner");
private CheckBox tuesdayLunch = new CheckBox("Tuesday Lunch");
private CheckBox tuesdayDinner = new CheckBox("Tuesday Dinner");
private CheckBox wednessdayLunch = new CheckBox("Wednessday Lunch");
private CheckBox wednessdayDinner = new CheckBox("Wednessday Dinner");
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
public VBox mealBoxes = new VBox(8.5);

//Menu Table Set
public Label menuTitle = new Label("MENU");
public TableView<Meal> menuTable = R.makeLeftReferenceTable();
public TextField menuAddCustom = new TextField();
public Button menuResetButton = R.makeButton("RESET", 80, false);
public Button menuClearMealButton = R.makeButton("CLEAR", 80, false);
public Button menuRemoveButton = R.makeButton("REMOVE", 80, false);
public HBox menuControls = new HBox(15); //Spacing 
public VBox menuBox = R.fillReferenceTable("Menu", menuTable, menuAddCustom, menuControls);

//RecentList:
public Button menuRecentButton = R.makeButton("RECENT", 75, false);
//Ingredients Table Set
public TableView<Meal> menuIngredientsTable = R.makeSimpleReferenceTable();
public Button menuIngredientsAddButton = R.makeButton("ADD", 65, false);
public TextField menuIngredientsEditID = new TextField();
public Button menuIngredientsAddAllButton = R.makeButton("ADD ALL", 85, false);
public HBox menuIngredientsControls = new HBox(25); //Spacing 
public Label menuIngredientsTitle = new Label("Ingredients");
public HBox menuIngredientsTitleBox = new HBox(25);
public VBox menuIngredientsBox = new VBox(10); 

//Catalog View Set:
private ObservableList<Meal> MenuSearch = FXCollections.observableArrayList();
public TableView<Meal> menuCatalogTable = R.makeDetailedReferenceTable();
public TextField menuCatalogSearch = new TextField();
public Button menuCatalogEditButton = R.makeButton("EDIT", 75, false);
public Button menuCatalogNewButton = R.makeButton("NEW", 75, false);
public HBox menuCatalogControls = new HBox(15); //Spacing 
public VBox menuCatalogViewBox = R.fillReferenceTable("Catalog", menuCatalogTable, menuCatalogControls);

public HBox combineMenuBox = new HBox(30); //Spacing 
public VBox wholeMenu = new VBox(15); //spacing

             
public void setMenu()
{
    //adding to list for updateAllIndicators()
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
    menuTitle.setFont(R.TitleFont);
    menuTitle.setAlignment(Pos.CENTER);
    menuAddCustom.setPrefWidth(250);
    menuControls.setAlignment(Pos.CENTER);
    menuControls.getChildren().addAll(menuResetButton, menuClearMealButton, menuRemoveButton);
            
    //Boxes
    selectButtons.setAlignment(Pos.CENTER_LEFT);
    selectButtons.getChildren().addAll(selectAll, deselectAll);
    mealBoxes.setAlignment(Pos.CENTER_LEFT);
    mealBoxes.getChildren().addAll(R.getIndicatorsBox(Ibreakfast, breakfast),R.getIndicatorsBox(ImondayLunch, mondayLunch),R.getIndicatorsBox(ImondayDinner, mondayDinner),
            R.getIndicatorsBox(ItuesdayLunch, tuesdayLunch), R.getIndicatorsBox(ItuesdayDinner, tuesdayDinner), R.getIndicatorsBox(IwednessdayLunch, wednessdayLunch), R.getIndicatorsBox(IwednessdayDinner, wednessdayDinner),
            R.getIndicatorsBox(IthursdayLunch, thursdayLunch), R.getIndicatorsBox(IthursdayDinner, thursdayDinner), R.getIndicatorsBox(IfridayLunch, fridayLunch), R.getIndicatorsBox(IfridayDinner, fridayDinner),
            R.getIndicatorsBox(IsaturdayLunch, saturdayLunch), R.getIndicatorsBox(IsaturdayDinner, saturdayDinner), R.getIndicatorsBox(IsundayLunch, sundayLunch), R.getIndicatorsBox(IsundayDinner, sundayDinner),selectButtons);

    //Ingredients Table
    menuIngredientsEditID.setPrefWidth(50);  
    menuIngredientsEditID.setStyle( "-fx-alignment: CENTER;"); //center allign
    menuIngredientsControls.setAlignment(Pos.CENTER);
    menuIngredientsControls.getChildren().addAll(menuIngredientsAddButton, menuIngredientsEditID, menuIngredientsAddAllButton);
    menuIngredientsTitle.setFont(R.TitleFont);
    menuIngredientsTitle.setAlignment(Pos.CENTER_LEFT);
    menuIngredientsTitle.setMaxWidth(160);
    menuIngredientsTitleBox.setAlignment(Pos.CENTER_RIGHT);
    menuIngredientsTitleBox.getChildren().addAll(menuIngredientsTitle, menuRecentButton);
    menuIngredientsBox.setAlignment(Pos.TOP_CENTER);
    menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox,menuIngredientsTable,menuIngredientsControls);

    
    //Catalog View Table
    menuCatalogSearch.setPrefWidth(150);  
    menuCatalogSearch.setStyle( "-fx-alignment: CENTER;"); //center allign
    //menuStats.setAlignment(Pos.CENTER);
   // menuStats.getChildren().addAll();
    menuCatalogControls.setAlignment(Pos.CENTER);
    menuCatalogControls.getChildren().addAll(menuCatalogEditButton, menuCatalogSearch, menuCatalogNewButton);
      
    //Catalog Assembly All Boxes
    combineMenuBox.setAlignment(Pos.CENTER);
    combineMenuBox.getChildren().addAll(menuBox, mealBoxes, menuIngredientsBox, menuCatalogViewBox); 
   
    wholeMenu.setAlignment(Pos.TOP_CENTER);
    wholeMenu.getChildren().addAll(menuTitle,combineMenuBox);
    

}



}
