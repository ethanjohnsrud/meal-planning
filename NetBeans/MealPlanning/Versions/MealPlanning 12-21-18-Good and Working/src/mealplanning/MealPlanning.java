package mealplanning;


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

public class MealPlanning extends Application {
    
    static final boolean print = false;
    static final boolean sampleFill = false;
       
    static Stage primaryStage = new Stage();
    static AlertBox alert = new AlertBox();
    static Save save = new Save();
    static CatalogView catalogView = new CatalogView();
    static MealView mealView = new MealView();
    static MenuView menuView = new MenuView();
    static ShoppingListView shoppingListView = new ShoppingListView();
    private Resources R = new Resources();
     
    
    @Override
    public void start(Stage primaryStage) {
       
        sceneControlsSetup();
        mealView.runMeal();
        catalogView.runCatalog();
        menuView.runMenu();
        shoppingListView.runShoppingList();
        set();
        

        
        Scene mealScene = new Scene(mealControlBox);
        Scene catalogScene = new Scene(catalogControlBox);
        Scene menuScene = new Scene(menuControlBox);
        Scene shoppingListScene = new Scene(shoppingListControlBox);
        //Actions in CatalogScene
        catalogSaveAll.setOnAction(e ->{
           save.exportData(menuView.currentMenu);
            });
        goToMenuFromCatalog.setOnAction(e -> {
                menuView.updateMenu();
                primaryStage.setScene(menuScene);   
            });
        goToShoppingListFromCatalog.setOnAction(e -> {
                primaryStage.setScene(shoppingListScene);   
            });
        catalogView.catalogNewButton.setOnAction(e ->{
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
            });
        catalogView.catalogEditButton.setOnAction(e ->{
            try {
                Meal selectedItem = catalogView.catalogTable.getSelectionModel().getSelectedItem();
                if(selectedItem != null) {
                    mealView.updateMeal();
                    primaryStage.setScene(mealScene); 
                }
             }catch(NullPointerException exe){}
             });       
        
        //Actions in MealScene
        goToCatalogFromMeal.setOnAction(e -> {
                    catalogView.updateCatalog();
                    primaryStage.setScene(catalogScene);   
            });
        goToMenuFromMeal.setOnAction(e -> {
                    menuView.updateMenu();
                    primaryStage.setScene(menuScene);   
            });
        
           //Actions in MenuScene
           menuSaveAll.setOnAction(e ->{
            save.exportData(menuView.currentMenu);
            });
        goToCatalogFromMenu.setOnAction(e -> {
                    catalogView.updateCatalog();
                    primaryStage.setScene(catalogScene);   
            });
        goToShoppingListFromMenu.setOnAction(e -> {
                    primaryStage.setScene(shoppingListScene);   
            });
        menuView.menuCatalogNewButton.setOnAction(e ->{
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
            });
        menuView.menuCatalogEditButton.setOnAction(e ->{
            try {
                Meal selectedItem = menuView.menuCatalogTable.getSelectionModel().getSelectedItem();
                    if(selectedItem != null) {
                        mealView.updateMeal();
                        primaryStage.setScene(mealScene); 
                    }
             }catch(NullPointerException exe){}
             }); 
        //Actions in ShoppingListScene
        shoppingListSaveAll.setOnAction(e ->{
            save.exportData(menuView.currentMenu);
            });
        goToCatalogFromShoppingList.setOnAction(e -> {
                    catalogView.updateCatalog();
                    primaryStage.setScene(catalogScene);   
            });
        goToMenuFromShoppingList.setOnAction(e -> {
                    menuView.updateMenu();
                    primaryStage.setScene(menuScene);   
            });

                
        primaryStage.setTitle("                                - M E A L - P L A N N E R -");
        primaryStage.setScene(menuScene);
        primaryStage.show();
        
        catalogView.endLaunching();       

    }
    
    Button goToCatalogFromMeal = R.makeButton("CATALOG", 100, false);
    Button goToCatalogFromMenu = R.makeButton("CATALOG", 100, false);
    Button goToMenuFromCatalog = R.makeButton("MENU", 100, false);
    Button goToMenuFromMeal = R.makeButton("MENU", 100, false);
    Button goToCatalogFromShoppingList = R.makeButton("CATALOG", 100, false);
    Button goToMenuFromShoppingList = R.makeButton("MENU", 100, false);
    Button goToShoppingListFromCatalog = R.makeButton("SHOPPING LIST", 200, false);
    Button goToShoppingListFromMenu = R.makeButton("SHOPPING LIST", 200, false);
    Button catalogSaveAll = R.makeButton("SAVE ALL", 100, false);
    Button menuSaveAll = R.makeButton("SAVE ALL", 100, false);
    Button shoppingListSaveAll = R.makeButton("SAVE ALL", 100, false);
    

