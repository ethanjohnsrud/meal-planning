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
import static mealplanning.MealPlanning.makeMealView;
import static mealplanning.MealPlanning.shoppingListView;
import static mealplanning.MealPlanning.alert;
import static mealplanning.MealPlanning.print;
import static mealplanning.MealPlanning.save;
import static mealplanning.MealPlanning.currentEntry;


/**
 *
 * @author ethan
 */
public class MenuView {

    MenuView() {
        setMenu();
        runMenu();
//        MenusList.add("Menu");
    }

    //MENU
    public String currentMenu = null;
    static final ObservableList<String> MenusList = FXCollections.observableArrayList();
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
    private int currentEntry = MealPlanning.currentEntry;

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
        menuIngredientsTable.setItems(catalogView.getCurrentMeal().Ingredients);
        menuIngredientsTable.refresh();
        menuRecentTable.setItems(null);
        menuRecentTable.setItems(MenuRecent);
        menuRecentTable.refresh();
        menuIngredientsTitle.setText("Recent");
        menuIngredientsBox.getChildren().clear();
        menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuRecentTable, menuIngredientsControls);

        menuCatalogTable.setItems(null);
        menuCatalogTable.setItems(Catalog);
        menuCatalogTable.refresh();

        return;
    }

    public void runMenu() {

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
                    menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-1) + 1, 0);
                }
                if (mondayLunch.isSelected()) {
                    menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-2) + 1, 0);
                }
                if (mondayDinner.isSelected()) {
                    menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-3) + 1, 0);
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
                    menuInsert(new Meal( 00, menuAddCustom.getText()), getMenuIndex(-15) + 1, 0);
                }

                menuAddCustom.clear();
            } catch (NullPointerException exe) {
            }
        });

        menuResetButton.setOnAction(e -> {
            if(currentMenu != null){
                if (new AlertBox().actionConfirmation("Are you sure you want to Clear and Reset the Menu?")) {
                    resetMenu();
                }
            }
        });

        menuClearMealButton.setOnAction(e -> {
            menuInitiateRemoveMeal();
        });

        menuIngredientsAddButton.setOnAction(e -> {
            if(catalogView.IDset()){
            try {
                if (!catalogView.getCurrentMeal().Ingredients.isEmpty()) {
             
                    boolean found = false;
                    for (int i = 0; i < Catalog.size(); i++) {
                        if (Catalog.get(i).getID() == getMenuEditIDEntry()) {
                            found = true;
                            menuInitiateAdd(Catalog.get(i));
                            break;
                        }
                    }
                    if (!found) {
                        alert.error("Meal not found in Catalog - " + Integer.toString(getMenuEditIDEntry()) + " - MenuView. menuIngredientsAddButton.setOnAction()--(adding ingredient)");
//                   menuIngredientsEditID.setText("");
                    }
                }else 
                    new AlertBox().notify("Please use the 'ADD ALL' button to add whole meals to the Menu.");
            } catch (NullPointerException exe) {
            }
            }
        });

        menuIngredientsAddAllButton.setOnAction(e -> {
            if(catalogView.IDset()){
            try {
                Meal currentMeal = catalogView.getCurrentMeal();
                menuInitiateAdd(currentMeal);
            } catch (NullPointerException exe) {
                alert.error("NullPointerException - " + Integer.toString(getMenuEditIDEntry()) + " - MenuView.menuIngredientsAddAllButton.setOnAction()--(adding meal)");
            }
            }
        });
        
        menuIngredientsEditID.setOnAction(e -> {  //add
            try {
                if (print) {
                    System.out.println("mennuIngredientsEditID - entry is: " + Integer.toString(getMenuEditIDEntry()));
                }
                if (getMenuEditIDEntry() > 0) {
                    boolean found = false;
                    for (int i = 0; i < Catalog.size(); i++) {
                        if (Catalog.get(i).getID() == getMenuEditIDEntry()) {
                            found = true;
                            menuInitiateAdd(Catalog.get(i));
                            break;
                        }
                    }
                    if (!found) {
                        alert.error("Meal not found in Catalog - " + Integer.toString(getMenuEditIDEntry()) + " - MenuView.menuIngredientsEditID.setOnAction()--(adding meal)");
//                menuIngredientsEditID.setText("");
                    }
                }
            } catch (NullPointerException exe) {
            }
        });

        //menuCatalogEditButton in start, because must switch scenes
        //menuCatalogNewButton in start, because must switch scenes
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

        //menuRecent Table
        menuRecentTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
            if (newSelection != null) {
                try {
//            String currentID = Integer.toString(menuRecentTable.getSelectionModel().getSelectedItem().getID());

                    catalogView.catalogTable.getSelectionModel().select(menuRecentTable.getSelectionModel().getSelectedItem());
                    menuCatalogTable.getSelectionModel().select(menuRecentTable.getSelectionModel().getSelectedItem());
                    menuRecentTable.refresh();
//            catalogView.catalogEditID.setText(currentID);
//            catalogView.catalogChangeID.setText(currentID);
//            menuCatalogSearch.setText(currentID);
//            menuIngredientsEditID.setText(currentID);

                    // catalogView.catalogIngredientsTable.setItems(selectedItem.Ingredients);
//            if(selectedItem.Ingredients.isEmpty()) {
//                menuIngredientsTitle.setText("Recent");
//                menuIngredientsTable.setItems(MenuRecent);
//            } else {
//                menuIngredientsTitle.setText("Ingredients");
//                menuIngredientsTable.setItems(selectedItem.Ingredients);
//            }
                } catch (NullPointerException e) {
                    alert.error("NullPointerException - MenuView.menuRecentTable.addListener()");
                } catch (IndexOutOfBoundsException ex) {
                    alert.error("IndexOutOfBoundsException - MenuView.menuRecentTable.addListener()");
                }
            }
        });

        //menuIngredientsTable id
        menuIngredientsTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
            if (newSelection != null) {
                try {
//                 String currentID = Integer.toString(menuIngredientsTable.getSelectionModel().getSelectedItem().getID());

                    menuIngredientsEditID.setText(Integer.toString(menuIngredientsTable.getSelectionModel().getSelectedItem().getID()));

                } catch (NullPointerException e) {
//                    alert.error("NullPointerException - MenuView.menuIngredientsTable.addListener()");
                } catch (IndexOutOfBoundsException ex) {
                    alert.error("IndexOutOfBoundsException - MenuView.menuIngredientsTable.addListener()");
                }
            }
        });

        menuCatalogTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
            if (newSelection != null) {
                try {
//        String currentID = Integer.toString(menuCatalogTable.getSelectionModel().getSelectedItem().getID());
                    catalogView.catalogTable.getSelectionModel().select(menuCatalogTable.getSelectionModel().getSelectedItem());
//        catalogView.catalogEditID.setText(currentID);
//        catalogView.catalogChangeID.setText(currentID);
                    menuCatalogSearch.setText(Integer.toString(catalogView.getCurrentMeal().getID()));
                    menuIngredientsEditID.setText(Integer.toString(catalogView.getCurrentMeal().getID()));
                    // catalogView.catalogIngredientsTable.setItems(selectedItem.Ingredients);
                    if (catalogView.getCurrentMeal().Ingredients.isEmpty()) {
                        menuIngredientsTitle.setText("Recent");
                        menuIngredientsBox.getChildren().clear();
                        menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuRecentTable, menuIngredientsControls);
                    } else {
                        menuIngredientsTitle.setText("Ingredients");
                        menuIngredientsBox.getChildren().clear();
                        menuIngredientsTable.getSelectionModel().clearSelection();
                        menuIngredientsTable.setItems(catalogView.getCurrentMeal().Ingredients);
                        menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuIngredientsTable, menuIngredientsControls);
                    }
                } catch (NullPointerException e) {
                    alert.error("NullPointerException - MenuView.menuCatalogTable.addListener()");
                } catch (IndexOutOfBoundsException ex) {
                    alert.error("IndexOutOfBoundsException - MenuView.menuCatalogTable.addListener()");
                }
            }
        });

        menuCatalogSearch.setOnAction(e -> {
            try {
                if (menuCatalogSearch.getText().equals("")) {
                    menuCatalogTable.setItems(null);
                    menuCatalogTable.setItems(Catalog);
                } else {
                    MenuSearch.clear();
                    for (int i = 0; i < Catalog.size(); i++) {
                        boolean match = false;
                        for (int j = 0; j < menuCatalogSearch.getText().length(); j++) {
                            if (Catalog.get(i).getName().charAt(j) == menuCatalogSearch.getText().charAt(j)) {
                                match = true;
                            } else {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            MenuSearch.add(new Meal(false, Catalog.get(i)));
                        }
                    }
                    menuCatalogTable.setItems(null);
                    menuCatalogTable.setItems(MenuSearch);
                }
            } catch (NullPointerException exe) {
            }
        });

        menuRecentButton.setOnAction(e -> {
            menuIngredientsTitle.setText("Recent");
            menuIngredientsBox.getChildren().clear();
            menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuRecentTable, menuIngredientsControls);

        });

        menuSelect.setOnAction(e -> {
            if(catalogView.IDset()){
            if(currentMenu != null) {
                if (new AlertBox().actionRequest(false, "Do you Wish to Save: " + currentMenu+"?", "Save: "+currentMenu)) {
                    new Save().menuExport(true, currentMenu);
                }
            }
            currentMenu = menuSelect.getValue();
            if(!save.menuImport(true, currentMenu) || currentMenu == null) {  //import
                resetMenu();
                alert.error("Creating a New Menu - "+currentMenu);
            }
            updateMenu();
            }
        });

        menuTitlesButton.setOnAction(e -> {
            editMenusList(MenusList);
        });

        return;
    }

