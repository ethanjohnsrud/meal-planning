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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import static mealplanning.MealPlanning.save;

/**
 *
 * @author ethan
 */

public class CatalogView {
    
    CatalogView() {
        setCatalog();
        runCatalog();
    }
    
        //Catalog
    static final ObservableList<Meal> Catalog = FXCollections.observableArrayList();
    static final ObservableList<Meal> CatalogFoodOnly = FXCollections.observableArrayList();
    
    private CatalogView catalogView = MealPlanning.catalogView;
    private MealView mealView = MealPlanning.mealView;
    private MenuView menuView = MealPlanning.menuView;
    private ShoppingListView shoppingListView = MealPlanning.shoppingListView;
    private AlertBox alert = MealPlanning.alert;
    private Save save = MealPlanning.save;
    private boolean launching = true;  //overrides errors for no current meal
    private Resources R = new Resources();    
    
    
    
    //*****CATALOG*****   
    public void updateCatalog() {
        save.checkIngredients();  //varify
        try {
        for(int i = 1; i<Catalog.size(); i++) {  //Insertion Sort Algorithm   
            int j = i;  
            Meal keyMeal;
            while (j>0 && Catalog.get(j-1).getID() > Catalog.get(j).getID())  {    //move upwards
                keyMeal = new Meal(Catalog.get(j));
                Catalog.remove(j);
                Catalog.add(j-1,keyMeal);
                j--; 
            } 
        }
        }catch(NullPointerException exe) {
            alert.error("Insertion Algorithm Failed sorting Catalog, may have lost meals - NullPointerException - updateCatalog()");
        }
        catalogChangeID.setText(catalogEditID.getText());
        catalogTable.setItems(null);
        catalogTable.setItems(Catalog);
        catalogTable.refresh();
        catalogIngredientsTable.setItems(null);
        
        return;
    }


