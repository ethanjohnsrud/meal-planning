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

    public ObservableList<Meal> Catalog = CatalogView.Catalog;
    public ObservableList<Meal> FoodOnly = CatalogView.CatalogFoodOnly;

    private Resources R = new Resources();

    //Temporary Meal Variations
    private ObservableList<Meal> MakeMeal = FXCollections.observableArrayList();
    private ObservableList<Meal> packableMeals = FXCollections.observableArrayList();
    private ObservableList<Meal> diningMeals = FXCollections.observableArrayList();
    private ObservableList<Meal> packableVegetables = FXCollections.observableArrayList();
    private ObservableList<Meal> packableFruits = FXCollections.observableArrayList();
    private ObservableList<Meal> allCarbs = FXCollections.observableArrayList();
    private ObservableList<Meal> allFruits = FXCollections.observableArrayList();
    private ObservableList<Meal> allVegetables = FXCollections.observableArrayList();
    
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
        menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuRecentTable, menuIngredientsControls,menuIngredientsMakeMealBox);

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
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-1) + 1, 0);
                }
                if (mondayLunch.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-2) + 1, 0);
                }
                if (mondayDinner.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-3) + 1, 0);
                }
                if (tuesdayLunch.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-4) + 1, 0);
                }
                if (tuesdayDinner.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-5) + 1, 0);
                }
                if (wednessdayLunch.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-6) + 1, 0);
                }
                if (wednessdayDinner.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-7) + 1, 0);
                }
                if (thursdayLunch.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-8) + 1, 0);
                }
                if (thursdayDinner.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-9) + 1, 0);
                }
                if (fridayLunch.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-10) + 1, 0);
                }
                if (fridayDinner.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-11) + 1, 0);
                }
                if (saturdayLunch.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-12) + 1, 0);
                }
                if (saturdayDinner.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-13) + 1, 0);
                }
                if (sundayLunch.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-14) + 1, 0);
                }
                if (sundayDinner.isSelected()) {
                    menuInsert(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false), getMenuIndex(-15) + 1, 0);
                }

                menuAddCustom.clear();
            } catch (NullPointerException exe) {
            }
        });

        menuResetButton.setOnAction(e -> {
            if (new AlertBox().actionConfirmation("Are you sure you want to Clear and Reset the Menu?")) {
                resetMenu();
            }
        });

        menuClearMealButton.setOnAction(e -> {
            menuInitiateRemoveMeal();
        });

        menuIngredientsAddButton.setOnAction(e -> {
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

                }
            } catch (NullPointerException exe) {
            }
        });

        menuIngredientsAddAllButton.setOnAction(e -> {
            try {
                Meal currentMeal = catalogView.getCurrentMeal();
                menuInitiateAdd(currentMeal);
            } catch (NullPointerException exe) {
                alert.error("NullPointerException - " + Integer.toString(getMenuEditIDEntry()) + " - MenuView.menuIngredientsAddAllButton.setOnAction()--(adding meal)");
            }
        });
        
        menuIngredientsAddMade.setOnAction(e -> {
            makeMealInitiateAdd();
        });
        
        menuIngredientsMakeMeal.setOnAction(e -> {
            launchMakeMeal();
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
                    alert.error("NullPointerException - MenuView.menuIngredientsTable.addListener()");
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
                        menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuRecentTable, menuIngredientsControls,menuIngredientsMakeMealBox);
                    } else {
                        menuIngredientsTitle.setText("Ingredients");
                        menuIngredientsBox.getChildren().clear();
                        menuIngredientsTable.getSelectionModel().clearSelection();
                        menuIngredientsTable.setItems(catalogView.getCurrentMeal().Ingredients);
                        menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuIngredientsTable, menuIngredientsControls,menuIngredientsMakeMealBox);
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
            menuIngredientsBox.getChildren().addAll(menuIngredientsTitleBox, menuRecentTable, menuIngredientsControls,menuIngredientsMakeMealBox);

        });

        menuSelect.setOnAction(e -> {
            if (new AlertBox().actionRequest(false, "Do you Wish to Save: " + currentMenu+"?", "Save: "+currentMenu)) {
                new Save().menuExport(true, currentMenu);
            }
                currentMenu = menuSelect.getValue();
                if(!save.menuImport(true, currentMenu)) {
                    resetMenu();
                    new AlertBox().notify("Creating a New Menu - "+currentMenu);
                }

        });

        menuTitlesButton.setOnAction(e -> {
            new AlertBox().editMenusList(MenusList);
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

    public void menuInsert(Meal theMeal, int place, int remainingDepth) {
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

            if (breakfast.isSelected()) {
                Ibreakfast.setText(R.updateIndicators(statusbreakfast, selectedItem));
                menuAdd(selectedItem, -1);
            }
            if (mondayLunch.isSelected()) {
                ImondayLunch.setText(R.updateIndicators(statusmondayLunch, selectedItem));
                menuAdd(selectedItem, -2);
            }
            if (mondayDinner.isSelected()) {
                ImondayDinner.setText(R.updateIndicators(statusmondayDinner, selectedItem));
                menuAdd(selectedItem, -3);
            }
            if (tuesdayLunch.isSelected()) {
                ItuesdayLunch.setText(R.updateIndicators(statustuesdayLunch, selectedItem));
                menuAdd(selectedItem, -4);
            }
            if (tuesdayDinner.isSelected()) {
                ItuesdayDinner.setText(R.updateIndicators(statustuesdayDinner, selectedItem));
                menuAdd(selectedItem, -5);
            }
            if (wednessdayLunch.isSelected()) {
                IwednessdayLunch.setText(R.updateIndicators(statuswednessdayLunch, selectedItem));
                menuAdd(selectedItem, -6);
            }
            if (wednessdayDinner.isSelected()) {
                IwednessdayDinner.setText(R.updateIndicators(statuswednessdayDinner, selectedItem));
                menuAdd(selectedItem, -7);
            }
            if (thursdayLunch.isSelected()) {
                IthursdayLunch.setText(R.updateIndicators(statusthursdayLunch, selectedItem));
                menuAdd(selectedItem, -8);
            }
            if (thursdayDinner.isSelected()) {
                IthursdayDinner.setText(R.updateIndicators(statusthursdayDinner, selectedItem));
                menuAdd(selectedItem, -9);
            }
            if (fridayLunch.isSelected()) {
                IfridayLunch.setText(R.updateIndicators(statusfridayLunch, selectedItem));
                menuAdd(selectedItem, -10);
            }
            if (fridayDinner.isSelected()) {
                IfridayDinner.setText(R.updateIndicators(statusfridayDinner, selectedItem));
                menuAdd(selectedItem, -11);
            }
            if (saturdayLunch.isSelected()) {
                IsaturdayLunch.setText(R.updateIndicators(statussaturdayLunch, selectedItem));
                menuAdd(selectedItem, -12);
            }
            if (saturdayDinner.isSelected()) {
                IsaturdayDinner.setText(R.updateIndicators(statussaturdayDinner, selectedItem));
                menuAdd(selectedItem, -13);
            }
            if (sundayLunch.isSelected()) {
                IsundayLunch.setText(R.updateIndicators(statussundayLunch, selectedItem));
                menuAdd(selectedItem, -14);
            }
            if (sundayDinner.isSelected()) {
                IsundayDinner.setText(R.updateIndicators(statussundayDinner, selectedItem));
                menuAdd(selectedItem, -15);
            }

            //Adding to Recent Meals
            try {
                if (selectedItem.getID() != 0) {
                    for (int i = 0; i < Catalog.size(); i++) {
                        if (Catalog.get(i).getID() == selectedItem.getID()) {
                        MenuRecent.add(0,new Meal(false,selectedItem));  //copy
//                            MenuRecent.add(0, Catalog.get(i));  //link
                            if (MenuRecent.size() > 1) {
                                for (int j = 1; j < MenuRecent.size(); j++) {
                                    if (MenuRecent.get(j).getID() == selectedItem.getID()) {
                                        MenuRecent.remove(j);
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
                alert.error("adding/removing from RecentList - IndexOutOfBoundsException - MenuView.menuInitiateAdd()");
            } catch (NullPointerException e) {
                alert.error("adding/removing from RecentList - NullPointerException - MenuView.menuInitiateAdd()");
            }
        }

        return;
    }

    //***********Menu Remove Operations****************************************    

    public void menuRemoveMeal(int mealID) {
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

    public void menuInitiateRemoveMeal() {
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

    
    //***********MakeMeal Operations****************************************    
   public void makeMealAdd(Meal theMeal) {

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

public void resetMakeMeal() {
    MakeMeal.clear();

    MakeMeal.add(new Meal(false, -1, "*****Make Meal*****", false, false, false, false, false,false,false));
    MakeMeal.add(new Meal(false, -2, "*******************", false, false, false, false, false,false,false));
    return;
}

public String makeMealIndicatorsUpdate(Meal status) {
    String current = null;
    for(int i=1; i<MakeMeal.size(); i++) {
        current = R.updateIndicators(status,MakeMeal.get(i));
        //Adding to Recent Meals
            try {
                if (MakeMeal.get(i).getID() != 0) {
                    for (int j = 0; j < Catalog.size(); j++) {
                        if (Catalog.get(i).getID() == MakeMeal.get(i).getID()) {
                        MenuRecent.add(0,new Meal(false,MakeMeal.get(i)));  //copy
//                            MenuRecent.add(0, Catalog.get(j));  //link
                            if (MenuRecent.size() > 1) {
                                for (int l = 1; l < MenuRecent.size(); l++) {
                                    if (MenuRecent.get(l).getID() == MakeMeal.get(i).getID()) {
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
                alert.error("adding/removing from RecentList - IndexOutOfBoundsException - MenuView.makeMealIndicatorsUpdate()");
            } catch (NullPointerException e) {
                alert.error("adding/removing from RecentList - NullPointerException - MenuView.makeMealIndicatorsUpdate()");
            }
    }
    return current;
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
            if (breakfast.isSelected()) {
                Ibreakfast.setText(makeMealIndicatorsUpdate(statusbreakfast));
                makeMealAdding(-1);
            }
            if (mondayLunch.isSelected()) {
                ImondayLunch.setText(makeMealIndicatorsUpdate(statusmondayLunch));
                makeMealAdding(-2);
            }
            if (mondayDinner.isSelected()) {
                ImondayDinner.setText(makeMealIndicatorsUpdate(statusmondayDinner));
                makeMealAdding(-3);
            }
            if (tuesdayLunch.isSelected()) {
                ItuesdayLunch.setText(makeMealIndicatorsUpdate(statustuesdayLunch));
                makeMealAdding(-4);
            }
            if (tuesdayDinner.isSelected()) {
                ItuesdayDinner.setText(makeMealIndicatorsUpdate(statustuesdayDinner));
                makeMealAdding(-5);
            }
            if (wednessdayLunch.isSelected()) {
                IwednessdayLunch.setText(makeMealIndicatorsUpdate(statuswednessdayLunch));
                makeMealAdding(-6);
            }
            if (wednessdayDinner.isSelected()) {
                IwednessdayDinner.setText(makeMealIndicatorsUpdate(statuswednessdayDinner));
                makeMealAdding(-7);
            }
            if (thursdayLunch.isSelected()) {
                IthursdayLunch.setText(makeMealIndicatorsUpdate(statusthursdayLunch));
                makeMealAdding(-8);
            }
            if (thursdayDinner.isSelected()) {
                IthursdayDinner.setText(makeMealIndicatorsUpdate(statusthursdayDinner));
                makeMealAdding(-9);
            }
            if (fridayLunch.isSelected()) {
                IfridayLunch.setText(makeMealIndicatorsUpdate(statusfridayLunch));
                makeMealAdding(-10);
            }
            if (fridayDinner.isSelected()) {
                IfridayDinner.setText(makeMealIndicatorsUpdate(statusfridayDinner));
                makeMealAdding(-11);
            }
            if (saturdayLunch.isSelected()) {
                IsaturdayLunch.setText(makeMealIndicatorsUpdate(statussaturdayLunch));
                makeMealAdding(-12);
            }
            if (saturdayDinner.isSelected()) {
                IsaturdayDinner.setText(makeMealIndicatorsUpdate(statussaturdayDinner));
                makeMealAdding(-13);
            }
            if (sundayLunch.isSelected()) {
                IsundayLunch.setText(makeMealIndicatorsUpdate(statussundayLunch));
                makeMealAdding(-14);
            }
            if (sundayDinner.isSelected()) {
                IsundayDinner.setText(makeMealIndicatorsUpdate(statussundayDinner));
                makeMealAdding(-15);
            }

            
        }
    return;
}
     
private void makeMealSet() {
    makeMealTypeOptions.add("Packable");
    makeMealTypeOptions.add("Dining");
    makeMealType.setPrefWidth(250);
    makeMealType.setPromptText("Select Meal Type");
    makeMealVariations.setPrefWidth(250);
    makeMealVegetables.setPrefWidth(250);
    makeMealFruits.setPrefWidth(250);
    makeMealCarbs.setPrefWidth(250);
    makeMealCatalog.setPrefWidth(250);

    makeMealDisplay.setItems(MakeMeal);
    resetMakeMeal();
    
    
    makeMealType.setOnAction(e -> {
        
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
        makeMealAdd(new Meal(false, makeMealCatalog.getSelectionModel().getSelectedItem()));
    });
    makeMealCustomAdd.setOnAction(e -> {
        makeMealAdd(new Meal(false, 00, menuAddCustom.getText(), false, false, false, false, false,false,false));
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
        resetMakeMeal();
    });   
    makeMealGenerateButton.setOnAction(e -> {
        try {
        Random rand = new Random();
        if(makeMealType.getSelectionModel().getSelectedItem().equals("Packable")){
            if(!packableMeals.isEmpty())
                makeMealAdd(packableMeals.get(rand.nextInt(packableMeals.size())));
            if(!packableVegetables.isEmpty())
                makeMealAdd(packableVegetables.get(rand.nextInt(packableVegetables.size())));
            if(!packableFruits.isEmpty())
              makeMealAdd(packableFruits.get(rand.nextInt(packableFruits.size())));
        }
        else {
            if(!diningMeals.isEmpty())
                makeMealAdd(diningMeals.get(rand.nextInt(diningMeals.size())));
            if(!allVegetables.isEmpty())
                makeMealAdd(allVegetables.get(rand.nextInt(allVegetables.size())));
            if(!allFruits.isEmpty())
                makeMealAdd(allFruits.get(rand.nextInt(allFruits.size())));
        }       
        } catch(IndexOutOfBoundsException exe){
        alert.error("IndexOutOfBoundsException - MenuView.makeMealGenerateButton.setOnAction()");
        }
    });
    makeMealSaveButton.setOnAction(e -> makeMealwindow.close());
               
    return;
}


    
//***********Menu Layout Operations****************************************    

    public void updateAllIndicators() {
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

      
    private void generateMealVariations(){
        packableMeals.clear();
        diningMeals.clear();
        packableVegetables.clear();
        packableFruits.clear();
        allCarbs.clear();
        allFruits.clear();
        allVegetables.clear();
        
        //Figure PackableMeals & DiningMeals
        
        for(int i=0; i<Catalog.size();i++) {
            if(Catalog.get(i).getID()>=10){
                if(!Catalog.get(i).Ingredients.isEmpty()) {
                    for(int j=0; j<Catalog.get(i).Ingredients.size(); j++) {
                        if(Catalog.get(i).Ingredients.get(j).isMeat()){
                            System.out.println();
                        for(int l=0; l<Catalog.get(i).Ingredients.size(); l++) {
                            if(Catalog.get(i).Ingredients.get(l).isCarbBase()){
                            if(Catalog.get(i).isPackable()) {
                                Meal tempMeal = new Meal(Catalog.get(i), Catalog.get(i).Ingredients.get(j), Catalog.get(i).Ingredients.get(l));
                                packableMeals.add(tempMeal);
                            }else{
                                Meal tempMeal = new Meal(Catalog.get(i), Catalog.get(i).Ingredients.get(j), Catalog.get(i).Ingredients.get(l));
                                diningMeals.add(tempMeal);

                            }
                            }
                        }
                        }
                    }
                }else{            
                    if(!Catalog.get(i).isMeat() && Catalog.get(i).isCarb() && Catalog.get(i).isVegetable() && Catalog.get(i).isFruit() && Catalog.get(i).isPackable()) //only vegetable and packable
                       packableVegetables.add(Catalog.get(i));
                    if(!Catalog.get(i).isMeat() && Catalog.get(i).isCarb() && Catalog.get(i).isVegetable() && Catalog.get(i).isFruit() && Catalog.get(i).isPackable()) //only fruit and packable
                        packableFruits.add(Catalog.get(i));
                    if(!Catalog.get(i).isMeat() && Catalog.get(i).isCarb() && !Catalog.get(i).isVegetable() && !Catalog.get(i).isFruit()) //only carb
                        allCarbs.add(Catalog.get(i));
                    if(!Catalog.get(i).isMeat() && !Catalog.get(i).isCarb() && !Catalog.get(i).isVegetable() && Catalog.get(i).isFruit()) //only Fruit
                        allFruits.add(Catalog.get(i));
                    if(!Catalog.get(i).isMeat() && !Catalog.get(i).isCarb() && Catalog.get(i).isVegetable() && !Catalog.get(i).isFruit()) //only Veggie
                        allVegetables.add(Catalog.get(i));
                }
            }             
        }
                
        return;
    }

     public void resetMenu() {
        Menu.clear();

        Menu.add(new Meal(false, -1, "*****Breakfast*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -2, "*****Monday Lunch*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -3, "*****Monday Dinner*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -4, "*****Tuesday Lunch*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -5, "*****Tuesday Dinner*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -6, "*****Wednessday Lunch*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -7, "*****Wednessday Dinner*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -8, "*****Thursday Lunch*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -9, "*****Thursday Dinner*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -10, "*****Friday Lunch*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -11, "*****Friday Dinner*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -12, "*****Saturday Lunch*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -13, "*****Saturday Dinner*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -14, "*****Sunday Lunch*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -15, "*****Sunday Dinner*****", false, false, false, false, false,false,false));
        Menu.add(new Meal(false, -16, "***********************", false, false, false, false, false,false,false));

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
    public Button menuIngredientsAddMade = R.makeButton("ADD MADE", 103, false);
    public Button menuIngredientsMakeMeal = R.makeButton("MAKE MEAL", 122, false);
    public HBox menuIngredientsMakeMealBox = new HBox(25); //Spacing 
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
    
    
    //makeMeal Window
    private Stage makeMealwindow = new Stage();
    private Label makeMealtitle = new Label();
    private ObservableList<String> makeMealTypeOptions = FXCollections.observableArrayList();
    private ComboBox<String> makeMealType = new ComboBox();
    private ComboBox<Meal> makeMealVariations = new ComboBox();
    private ComboBox<Meal> makeMealVegetables = new ComboBox();
    private ComboBox<Meal> makeMealFruits = new ComboBox();
    private ComboBox<Meal> makeMealCarbs = new ComboBox(allCarbs);
    private ComboBox<Meal> makeMealCatalog = new ComboBox(Catalog);
    private TableView<Meal> makeMealDisplay = R.makeLeftReferenceTable();
    private TextField makeMealCustomAdd = new TextField();
    private Button makeMealClearButton = R.makeButton("CLEAR", 112, false);
    private Button makeMealRemoveButton = R.makeButton("REMOVE", 112, false);
    private Button makeMealGenerateButton = R.makeButton("GENERATE", 150, false);
    private Button makeMealSaveButton = R.makeButton("SAVE", 125, false);
    private HBox makeMealRemoveButtonsBox = new HBox(26);
    private HBox makeMealControlsBox = new HBox(25);
    private VBox makeMealSelectionsBox = new VBox(43);
    private VBox makeMealDisplayBox = R.fillReferenceTable(makeMealDisplay, makeMealCustomAdd, makeMealRemoveButtonsBox);
    private HBox makeMealwholeBox = new HBox(25);
    private HBox makeMealControlsButtonsBox = new HBox(50);
    private VBox makeMeallayout = new VBox(15);
    private Scene makeMealscene = new Scene(makeMeallayout);  

private void launchMakeMeal() {
        generateMealVariations();
        makeMealType.setItems(makeMealTypeOptions);
              
        makeMealwindow.setTitle("---M-A-K-E---M-E-A-L---");
        makeMealtitle.setText("---M-A-K-E---M-E-A-L---");
        makeMealtitle.setAlignment(Pos.CENTER);
        makeMealSelectionsBox.getChildren().clear();
        makeMealSelectionsBox.getChildren().addAll(makeMealVariations, makeMealVegetables, makeMealFruits, makeMealCarbs, makeMealCatalog);
        makeMealSelectionsBox.getChildren().add(makeMealGenerateButton);
        makeMealSelectionsBox.setAlignment(Pos.TOP_CENTER);
        makeMealwholeBox.getChildren().clear();
        makeMealwholeBox.getChildren().addAll(makeMealSelectionsBox, makeMealDisplayBox);
        makeMealwholeBox.setAlignment(Pos.TOP_CENTER);
        makeMealRemoveButtonsBox.getChildren().clear();
        makeMealRemoveButtonsBox.getChildren().addAll(makeMealClearButton, makeMealRemoveButton);
        makeMealRemoveButtonsBox.setAlignment(Pos.BOTTOM_CENTER);
        makeMealControlsBox.getChildren().clear();
        makeMealControlsBox.getChildren().addAll(makeMealSaveButton);
        makeMealControlsBox.setAlignment(Pos.BOTTOM_CENTER);
        makeMeallayout.getChildren().clear();
        makeMeallayout.setPrefWidth(580);
        makeMeallayout.getChildren().addAll(makeMealtitle, makeMealType, makeMealwholeBox,makeMealControlsBox);
        makeMeallayout.setAlignment(Pos.CENTER);

        makeMealwindow.setAlwaysOnTop(true);
        makeMealwindow.setScene(makeMealscene);
        makeMealwindow.showAndWait();
        
        return;
    }
  
   
    public void setMenu() {
        makeMealSet();
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
        menuIngredientsMakeMealBox.setAlignment(Pos.CENTER);
        menuIngredientsMakeMealBox.getChildren().addAll(menuIngredientsAddMade,menuIngredientsMakeMeal);
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