    HBox mealButtonsBox = new HBox(50);
    VBox mealControlBox = new VBox(30);
    HBox catalogButtonsBox = new HBox(50);
    VBox catalogControlBox = new VBox(30);
    HBox menuButtonsBox = new HBox(50);
    VBox menuControlBox = new VBox(30);
    HBox shoppingListButtonsBox = new HBox(50);
    VBox shoppingListControlBox = new VBox(30);
    
    
    Font TitleFont = new Font("Book Antiqua", 25);
    Font DetailFont = new Font("Book Antiqua", 13);
        

void set()
{    
    if(sampleFill) {
        catalogView.Catalog.add(new Meal(true, 11,"mealOne",true,false,false,true,true));
        catalogView.Catalog.add(new Meal(true, 22,"mealTwo",true,true,false,false,false));
        catalogView.Catalog.add(new Meal(true, 33,"mealThree",true,true,false,false,false));
        catalogView.Catalog.add(new Meal(true, 44,"mealFour",false,true,true,false,true));
        catalogView.Catalog.add(new Meal(true, 55,"mealFive",true,false,false,false,false));      
        
       save.exportData(menuView.currentMenu);
    }
    else         //Inport Data
        save.importData();

    catalogView.updateCatalog();
    mealView.updateMeal();
    menuView.updateMenu();
    shoppingListView.updateShoppingList();

return;    
}

//Scene Controls Setup
void sceneControlsSetup()
{
//Catalog
    goToMenuFromCatalog.setFont(DetailFont);
    goToMenuFromCatalog.setPrefWidth(100);
    goToShoppingListFromCatalog.setFont(DetailFont);
    goToShoppingListFromCatalog.setPrefWidth(150);
    catalogSaveAll.setFont(DetailFont);
    catalogSaveAll.setPrefWidth(150);
    catalogButtonsBox.getChildren().addAll(goToMenuFromCatalog, catalogSaveAll, goToShoppingListFromCatalog);
    //catalogButtonsBox.getChildren().add(testButtons);
    catalogButtonsBox.setAlignment(Pos.CENTER);
    catalogControlBox.getChildren().addAll(catalogView.wholeCatalog, catalogButtonsBox);
    catalogControlBox.setAlignment(Pos.CENTER); 
    //Meal
    goToCatalogFromMeal.setFont(DetailFont);
    goToCatalogFromMeal.setPrefWidth(100);
    goToMenuFromMeal.setFont(DetailFont);
    goToMenuFromMeal.setPrefWidth(100);
    mealButtonsBox.getChildren().addAll(goToCatalogFromMeal, goToMenuFromMeal);
    mealButtonsBox.setAlignment(Pos.CENTER);
    mealControlBox.getChildren().addAll(mealView.wholeMeal, mealButtonsBox);
    mealControlBox.setAlignment(Pos.CENTER); 
    //Menu
    goToCatalogFromMenu.setFont(DetailFont);
    goToCatalogFromMenu.setPrefWidth(100);
    goToShoppingListFromMenu.setFont(DetailFont);
    goToShoppingListFromMenu.setPrefWidth(150);
    menuSaveAll.setFont(DetailFont);
    menuSaveAll.setPrefWidth(150);
    menuButtonsBox.getChildren().addAll(goToCatalogFromMenu, menuSaveAll, goToShoppingListFromMenu);
    menuButtonsBox.setAlignment(Pos.CENTER);
    menuControlBox.getChildren().addAll(menuView.wholeMenu, menuButtonsBox);
    menuControlBox.setAlignment(Pos.CENTER); 
    //Shopping List
    goToCatalogFromShoppingList.setFont(DetailFont);
    goToCatalogFromShoppingList.setPrefWidth(100);
    goToMenuFromShoppingList.setFont(DetailFont);
    shoppingListSaveAll.setPrefWidth(100);
    shoppingListSaveAll.setFont(DetailFont);
    goToMenuFromShoppingList.setPrefWidth(100);
    shoppingListButtonsBox.getChildren().addAll(goToCatalogFromShoppingList, shoppingListSaveAll, goToMenuFromShoppingList);
    shoppingListButtonsBox.setAlignment(Pos.CENTER);
    shoppingListControlBox.getChildren().addAll(shoppingListView.wholeShoppingList, shoppingListButtonsBox);
    shoppingListControlBox.setAlignment(Pos.CENTER);    
    
    return;
}
        

 
    public static void main(String[] args) {
        launch(args);
    }

    private String toString(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
}