    public void runCatalog() {
        testNotify.setOnAction(e -> {new AlertBox().notify("Test Notification");});
        testError.setOnAction(e -> {alert.error("Test Error");});
        testRequest.setOnAction(e -> {
            if(new AlertBox().actionRequest("Error detected.  Suggest to perform Action Below:", "Action to Perform"))
                new AlertBox().notify("Action Taken");
            else
                new AlertBox().notify("Action Ignored");
        });

        
        
          catalogRemoveButton.setOnAction( e -> { 
              try {
              Meal selectedItem = catalogTable.getSelectionModel().getSelectedItem();
    //          if(getCatalogEditIDEntry()==selectedItem.getID()) {
    //                for(int i=0; i<CatalogFoodOnly.size(); i++){
    //                    if(CatalogFoodOnly.get(i).getID() == selectedItem.getID()) {
    //                        CatalogFoodOnly.remove(i);
    //                        break;
    //                    }
    //                }           
               catalogTable.getItems().remove(selectedItem);
    //          }
              }catch(NullPointerException exe){}
              });

    //add button in start because must initiate scene change.   

    //Edit button in start because must initiate scene change. 

    //EditID:
        catalogTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
        try{
            if (newSelection  !=  null) {
            Meal selectedItem = catalogTable.getSelectionModel().getSelectedItem();
            catalogEditID.setText(Integer.toString(selectedItem.getID()));
            catalogChangeID.setText(Integer.toString(selectedItem.getID()));
            catalogIngredientsTable.setItems(selectedItem.Ingredients);
            menuView.menuIngredientsTable.setItems(selectedItem.Ingredients);
            }
        }catch(NullPointerException e){}
        });
        catalogIngredientsTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
        try{
            if (newSelection  !=  null) {
            Meal selectedItem = catalogIngredientsTable.getSelectionModel().getSelectedItem();
            catalogEditID.setText(Integer.toString(selectedItem.getID()));
            catalogChangeID.setText(Integer.toString(selectedItem.getID()));
            }
        }catch(NullPointerException e){}
        });

        catalogEditID.setOnAction(e -> {
            try {
                 boolean found=false;
                 for(int i=0; i<Catalog.size(); i++){
                     if(Catalog.get(i).getID()== getCatalogEditIDEntry()){
                         found=true;
                        catalogTable.getSelectionModel().select(i);
                        catalogIngredientsTable.setItems(Catalog.get(i).Ingredients);
                        menuView.menuCatalogTable.getSelectionModel().select(i);
                        menuView.menuIngredientsTable.setItems(Catalog.get(i).Ingredients);
                         Catalog.get(i).print();
                         break;
                     }
                 }
                 if(!found) {
                    alert.error("Meal not found in Catalog - "+Integer.toString(getCatalogEditIDEntry())+" - Catalog-idText.setOnAction()");
                    catalogEditID.setText("");
                    catalogChangeID.setText("");
                 }

              } catch(NumberFormatException exe) {
                  exe.printStackTrace();
                            if(catalogEditID.getText()!=null)
                                 alert.error("Input is not a valid integer - catalogEditID.setOnAction");
             }  
        });

        //CatalogChangeID
        catalogChangeID.setOnAction( e -> {
            try {
                if(Integer.parseInt(catalogChangeID.getText()) > 0) {                    
                    boolean found=false;
                    for(int i=0; i<Catalog.size(); i++){
                        if(Catalog.get(i).getID()==Integer.parseInt(catalogChangeID.getText())){
                            found=true;
                            alert.error("Meal already in Catalog - "+catalogChangeID.getText()+" - " +Catalog.get(i).getName()  +"Meal-idText.setOnAction()");
                            catalogChangeID.setText(Integer.toString(getCurrentMeal().getID()));
                            Catalog.get(i).print();
                            break;
                        }
                    }
                    if(!found) {
                        getCurrentMeal().setID(Integer.parseInt(catalogChangeID.getText()));
                        catalogEditID.setText(catalogChangeID.getText());
                       catalogChangeID.setText(catalogChangeID.getText());
                        getCurrentMeal().print();
                        updateCatalog();
                        catalogTable.getSelectionModel().select(getCurrentMeal());
                    }
                } else {
                    catalogChangeID.setText(Integer.toString(getCurrentMeal().getID()));
                    alert.error("Invalid Input - ID must be greater than zero. - catalogChangeID.setOnAction");
                     }
              } catch(NumberFormatException exe) {
                  catalogChangeID.setText(Integer.toString(getCurrentMeal().getID()));
                  exe.printStackTrace();
               alert.error("Input is not a valid integer - catalogChangeID.setOnAction");
             }
           });

           return;
    }  

    
    public Scene catalogScene;


    public Label catalogTitle = new Label("MEAL CATALOG");
    public TableView<Meal> catalogTable = new TableView<Meal>();
    public TextField catalogChangeID = new TextField();
    public Button catalogNewButton = R.makeButton("NEW", 125, false);
    public Button catalogEditButton = R.makeButton("EDIT", 125, false);
    public TextField catalogEditID = new TextField();
    public Button catalogRemoveButton = R.makeButton("REMOVE", 125, false);
    public HBox catalogControls = new HBox(25); //Spacing 
    public TableView<Meal> catalogIngredientsTable = R.makeSimpleReferenceTable();
    public Button testNotify = R.makeButton("Notify", 65, false);
    public Button testError = R.makeButton("Error", 65, false);
    public Button testRequest = R.makeButton("Request", 65, false);
    public HBox testButtons = new HBox(10);
    public VBox catalogIngredientsBox = R.fillReferenceTable("Ingredients", catalogIngredientsTable); 
    public VBox catalogBox = new VBox(10); //Spacing 
    public HBox wholeCatalog = new HBox(50); //spacing
    

    
    public void setCatalog()
    {
    
    
        //Catalog Table:
        catalogTitle.setFont(R.TitleFont);
        catalogTitle.setAlignment(Pos.CENTER);
        catalogChangeID.setPrefWidth(50);  
        catalogChangeID.setStyle( "-fx-alignment: CENTER;"); //center allign
        catalogEditID.setPrefWidth(75);  
        catalogEditID.setStyle( "-fx-alignment: CENTER;"); //center allign

        TableColumn<Meal, Integer> catalogIDColumn = new TableColumn("ID");
        catalogIDColumn.getStyleClass().add("Book Antiqua,15");
        catalogIDColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("ID"));
        catalogIDColumn.setPrefWidth(50);
        catalogIDColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
        TableColumn<Meal, String> catalogNameColumn = new TableColumn("Name");
        catalogNameColumn.getStyleClass().add("Book Antiqua,15");
        catalogNameColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Name"));
        catalogNameColumn.setPrefWidth(200);
        catalogNameColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
        TableColumn<Meal, String> catalogMColumn = new TableColumn("Meat");
        catalogMColumn.getStyleClass().add("Book Antiqua,15");
        catalogMColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Meat"));
        catalogMColumn.setPrefWidth(100);
        catalogMColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
        TableColumn<Meal, String> catalogCColumn = new TableColumn("Carbohydrate");
        catalogCColumn.getStyleClass().add("Book Antiqua,15");
        catalogCColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Carb"));
        catalogCColumn.setPrefWidth(100);
        catalogCColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
        TableColumn<Meal, String> catalogVColumn = new TableColumn("Vegetable");
        catalogVColumn.getStyleClass().add("Book Antiqua,15");
        catalogVColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Vegetable"));
        catalogVColumn.setPrefWidth(100);
        catalogVColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
        TableColumn<Meal, String> catalogFColumn = new TableColumn("Fruit");
        catalogFColumn.getStyleClass().add("Book Antiqua,15");
        catalogFColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Fruit"));
        catalogFColumn.setPrefWidth(100);
        catalogFColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
        TableColumn<Meal, String> catalogBColumn = new TableColumn("NameBrand");
        catalogBColumn.getStyleClass().add("Book Antiqua,15");
        catalogBColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("NameBrand"));
        catalogBColumn.setPrefWidth(100);
        catalogBColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
      //catalogTable.setItems(Catalog);
        catalogTable.getColumns().setAll(catalogIDColumn, catalogNameColumn,catalogMColumn,catalogCColumn,catalogVColumn,catalogFColumn,catalogBColumn);

        //Catalog Assembly VBOX
        catalogControls.getChildren().addAll(catalogChangeID, catalogEditButton, catalogEditID, catalogRemoveButton, catalogNewButton);
        catalogControls.setAlignment(Pos.CENTER);
        catalogBox.setAlignment(Pos.CENTER);
        catalogBox.getChildren().addAll(catalogTitle, catalogTable, catalogControls); 
        testButtons.getChildren().addAll(testRequest, testNotify, testError);
        testButtons.setAlignment(Pos.BOTTOM_CENTER); 
        catalogIngredientsBox.getChildren().add(testButtons);

        wholeCatalog.setAlignment(Pos.TOP_CENTER);
        wholeCatalog.getChildren().addAll(catalogBox,catalogIngredientsBox);


    }


    //Return current Meal that is displayed in catalogEditID text field
    public Meal getCurrentMeal(){
        if(launching)
            return new Meal(false, R.theOrigionalMeal);
        try {
         boolean found=false;
         for(int i=0; i<Catalog.size(); i++){
             if(Catalog.get(i).getID()== getCatalogEditIDEntry()){
                 found=true;
                 return Catalog.get(i);
                }
         }
         if(!found){
                alert.error("***Meal not found in Catalog - "+Integer.toString(getCatalogEditIDEntry())+" - getCurrentMeal()***");
                alert.error("**-> Returning theOrigionalMeal:");
                R.theOrigionalMeal.print();

             return new Meal(false, R.theOrigionalMeal);
         }


      } catch(NumberFormatException exe) {
          exe.printStackTrace();
         alert.error("***Meal not found in Catalog - "+Integer.toString(getCatalogEditIDEntry())+" - getCurrentMeal()***");
         alert.error("Input is not a valid integer - getCurrentMeal()");
         alert.error("***Retrivied from catalogEditID.getText() - "+Integer.toString(getCatalogEditIDEntry())+" - getCurrentMeal()***");

      }  
    return null;
    }

    //Convert editID text to Integer
    public int getCatalogEditIDEntry(){
     int entry = 0;
        try{
            if(Integer.parseInt(catalogEditID.getText()) > 0)                     
                entry = Integer.parseInt(catalogEditID.getText());
            else {
                entry = 0;
                //if(!errorOverride)
                //    alert.error("Input is not a valid integer - catalogEditID is zero or less than - getCatalogEditIDEntry()");   //caught elsewhere unneeded
            }
          } catch(NumberFormatException ex) {
              entry = 0;
         //   if(!errorOverride) 
                alert.error("Input is not a valid integer - NumberFormatException - getCatalogEditIDEntry()");
                alert.error("getCatalogEditIDEntry() - Entry: >"+catalogEditID.getText()+"<");

         }
    return entry;
    }
    
    public void endLaunching() {
        launching = false;
    }
}
