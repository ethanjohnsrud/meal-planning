package MealPlanning;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.lang.NumberFormatException;
import static javafx.application.Application.launch;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

import static MealPlanning.CatalogView.Catalog;


public class MealPlanning extends Application {
    
    public static boolean print = false;
    public static boolean sampleFill = false;
    public static Settings settings = new Settings();
    public static int currentEntry = 0;        //Current meal added counter/time, for tracking recent.
       
    public static Stage primaryStage = new Stage();
    public static AlertBox alert = new AlertBox();
    public static Save save = new Save();
    public static CatalogView catalogView = new CatalogView();
    public static MealView mealView = new MealView();
    public static MenuView menuView = new MenuView();
    public static MakeMealView makeMealView = new MakeMealView();
    public static ShoppingListView shoppingListView = new ShoppingListView();
    private Resources R = new Resources();
    public ObservableList<Meal> Catalog = CatalogView.Catalog;

     
    
    @Override
    public void start(Stage primaryStage) {
       
        sceneControlsSetup();
//        mealView.runMeal();
//        catalogView.runCatalog();
//        menuView.runMenu();
//        makeMealView.runMakeMeal();
//        shoppingListView.runShoppingList();
        set();
        

        
        Scene mealScene = new Scene(mealControlBox);
//        mealScene.setFill(Color.GOLDENROD);
        Scene catalogScene = new Scene(catalogControlBox);
//        mealScene.setFill(Color.GOLDENROD);
        Scene menuScene = new Scene(menuControlBox);
//        mealScene.setFill(Color.GOLDENROD);
        Scene makeMealScene = new Scene(makeMealControlBox);
//        mealScene.setFill(Color.GOLDENROD);
        Scene shoppingListScene = new Scene(shoppingListControlBox);        
//        mealScene.setFill(Color.GOLDENROD);
        
        //Actions in CatalogScene
        catalogSaveAll.setOnAction(e ->{
           save.exportData(menuView.currentMenu, currentEntry, catalogView.mealsLast);
            });
        goToMenuFromCatalog.setOnAction(e -> {
            if(catalogView.IDset()){    
            menuView.updateMenu();
                primaryStage.setScene(menuScene);   
            }});
        goToShoppingListFromCatalog.setOnAction(e -> {
            if(catalogView.IDset()){ 
            primaryStage.setScene(shoppingListScene);   
            }});
        catalogView.catalogNewButton.setOnAction(e ->{
            if(catalogView.IDset()){
            //errorOverride = true;
            boolean found=false;  //checking for 0 Id's; origional Meals
                for(int i=0; i<catalogView.Catalog.size(); i++){
                    if(catalogView.Catalog.get(i).getID()== 0){
                        alert.error("Found an Origional Meal in Catalog - must be edited first. - ");
                        found=true;
                        break;            
                    }
                }
                if(!found){  //normal: adding new meal
                    catalogView.Catalog.add(1,new Meal(true, R.theOrigionalMeal));
                }
            catalogView.catalogEditID.setText("0");  //Going to be "a" Origional Meal either way.
            mealView.updateMeal();
            //errorOverride = false;
            primaryStage.setScene(mealScene); 
            }});
        catalogView.catalogEditButton.setOnAction(e ->{
            if(catalogView.IDset()){
            try {
                Meal selectedItem = catalogView.catalogTable.getSelectionModel().getSelectedItem();
                if(selectedItem != null) {
                    mealView.updateMeal();
                    primaryStage.setScene(mealScene); 
                }
             }catch(NullPointerException exe){}
            }});       
        
        //Actions in MealScene
        goToCatalogFromMeal.setOnAction(e -> {
            if(catalogView.IDset()){    
            catalogView.updateCatalog();
            primaryStage.setScene(catalogScene);   
            }});
        goToMenuFromMeal.setOnAction(e -> {
            if(catalogView.IDset()){ 
            menuView.updateMenu();
            primaryStage.setScene(menuScene);   
            }});
        
           //Actions in MenuScene
           menuSaveAll.setOnAction(e ->{
            save.exportData(menuView.currentMenu, currentEntry, catalogView.mealsLast);
            });
        goToCatalogFromMenu.setOnAction(e -> {
            if(catalogView.IDset()){
            catalogView.updateCatalog();
            primaryStage.setScene(catalogScene);   
            }});
        goToShoppingListFromMenu.setOnAction(e -> {
            if(catalogView.IDset()){ 
            primaryStage.setScene(shoppingListScene);   
            }});
        menuView.menuCatalogNewButton.setOnAction(e ->{
            if(catalogView.IDset()){                
            //errorOverride = true;
            boolean found=false;  //checking for 0 Id's; origional Meals
                for(int i=0; i<catalogView.Catalog.size(); i++){
                    if(catalogView.Catalog.get(i).getID()== 0){
                        alert.error("Found an Origional Meal in Catalog - must be edited first. - ");
                        found=true;
                        break;            
                    }
                }
                if(!found){  //normal: adding new meal
                    catalogView.Catalog.add(1,new Meal(true, R.theOrigionalMeal));
                }
            catalogView.catalogEditID.setText("0");  //Going to be "a" Origional Meal either way.
            mealView.updateMeal();
            //errorOverride = false;
            primaryStage.setScene(mealScene); 
            }});
        menuView.menuCatalogEditButton.setOnAction(e ->{
            if(catalogView.IDset()){
            try {
                Meal selectedItem = menuView.menuCatalogTable.getSelectionModel().getSelectedItem();
                    if(selectedItem != null) {
                        mealView.updateMeal();
                        primaryStage.setScene(mealScene); 
                    }
             }catch(NullPointerException exe){}
             }}); 
        //Actions in MakeMealScene
        goToMakeMealFromMenu.setOnAction(e -> {
            if(catalogView.IDset()){
            makeMealView.updateMakeMeal();
            primaryStage.setScene(makeMealScene);   
            }});
        makeMealSave.setOnAction(e ->{ //return to Menu
            if(catalogView.IDset()){
            menuView.updateMenu();
            primaryStage.setScene(menuScene);  
            }});
        //Actions in ShoppingListScene
        shoppingListSaveAll.setOnAction(e ->{
            save.exportData(menuView.currentMenu, currentEntry, catalogView.mealsLast);
            });
        goToCatalogFromShoppingList.setOnAction(e -> {
            if(catalogView.IDset()){
            catalogView.updateCatalog();
            primaryStage.setScene(catalogScene);   
            }});
        goToMenuFromShoppingList.setOnAction(e -> {
            if(catalogView.IDset()){
            menuView.updateMenu();
            primaryStage.setScene(menuScene);   
            }});

                
        primaryStage.setTitle("                                - M E A L - P L A N N E R -");
        primaryStage.setScene(menuScene);
        primaryStage.show();
        
//        ------------------------------------------------------------------------------  //FOR SETTING UP DATA
        
//    currentEntry = new AlertBox().getInteger(0, "CURRENT LAST USED");

//    for(int i = 0; i<Catalog.size(); i++){
//        Catalog.get(i).setPopularity(new AlertBox().getInteger(0, Catalog.get(i).getID()+" - "+Catalog.get(i).getName()+"\n"+"POPULARITY"));
//        Catalog.get(i).setLastUsed(new AlertBox().getInteger(0, Catalog.get(i).getID()+" - "+Catalog.get(i).getName()+"\n"+"LAST USED"));
//      
//        
//    }
        
//        ------------------------------------------------------------------------------
        
        catalogView.endLaunching();   
    }
    

    
    Button goToCatalogFromMeal = R.makeButton("CATALOG", 150, false);
    Button goToCatalogFromMenu = R.makeButton("CATALOG", 150, false);
    Button goToMakeMealFromMenu = R.makeButton("Make Meal", 150, false);
    Button makeMealSave = R.makeButton("SAVE", 150, false);
    Button goToMenuFromCatalog = R.makeButton("MENU", 150, false);
    Button goToMenuFromMeal = R.makeButton("MENU", 150, false);
    Button goToCatalogFromShoppingList = R.makeButton("CATALOG", 150, false);
    Button goToMenuFromShoppingList = R.makeButton("MENU", 150, false);
    Button goToShoppingListFromCatalog = R.makeButton("SHOPPING LIST", 150, false);
    Button goToShoppingListFromMenu = R.makeButton("SHOPPING LIST", 150, false);
    Button catalogSaveAll = R.makeButton("SAVE ALL", 150, false);
    Button menuSaveAll = R.makeButton("SAVE ALL", 150, false);
    Button shoppingListSaveAll = R.makeButton("SAVE ALL", 150, false);
    