//Convert editID text to Integer
    public int getMenuEditIDEntry() {
        int entry = 0;
        try {
            if (Integer.parseInt(menuIngredientsEditID.getText()) > 0) {
                entry = Integer.parseInt(menuIngredientsEditID.getText());
            } else {
                entry = 0;
                alert.error("Input is not a valid integer - getMenuEditIDEntry()");
            }
        } catch (NumberFormatException ex) {
            entry = 0;
            alert.error("Input is not a valid integer - getMenuEditIDEntry()");
        }
        return entry;
    }

    
//***********Menu Add Operations****************************************    
    public int getMenuIndex(int ID) {
        for (int i = 0; i < Menu.size(); i++) {
            if (Menu.get(i).getID() == ID) {
                return i;
            }
        }
        return 0;  //default first place    
    }

    public final String indent = "     -";

    public void menuAdd(Meal selected, int startID) {
        try {
            Meal currentMeal = catalogView.getCurrentMeal();
            int start = getMenuIndex(startID);
            int current = start + 1;
            int end = getMenuIndex(startID - 1);
            if (end == 0) //didn't find, last one.
            {
                end = Menu.size() - 1;
            }

            if ((currentMeal.getID() == selected.getID()) && (selected.Ingredients.isEmpty())) {  //add whole Meal from Catalog, indent at top of day's meal
                menuInsert(selected, current, 2);
                this.addRecent(currentMeal);
            } else {  //adding ingredient or whole meal including ingredients
                while (current != end) {
                    if (Menu.get(current).getID() == currentMeal.getID()) {
                        current++;
                        break;
                    }
                    current++;
                }
                if (current == end) {  //didn't find, new meal 
                    //Add Meal Name title and then following ingredient
                    Menu.add(current, new Meal(false, currentMeal));
                    this.addRecent(currentMeal);
                    current++;
                    menuInsert(selected, current, 2);
                } else //meal match
                {
                    menuInsert(selected, current, 2);
                }
            }

        } catch (NullPointerException exe) {
        }
        return;
    }

    public void menuInsert(Meal theMeal, int place, int remainingDepth) {  //Exact copy in MakeMealView
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

    public void menuInitiateAdd(Meal selectedItem) {
        if ((selectedItem != null) && (selectedItem.getID() >= 0)) {
int none = 0;
            if (breakfast.isSelected()) {
                Ibreakfast.setText(R.updateIndicators(statusbreakfast, selectedItem));
                menuAdd(selectedItem, -1);
            }else none++;
            if (mondayLunch.isSelected()) {
                ImondayLunch.setText(R.updateIndicators(statusmondayLunch, selectedItem));
                menuAdd(selectedItem, -2);
            }else none++;
            if (mondayDinner.isSelected()) {
                ImondayDinner.setText(R.updateIndicators(statusmondayDinner, selectedItem));
                menuAdd(selectedItem, -3);
            }else none++;
            if (tuesdayLunch.isSelected()) {
                ItuesdayLunch.setText(R.updateIndicators(statustuesdayLunch, selectedItem));
                menuAdd(selectedItem, -4);
            }else none++;
            if (tuesdayDinner.isSelected()) {
                ItuesdayDinner.setText(R.updateIndicators(statustuesdayDinner, selectedItem));
                menuAdd(selectedItem, -5);
            }else none++;
            if (wednessdayLunch.isSelected()) {
                IwednessdayLunch.setText(R.updateIndicators(statuswednessdayLunch, selectedItem));
                menuAdd(selectedItem, -6);
            }else none++;
            if (wednessdayDinner.isSelected()) {
                IwednessdayDinner.setText(R.updateIndicators(statuswednessdayDinner, selectedItem));
                menuAdd(selectedItem, -7);
            }else none++;
            if (thursdayLunch.isSelected()) {
                IthursdayLunch.setText(R.updateIndicators(statusthursdayLunch, selectedItem));
                menuAdd(selectedItem, -8);
            }else none++;
            if (thursdayDinner.isSelected()) {
                IthursdayDinner.setText(R.updateIndicators(statusthursdayDinner, selectedItem));
                menuAdd(selectedItem, -9);
            }else none++;
            if (fridayLunch.isSelected()) {
                IfridayLunch.setText(R.updateIndicators(statusfridayLunch, selectedItem));
                menuAdd(selectedItem, -10);
            }else none++;
            if (fridayDinner.isSelected()) {
                IfridayDinner.setText(R.updateIndicators(statusfridayDinner, selectedItem));
                menuAdd(selectedItem, -11);
            }else none++;
            if (saturdayLunch.isSelected()) {
                IsaturdayLunch.setText(R.updateIndicators(statussaturdayLunch, selectedItem));
                menuAdd(selectedItem, -12);
            }else none++;
            if (saturdayDinner.isSelected()) {
                IsaturdayDinner.setText(R.updateIndicators(statussaturdayDinner, selectedItem));
                menuAdd(selectedItem, -13);
            }else none++;
            if (sundayLunch.isSelected()) {
                IsundayLunch.setText(R.updateIndicators(statussundayLunch, selectedItem));
                menuAdd(selectedItem, -14);
            }else none++;
            if (sundayDinner.isSelected()) {
                IsundayDinner.setText(R.updateIndicators(statussundayDinner, selectedItem));
                menuAdd(selectedItem, -15);
            }else none++;

            if(none == 15)
                new AlertBox().notify("Please select a meal to add to Menu.");
                        
        }

        return;
    }

    
    public void addRecent(Meal theMeal){  //Also exact copy of method indepent in MakeMealView
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
    //***********Menu Remove Operations****************************************    

    public void menuRemoveMeal(int mealID) {  //exact Copies in MakeMealView
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

    public void menuInitiateRemoveMeal() {  //exact Copies in MakeMealView
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

    



   
 Stage window5 = new Stage();
Label title5 = new Label();
Label currentHeader5 = new Label("CURRENT TITLE");
Label newHeader5 = new Label("NEW TITLE");
HBox headerBox5 = new HBox(10);
Button saveButton5 = new Button("SAVE");
HBox controlButtons5 = new HBox(20);
Button newButton5 = new Button("NEW MENU");
Button copy5 = new Button("COPY");
private boolean exists = false;

VBox menusList;
VBox layout5 = new VBox(15);
Scene scene5 = new Scene(layout5);
ListView<HBox> listView5 = new ListView<HBox>();
ObservableList<HBox> MenusList5 = FXCollections.observableArrayList();
        
    public void editMenusList(ObservableList<String> MenusList) {
        window5.setTitle("---M-E-N-U--S---L-I-S-T---");
        window5.setMinWidth(450);
             
        title5.setText("---M-E-N-U--S---L-I-S-T---\n\n");
        title5.setAlignment(Pos.CENTER);
        currentHeader5.setMinWidth(150);
        newHeader5.setMinWidth(150);
        headerBox5.setAlignment(Pos.CENTER_LEFT);
        headerBox5.getChildren().clear();
        headerBox5.getChildren().addAll(currentHeader5, newHeader5);
        headerBox5.setMinWidth(300);  
        
        MenusList.forEach(t -> {
            MenusList5.add(getMenuListEditBox(MenusList, t));
        });
        
        listView5.setPrefWidth(425);
        listView5.setPrefHeight(250);
        listView5.setItems(MenusList5);
        
        saveButton5.setOnAction(e -> {new Save().menusListExport(false); window5.close();});
        
        copy5.setOnAction(e -> {
            if(currentMenu != null){
                menuCopy();
            }else{
                new AlertBox().notify("Please Save and Select a Meal to Copy.");
                window5.close();
            }
        });
        
        controlButtons5.setAlignment(Pos.CENTER);
        controlButtons5.getChildren().clear();
        controlButtons5.getChildren().addAll(newButton5, copy5);
        
        newButton5.setOnAction(e-> { 
        MenusList.forEach(l -> {
        if(l.equals("New_Menu"))
            exists = true;
        });
        if(!exists) {
//            window5.close();
            MenusList.add("New_Menu");
            MenusList5.add(getMenuListEditBox(MenusList, "New_Menu"));
//            window5.showAndWait();
        }
        });
       
        layout5.getChildren().clear();
        layout5.getChildren().addAll(title5,headerBox5,listView5,controlButtons5,saveButton5);
        
        window5.setHeight(300);
        layout5.setAlignment(Pos.CENTER);

        window5.setAlwaysOnTop(true);
        window5.setScene(scene5);
        window5.showAndWait();
    }
  
    
public HBox getMenuListEditBox(ObservableList<String> titles, String title) {
  
    Label thisTitle = new Label(title);
    thisTitle.setPrefWidth(150);

    TextField thisNewTitle = new TextField(title);
    thisNewTitle.setPrefWidth(150);
    thisNewTitle.setOnAction(e -> {
        save.menuExport(true, thisTitle.getText());
        if(thisTitle.getText().equals(currentMenu))
            currentMenu = thisNewTitle.getText();
        setNewTitle(titles, title, thisNewTitle.getText());
        thisTitle.setText(thisNewTitle.getText());
        save.menuExport(true, thisTitle.getText());
        
    });
    
    Button thisRemove = new Button("Remove");
    thisRemove.setPrefWidth(65);
    thisRemove.setOnAction(e -> {
        titles.remove(title);
    });
    
    HBox theBox = new HBox(10);
    theBox.setAlignment(Pos.CENTER);
    theBox.getChildren().addAll(thisTitle, thisNewTitle, thisRemove);
    theBox.setPrefWidth(500);  
                
    return theBox;
}

private void setNewTitle(ObservableList<String> titles, String title, String text){
      for(int i=0; i<titles.size();i++) {
          if(titles.get(i).equals(title)) {
              titles.set(i, text);
          }              
      }
      return;
}

private void menuCopy(){
    save.menuExport(true, currentMenu);
    MenusList.add(currentMenu+" (2)");
    MenusList5.add(getMenuListEditBox(MenusList, currentMenu+" (2)")); 
    currentMenu=currentMenu+" (2)";
    save.menuExport(true, currentMenu);   
//    menuSelect.getSelectionModel().select(title+" (2)");
    return;
}



    
//***********Menu Layout Operations****************************************    

    public void updateAllIndicators() {  //exact copy in MakeMealView
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

     

     public void resetMenu() {
        Menu.clear();

        Menu.add(new Meal( -1, "*****Breakfast*****"));
        Menu.add(new Meal( -2, "*****Monday Lunch*****"));
        Menu.add(new Meal( -3, "*****Monday Dinner*****"));
        Menu.add(new Meal( -4, "*****Tuesday Lunch*****"));
        Menu.add(new Meal( -5, "*****Tuesday Dinner*****"));
        Menu.add(new Meal( -6, "*****Wednesday Lunch*****"));
        Menu.add(new Meal( -7, "*****Wednesday Dinner*****"));
        Menu.add(new Meal( -8, "*****Thursday Lunch*****"));
        Menu.add(new Meal( -9, "*****Thursday Dinner*****"));
        Menu.add(new Meal( -10, "*****Friday Lunch*****"));
        Menu.add(new Meal( -11, "*****Friday Dinner*****"));
        Menu.add(new Meal( -12, "*****Saturday Lunch*****"));
        Menu.add(new Meal( -13, "*****Saturday Dinner*****"));
        Menu.add(new Meal( -14, "*****Sunday Lunch*****"));
        Menu.add(new Meal( -15, "*****Sunday Dinner*****"));
        Menu.add(new Meal( -16, "***********************"));

        Ibreakfast.setText(R.resetIndicators(statusbreakfast));
        ImondayLunch.setText(R.resetIndicators(statusmondayLunch));
        ImondayDinner.setText(R.resetIndicators(statusmondayDinner));
        ItuesdayLunch.setText(R.resetIndicators(statustuesdayLunch));
        ItuesdayDinner.setText(R.resetIndicators(statustuesdayDinner));
        IwednessdayLunch.setText(R.resetIndicators(statuswednessdayLunch));
        IwednessdayDinner.setText(R.resetIndicators(statuswednessdayDinner));
        IthursdayLunch.setText(R.resetIndicators(statusthursdayLunch));
        IthursdayDinner.setText(R.resetIndicators(statusthursdayDinner));
        IfridayLunch.setText(R.resetIndicators(statusfridayLunch));
        IfridayDinner.setText(R.resetIndicators(statusfridayDinner));
        IsaturdayLunch.setText(R.resetIndicators(statussaturdayLunch));
        IsaturdayDinner.setText(R.resetIndicators(statussaturdayDinner));
        IsundayLunch.setText(R.resetIndicators(statussundayLunch));
        IsundayDinner.setText(R.resetIndicators(statussundayDinner));

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
    public VBox mealBoxes = new VBox(8.5);

//Menu Table Set
    public Label menuTitle = new Label("MENU");
    public ComboBox<String> menuSelect = new ComboBox(MenusList);
    public Button menuTitlesButton = R.makeButton("TITLES", 65, false);
    public HBox menuSelectControls = new HBox(15);
    public TableView<Meal> menuTable = R.makeLeftReferenceTable();
    public TextField menuAddCustom = new TextField();
    public Button menuResetButton = R.makeButton("RESET", 72, false);
    public Button menuClearMealButton = R.makeButton("CLEAR", 72, false);
    public Button menuRemoveButton = R.makeButton("REMOVE", 76, false);
    public HBox menuControls = new HBox(15); //Spacing 
    public VBox menuBox = R.fillReferenceTable("Menu", menuTable, menuAddCustom, menuControls);

//RecentList:
    public Button menuRecentButton = R.makeButton("RECENT", 75, false);
//Ingredients Table Set
    public TableView<Meal> menuIngredientsTable = R.makeSimpleReferenceTable();
    public TableView<Meal> menuRecentTable = R.makeSimpleReferenceTable();
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
    public Button menuCatalogEditButton = R.makeButton("EDIT", 85, false);
    public Button menuCatalogNewButton = R.makeButton("NEW", 85, false);
    public HBox menuCatalogControls = new HBox(25); //Spacing 
    public VBox menuCatalogViewBox = R.fillReferenceTable("Catalog", menuCatalogTable, menuCatalogControls);

    public HBox combineMenuBox = new HBox(30); //Spacing 
    public VBox wholeMenu = new VBox(15); //spacing
    
    
    
  
   
    public void setMenu() {
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
        menuAddCustom.setMaxWidth(250);
        menuControls.setAlignment(Pos.CENTER);
        menuControls.getChildren().addAll(menuResetButton, menuClearMealButton, menuRemoveButton);

        //Boxes
        menuSelect.setVisibleRowCount(5);
        menuSelect.setPromptText("Select Menu");
        menuSelectControls.setAlignment(Pos.CENTER);
        menuSelect.setPrefWidth(120);
        menuSelectControls.setPrefWidth(200);
        menuSelectControls.getChildren().addAll(menuSelect, menuTitlesButton);
        selectButtons.setAlignment(Pos.CENTER_LEFT);
        selectButtons.getChildren().addAll(selectAll, deselectAll);
        mealBoxes.setAlignment(Pos.BOTTOM_CENTER);
        mealBoxes.getChildren().addAll(menuSelectControls, R.getIndicatorsBox(Ibreakfast, breakfast), R.getIndicatorsBox(ImondayLunch, mondayLunch), R.getIndicatorsBox(ImondayDinner, mondayDinner),
                R.getIndicatorsBox(ItuesdayLunch, tuesdayLunch), R.getIndicatorsBox(ItuesdayDinner, tuesdayDinner), R.getIndicatorsBox(IwednessdayLunch, wednessdayLunch), R.getIndicatorsBox(IwednessdayDinner, wednessdayDinner),
                R.getIndicatorsBox(IthursdayLunch, thursdayLunch), R.getIndicatorsBox(IthursdayDinner, thursdayDinner), R.getIndicatorsBox(IfridayLunch, fridayLunch), R.getIndicatorsBox(IfridayDinner, fridayDinner),
                R.getIndicatorsBox(IsaturdayLunch, saturdayLunch), R.getIndicatorsBox(IsaturdayDinner, saturdayDinner), R.getIndicatorsBox(IsundayLunch, sundayLunch), R.getIndicatorsBox(IsundayDinner, sundayDinner), selectButtons);

        //Ingredients Table
        menuIngredientsEditID.setPrefWidth(50);
        menuIngredientsEditID.setStyle("-fx-alignment: CENTER;"); //center allign
        menuIngredientsControls.setAlignment(Pos.CENTER);
        menuIngredientsControls.getChildren().addAll(menuIngredientsAddButton, menuIngredientsEditID, menuIngredientsAddAllButton);
        menuIngredientsTitle.setFont(R.TitleFont);
        menuIngredientsTitle.setAlignment(Pos.CENTER_LEFT);
        menuIngredientsTitle.setMaxWidth(160);
        menuIngredientsTitleBox.setAlignment(Pos.CENTER_RIGHT);
        menuIngredientsTitleBox.getChildren().addAll(menuIngredientsTitle, menuRecentButton);
        menuIngredientsBox.setAlignment(Pos.TOP_CENTER);
//    menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox,menuIngredientsTable,menuIngredientsControls);

        //Catalog View Table
        menuCatalogSearch.setPrefWidth(150);
        menuCatalogSearch.setStyle("-fx-alignment: CENTER;"); //center allign
        //menuStats.setAlignment(Pos.CENTER);
        // menuStats.getChildren().addAll();
        menuCatalogControls.setAlignment(Pos.CENTER);
        menuCatalogControls.getChildren().addAll(menuCatalogEditButton, menuCatalogSearch, menuCatalogNewButton);

        //Catalog Assembly All Boxes
        combineMenuBox.setAlignment(Pos.CENTER);
        combineMenuBox.getChildren().addAll(menuBox, mealBoxes, menuIngredientsBox, menuCatalogViewBox);

        wholeMenu.setAlignment(Pos.TOP_CENTER);
        wholeMenu.getChildren().addAll(menuTitle, combineMenuBox);

    }

}
