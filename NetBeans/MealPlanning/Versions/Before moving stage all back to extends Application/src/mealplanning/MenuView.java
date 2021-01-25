/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

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
        updateMenu();
    }
    
    //MENU
    static final ObservableList<Meal> Menu = FXCollections.observableArrayList();

    private Stage primaryStage = MealPlanning.primaryStage;
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
  
    menuTable.setItems(null);
    menuTable.setItems(Menu);
    menuTable.refresh();
    
    menuIngredientsTable.setItems(null);
    //menuIngredientsTable.setItems(getCurrentMeal().Ingredients);
    menuIngredientsTable.refresh();
    
    menuCatalogTable.setItems(null);
    menuCatalogTable.setItems(Catalog);
    menuCatalogTable.refresh();
    
    return;
}

public void menuRemove() {
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
     }

public void runMenu() {
    //Actions in MenuScene
           menuSaveAll.setOnAction(e ->{
            save.exportData();
            });
        goToCatalogFromMenu.setOnAction(e -> {
                    catalogView.updateCatalog();
                    primaryStage.setScene(catalogView.catalogScene);   
            });
        goToShoppingListFromMenu.setOnAction(e -> {
                    primaryStage.setScene(shoppingListView.shoppingListScene);   
            });
        menuCatalogNewButton.setOnAction(e ->{
            //errorOverride = true;
            boolean found=false;  //checking for 0 Id's; origional Meals
                for(int i=0; i<Catalog.size(); i++){
                    if(Catalog.get(i).getID()== 0){
                        alert.error("Found an Origional Meal in Catalog - must be edited first. - ");
                        found=true;
                        break;            
                    }
                }
                if(!found){  //normal: adding new meal
                    Catalog.add(1,new Meal(true, R.theOrigionalMeal));
                }
            catalogView.catalogEditID.setText("0");  //Going to be "a" Origional Meal either way.
            mealView.updateMeal();
            //errorOverride = false;
            primaryStage.setScene(mealView.mealScene); 
            });
        menuCatalogEditButton.setOnAction(e ->{
            try {
                boolean found=false;  //checking for 0 Id's; origional Meals
                for(int i=0; i<Catalog.size(); i++){
                    if(Catalog.get(i).getID()== 0){
                        alert.error("Found an Origional Meal in Catalog - must be edited first. - ");
                        found=true;
                        catalogView.catalogEditID.setText("0");
                        mealView.updateMeal();
                        primaryStage.setScene(mealView.mealScene);
                        break;            
                    }
                }
                if(!found){  //normal: Edit selected Meal
                    Meal selectedItem = menuCatalogTable.getSelectionModel().getSelectedItem();
                    if(selectedItem != null) {
                        mealView.updateMeal();
                        primaryStage.setScene(mealView.mealScene); 
                    }
                }
             }catch(NullPointerException exe){}
             }); 
        
    menuAddCustom.setOnAction( e -> {
        try {
        if(breakfast.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-1)+1,0);
        if(mondayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-2)+1,0);
        if(mondayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-3)+1,0);
        if(tuesdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-4)+1,0);
        if(tuesdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-5)+1,0);
        if(wednessdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-6)+1,0);
        if(wednessdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-7)+1,0);
        if(thursdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-8)+1,0);
        if(thursdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-9)+1,0);
        if(fridayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-10)+1,0);
        if(fridayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-11)+1,0);
        if(saturdayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-12)+1,0);
        if(saturdayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-13)+1,0);
        if(sundayLunch.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-14)+1,0);
        if(sundayDinner.isSelected())
            menuInsert(new Meal(false,00,menuAddCustom.getText(),false,false,false,false), getMenuIndex(-15)+1,0);
       
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
        Meal selectedItem = menuIngredientsTable.getSelectionModel().getSelectedItem();
            menuInitiateAdd(selectedItem);
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
        if (newSelection  !=  null) {
            Meal selectedItem = menuIngredientsTable.getSelectionModel().getSelectedItem();
            menuIngredientsEditID.setText(Integer.toString(selectedItem.getID()));
        }
    }catch(NullPointerException e){}
    });
        //***currently only selects entry in ingredients table -> must also iniate add to menu
    menuIngredientsEditID.setOnAction( e -> {
        try{
            Meal selectedItem = menuCatalogTable.getSelectionModel().getSelectedItem();
            boolean found = false;
            for(int i = 0; i<selectedItem.Ingredients.size(); i++) {
                    if (selectedItem.Ingredients.get(i).getID() == getMenuEditIDEntry()) {
                        found = true;
                        menuIngredientsTable.getSelectionModel().select(i);
                        menuInitiateAdd(selectedItem.Ingredients.get(i));
                        break;
                    }
           }
            if(!found) {
                alert.error("Ingredient not found in " + Integer.toString(selectedItem.getID()) + " - " + selectedItem.getName() +" - ingredientsEditID.setOnAction()--(adding ingredient)");
                menuIngredientsEditID.setText("");
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
        catalogView.catalogIngredientsTable.setItems(selectedItem.Ingredients);
        menuCatalogEditID.setText(Integer.toString(selectedItem.getID()));
        menuIngredientsTable.setItems(selectedItem.Ingredients);
        }
    }catch(NullPointerException e){}
    });
    menuCatalogEditID.setOnAction( e -> {
        try {
         boolean found=false;
         for(int i=0; i<Catalog.size(); i++){
             if(Catalog.get(i).getID()== getMenuCatalogEditIDEntry()){
                 found=true;
                 catalogView.catalogTable.getSelectionModel().select(i);
                 catalogView.catalogIngredientsTable.setItems(Catalog.get(i).Ingredients);
                 menuCatalogTable.getSelectionModel().select(i);
                 menuIngredientsTable.setItems(Catalog.get(i).Ingredients);
                 Catalog.get(i).print();
                 break;
             }
         }
         if(!found) {
            alert.error("Meal not found in Catalog - "+Integer.toString(getMenuCatalogEditIDEntry())+" - Menu-idText.setOnAction()");
            catalogView.catalogEditID.setText("");
            catalogView.catalogChangeID.setText("");
            menuCatalogEditID.setText("");
         }

      } catch(NumberFormatException exe) {
          exe.printStackTrace();
         alert.error("Input is not a valid integer - menuCatalogEditID.setOnAction");
     } 
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

public  int getMenuCatalogEditIDEntry(){
 int entry = 0;
    try{
        if(Integer.parseInt(menuCatalogEditID.getText()) > 0)
            entry = Integer.parseInt(menuCatalogEditID.getText());
        else {
            entry = 0;
            alert.error("Input is not a valid integer - getMenuCatalogEditIDEntry()");
            }    
    } catch(NumberFormatException ex) {
          entry = 0;
       alert.error("Input is not a valid integer - getMenuCatalogEditIDEntry()");
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
            if(breakfast.isSelected())
                menuAdd(selectedItem,-1);
            if(mondayLunch.isSelected())
                menuAdd(selectedItem,-2);
            if(mondayDinner.isSelected())
                menuAdd(selectedItem,-3);
            if(tuesdayLunch.isSelected())
                menuAdd(selectedItem,-4);
            if(tuesdayDinner.isSelected())
                menuAdd(selectedItem,-5);
            if(wednessdayLunch.isSelected())
                menuAdd(selectedItem,-6);
            if(wednessdayDinner.isSelected())
                menuAdd(selectedItem,-7);
            if(thursdayLunch.isSelected())
                menuAdd(selectedItem,-8);
            if(thursdayDinner.isSelected())
                menuAdd(selectedItem,-9);
            if(fridayLunch.isSelected())
                menuAdd(selectedItem,-10);
            if(fridayDinner.isSelected())
                menuAdd(selectedItem,-11);
            if(saturdayLunch.isSelected())
                menuAdd(selectedItem,-12);
            if(saturdayDinner.isSelected())
                menuAdd(selectedItem,-13);
            if(sundayLunch.isSelected())
                menuAdd(selectedItem,-14);
            if(sundayDinner.isSelected())
                menuAdd(selectedItem,-15);
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
            if(breakfast.isSelected())
                menuRemoveMeal(-1);
            if(mondayLunch.isSelected())
                menuRemoveMeal(-2);
            if(mondayDinner.isSelected())
                menuRemoveMeal(-3);
            if(tuesdayLunch.isSelected())
                menuRemoveMeal(-4);
            if(tuesdayDinner.isSelected())
                menuRemoveMeal(-5);
            if(wednessdayLunch.isSelected())
                menuRemoveMeal(-6);
            if(wednessdayDinner.isSelected())
                menuRemoveMeal(-7);
            if(thursdayLunch.isSelected())
                menuRemoveMeal(-8);
            if(thursdayDinner.isSelected())
                menuRemoveMeal(-9);
            if(fridayLunch.isSelected())
                menuRemoveMeal(-10);
            if(fridayDinner.isSelected())
                menuRemoveMeal(-11);
            if(saturdayLunch.isSelected())
                menuRemoveMeal(-12);
            if(saturdayDinner.isSelected())
                menuRemoveMeal(-13);
            if(sundayLunch.isSelected())
                menuRemoveMeal(-14);
            if(sundayDinner.isSelected())
                menuRemoveMeal(-15);
    return;
}

public void resetMenu() {
    Menu.clear();
    
    Menu.add(new Meal(false,-1,"*****Breakfast*****",false,false,false,false));
    Menu.add(new Meal(false,-2,"*****Monday Lunch*****",false,false,false,false));
    Menu.add(new Meal(false,-3,"*****Monday Dinner*****",false,false,false,false));
    Menu.add(new Meal(false,-4,"*****Tuesday Lunch*****",false,false,false,false));
    Menu.add(new Meal(false,-5,"*****Tuesday Dinner*****",false,false,false,false));
    Menu.add(new Meal(false,-6,"*****Wednessday Lunch*****",false,false,false,false));
    Menu.add(new Meal(false,-7,"*****Wednessday Dinner*****",false,false,false,false));
    Menu.add(new Meal(false,-8,"*****Thursday Lunch*****",false,false,false,false));
    Menu.add(new Meal(false,-9,"*****Thursday Dinner*****",false,false,false,false));
    Menu.add(new Meal(false,-10,"*****Friday Lunch*****",false,false,false,false));
    Menu.add(new Meal(false,-11,"*****Friday Dinner*****",false,false,false,false));
    Menu.add(new Meal(false,-12,"*****Saturday Lunch*****",false,false,false,false));
    Menu.add(new Meal(false,-13,"*****Saturday Dinner*****",false,false,false,false));
    Menu.add(new Meal(false,-14,"*****Sunday Lunch*****",false,false,false,false));
    Menu.add(new Meal(false,-15,"*****Sunday Dinner*****",false,false,false,false));
    Menu.add(new Meal(false,-16,"***********************",false,false,false,false));

    
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

private Button selectAll = R.makeButton("ALL", 60, false);
private Button deselectAll = R.makeButton("NONE", 60, false);

public HBox selectButtons = new HBox(15);
public VBox mealBoxes = new VBox(10);

//Menu Table Set
public Label menuTitle = new Label("MENU");
public TableView<Meal> menuTable = R.makeLeftReferenceTable();
public TextField menuAddCustom = new TextField();
public Button menuResetButton = R.makeButton("RESET", 80, false);
public Button menuClearMealButton = R.makeButton("CLEAR", 80, false);
public Button menuRemoveButton = R.makeButton("REMOVE", 80, false);
public HBox menuControls = new HBox(15); //Spacing 
public VBox menuBox = R.fillReferenceTable("Menu", menuTable, menuAddCustom, menuControls);

//Ingredients Table Set
public TableView<Meal> menuIngredientsTable = R.makeSimpleReferenceTable();
public Button menuIngredientsAddButton = R.makeButton("ADD", 65, false);
public TextField menuIngredientsEditID = new TextField();
public Button menuIngredientsAddAllButton = R.makeButton("ADD ALL", 85, false);
public HBox menuIngredientsControls = new HBox(25); //Spacing 
public VBox menuIngredientsBox = R.fillReferenceTable("Ingredients", menuIngredientsTable,menuIngredientsControls); 

//Catalog View Set:
public TableView<Meal> menuCatalogTable = R.makeDetailedReferenceTable();
public TextField menuCatalogEditID = new TextField();
public Button menuCatalogEditButton = R.makeButton("EDIT", 135, false);
public Button menuCatalogNewButton = R.makeButton("NEW", 135, false);
public HBox menuCatalogControls = new HBox(25); //Spacing 
public VBox menuCatalogViewBox = R.fillReferenceTable("Catalog", menuCatalogTable, menuCatalogControls);

public HBox combineMenuBox = new HBox(30); //Spacing 
public VBox wholeMenu = new VBox(15); //spacing

public Button goToCatalogFromMenu = R.makeButton("CATALOG", 100, false);
public Button goToShoppingListFromMenu = R.makeButton("SHOPPING LIST", 200, false);
public Button menuSaveAll = R.makeButton("SAVE ALL", 100, false);

HBox menuButtonsBox = new HBox(50);
VBox menuControlBox = new VBox(30);

public Scene menuScene;


             
public void setMenu()
{
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
    mealBoxes.getChildren().addAll(breakfast,mondayLunch,mondayDinner,tuesdayLunch,tuesdayDinner,wednessdayLunch,wednessdayDinner,
        thursdayLunch, thursdayDinner, fridayLunch, fridayDinner, saturdayLunch, saturdayDinner, sundayLunch, sundayDinner, selectButtons);

    //Ingredients Table
    menuIngredientsEditID.setPrefWidth(50);  
    menuIngredientsEditID.setStyle( "-fx-alignment: CENTER;"); //center allign
    menuIngredientsControls.setAlignment(Pos.CENTER);
    menuIngredientsControls.getChildren().addAll(menuIngredientsAddButton, menuIngredientsEditID, menuIngredientsAddAllButton);

    
    //Catalog View Table
    menuCatalogEditID.setPrefWidth(50);  
    menuCatalogEditID.setStyle( "-fx-alignment: CENTER;"); //center allign
    //menuStats.setAlignment(Pos.CENTER);
   // menuStats.getChildren().addAll();
    menuCatalogControls.setAlignment(Pos.CENTER);
    menuCatalogControls.getChildren().addAll(menuCatalogEditButton, menuCatalogEditID, menuCatalogNewButton);
      
    //Catalog Assembly All Boxes
    combineMenuBox.setAlignment(Pos.CENTER);
    combineMenuBox.getChildren().addAll(menuBox, mealBoxes, menuIngredientsBox, menuCatalogViewBox); 
   
    wholeMenu.setAlignment(Pos.TOP_CENTER);
    wholeMenu.getChildren().addAll(menuTitle,combineMenuBox);
    
    //Menu
    goToCatalogFromMenu.setFont(R.DetailFont);
    goToCatalogFromMenu.setPrefWidth(100);
    goToShoppingListFromMenu.setFont(R.DetailFont);
    goToShoppingListFromMenu.setPrefWidth(150);
    menuSaveAll.setFont(R.DetailFont);
    menuSaveAll.setPrefWidth(150);
    menuButtonsBox.getChildren().addAll(goToCatalogFromMenu, menuSaveAll, goToShoppingListFromMenu);
    menuButtonsBox.setAlignment(Pos.CENTER);
    menuControlBox.getChildren().addAll(wholeMenu, menuButtonsBox);
    menuControlBox.setAlignment(Pos.CENTER); 
    
    menuScene = new Scene(menuControlBox);
}



}