    HBox mealButtonsBox = new HBox(50);
    VBox mealControlBox = new VBox(20);
    VBox makeMealControlBox = new VBox(20);
    HBox catalogButtonsBox = new HBox(50);
    VBox catalogControlBox = new VBox(20);
    HBox menuButtonsBox = new HBox(50);
    VBox menuControlBox = new VBox(20);
    HBox shoppingListButtonsBox = new HBox(50);
    VBox shoppingListControlBox = new VBox(20);
    
    
    Font TitleFont = new Font("Book Antiqua", 25);
    Font DetailFont = new Font("Book Antiqua", 13);
        

void set()
{    
    if(sampleFill) {
        catalogView.Catalog.add(new Meal(true, 11,"mealOne",3,5,true,false,false,true,true,false,false, false));
        catalogView.Catalog.add(new Meal(true, 22,"mealTwo",5, 3, true,true,false,false,false,true,false, false));
        catalogView.Catalog.add(new Meal(true, 33,"mealThree",11, 1, true,true,false,false,false,false,true, false));
        catalogView.Catalog.add(new Meal(true, 44,"mealFour", 35, 7, false,true,true,false,true,true,true, false));
        catalogView.Catalog.add(new Meal(true, 55,"mealFive",17, 9, true,false,false,false,false,false,false, false));      
        
       save.exportData(menuView.currentMenu, currentEntry, catalogView.mealsLast);
    }
    else         //Inport Data
        settings = save.importData();

    catalogView.mealsLast = settings.getMealsLast();
    currentEntry = settings.getCurrentEntry();
    if(currentEntry<=0)
        alert.error("### - "+currentEntry+" - Current Entry (date) is reading zero for Calculations - MealPlanning.set()");

    catalogView.updateCatalog();
    mealView.updateMeal();
    menuView.updateMenu();
    makeMealView.updateMakeMeal();
    shoppingListView.updateShoppingList();

return;    
}

//Scene Controls Setup
void sceneControlsSetup()
{
//Catalog
    catalogButtonsBox.getChildren().addAll(goToMenuFromCatalog, catalogSaveAll, goToShoppingListFromCatalog);
    //catalogButtonsBox.getChildren().add(testButtons);
    catalogButtonsBox.setAlignment(Pos.CENTER);
    catalogControlBox.getChildren().addAll(catalogView.wholeCatalog, catalogButtonsBox);
    catalogControlBox.setAlignment(Pos.CENTER); 
    //Meal
    mealButtonsBox.getChildren().addAll(goToCatalogFromMeal, goToMenuFromMeal);
    mealButtonsBox.setAlignment(Pos.CENTER);
    mealControlBox.getChildren().addAll(mealView.wholeMeal, mealButtonsBox);
    mealControlBox.setAlignment(Pos.CENTER); 
    //Menu
    menuButtonsBox.getChildren().addAll(goToCatalogFromMenu, menuSaveAll, goToMakeMealFromMenu, goToShoppingListFromMenu);
    menuButtonsBox.setAlignment(Pos.CENTER);
    menuControlBox.getChildren().addAll(menuView.wholeMenu, menuButtonsBox);
    menuControlBox.setAlignment(Pos.CENTER); 
    //MakeMealView
    makeMealControlBox.getChildren().addAll(makeMealView.wholeMakeMeal, makeMealSave);
    makeMealControlBox.setAlignment(Pos.CENTER); 
    //Shopping List
    shoppingListButtonsBox.getChildren().addAll(goToCatalogFromShoppingList, shoppingListSaveAll, goToMenuFromShoppingList);
    shoppingListButtonsBox.setAlignment(Pos.CENTER);
    shoppingListControlBox.getChildren().addAll(shoppingListView.wholeShoppingList, shoppingListButtonsBox);
    shoppingListControlBox.setAlignment(Pos.CENTER);    
    
    return;
}
        

 
    public static void main(String[] args) {
        launch(args);
    }

       
}

