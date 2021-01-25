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
    static CatalogView catalogView = new CatalogView();
    static MealView mealView = new MealView();
    static MenuView menuView = new MenuView();
    static ShoppingListView shoppingListView = new ShoppingListView();
    static Save save = new Save();
    private Resources R = new Resources();
    static boolean launching = true;  //overrides getCurrentMeal() exclusively while loading inports   
     
    
    @Override
    public void start(Stage mainStage) {        
     
        setData();
        catalogView.runCatalog();
        mealView.runMeal();
        menuView.runMenu();
        shoppingListView.runShoppingList();
        menuView.menuRemoveButton.setOnAction( e -> {
            menuView.menuRemove();
        });

        
        primaryStage.setTitle("                                - M E A L - P L A N N E R -");
        primaryStage.setScene(menuView.menuScene);
        primaryStage.show();
        
        launching = false;
    }
      

private void setData()
{    
    if(sampleFill) {
        CatalogView.Catalog.add(new Meal(true, 11,"mealOne",true,false,false,true));
        CatalogView.Catalog.add(new Meal(true, 22,"mealTwo",true,true,false,false));
        CatalogView.Catalog.add(new Meal(true, 33,"mealThree",true,true,false,false));
        CatalogView.Catalog.add(new Meal(true, 44,"mealFour",false,true,true,false));
        CatalogView.Catalog.add(new Meal(true, 55,"mealFive",true,false,false,false));      
        
        save.exportData();
    }
    else         //Import Data
        save.importData();

//    catalogView.updateCatalog();
//    mealView.updateMeal();
//    menuView.updateMenu();
//    shoppingListView.updateShoppingList();

return;    
}



        

 
    public static void main(String[] args) {
        launch(args);
    }

    private String toString(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
}
