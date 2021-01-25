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
    Save save = new Save();
    boolean launching = true;  //overrides getCurrentMeal() exclusively while loading inports
    static final AlertBox alert = new AlertBox();
    
     
    @Override
    public void start(Stage primaryStage) {
       
        sceneControlsSetup();
        setMeal();
        runMeal();
        setCatalog();
        runCatalog();
        setMenu();
        runMenu();
        setShoppingList();
        runShoppingList();
        set();
        

        
        Scene mealScene = new Scene(mealControlBox);
        Scene catalogScene = new Scene(catalogControlBox);
        Scene menuScene = new Scene(menuControlBox);
        Scene shoppingListScene = new Scene(shoppingListControlBox);
        //Actions in CatalogScene
        catalogSaveAll.setOnAction(e ->{
           save.exportData();
            });
        goToMenuFromCatalog.setOnAction(e -> {
                updateMenu();
                primaryStage.setScene(menuScene);   
            });
        goToShoppingListFromCatalog.setOnAction(e -> {
                primaryStage.setScene(shoppingListScene);   
            });
        catalogNewButton.setOnAction(e ->{
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
                    Catalog.add(1,new Meal(true, theOrigionalMeal));
                }
            catalogEditID.setText("0");  //Going to be "a" Origional Meal either way.
            updateMeal();
            //errorOverride = false;
            primaryStage.setScene(mealScene); 
            });
        catalogEditButton.setOnAction(e ->{
            try {
                boolean found=false;  //checking for 0 Id's; origional Meals
                for(int i=0; i<Catalog.size(); i++){
                    if(Catalog.get(i).getID()== 0){
                        alert.error("Found an Origional Meal in Catalog - must be edited first. - ");
                        found=true;
                        catalogEditID.setText("0");
                        updateMeal();
                        primaryStage.setScene(mealScene);
                        break;            
                    }
                }
                if(!found){  //normal: Edit selected Meal
                    Meal selectedItem = catalogTable.getSelectionModel().getSelectedItem();
                    if(selectedItem != null) {
                        updateMeal();
                        primaryStage.setScene(mealScene); 
                    }
                }
             }catch(NullPointerException exe){}
             });       
        
        //Actions in MealScene
        goToCatalogFromMeal.setOnAction(e -> {
                    updateCatalog();
                    primaryStage.setScene(catalogScene);   
            });
        goToMenuFromMeal.setOnAction(e -> {
                    updateMenu();
                    primaryStage.setScene(menuScene);   
            });
        
           //Actions in MenuScene
           menuSaveAll.setOnAction(e ->{
            save.exportData();
            });
        goToCatalogFromMenu.setOnAction(e -> {
                    updateCatalog();
                    primaryStage.setScene(catalogScene);   
            });
        goToShoppingListFromMenu.setOnAction(e -> {
                    primaryStage.setScene(shoppingListScene);   
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
                    Catalog.add(1,new Meal(true, theOrigionalMeal));
                }
            catalogEditID.setText("0");  //Going to be "a" Origional Meal either way.
            updateMeal();
            //errorOverride = false;
            primaryStage.setScene(mealScene); 
            });
        menuCatalogEditButton.setOnAction(e ->{
            try {
                boolean found=false;  //checking for 0 Id's; origional Meals
                for(int i=0; i<Catalog.size(); i++){
                    if(Catalog.get(i).getID()== 0){
                        alert.error("Found an Origional Meal in Catalog - must be edited first. - ");
                        found=true;
                        catalogEditID.setText("0");
                        updateMeal();
                        primaryStage.setScene(mealScene);
                        break;            
                    }
                }
                if(!found){  //normal: Edit selected Meal
                    Meal selectedItem = menuCatalogTable.getSelectionModel().getSelectedItem();
                    if(selectedItem != null) {
                        updateMeal();
                        primaryStage.setScene(mealScene); 
                    }
                }
             }catch(NullPointerException exe){}
             }); 
        //Actions in ShoppingListScene
        shoppingListSaveAll.setOnAction(e ->{
            save.exportData();
            });
        goToCatalogFromShoppingList.setOnAction(e -> {
                    updateCatalog();
                    primaryStage.setScene(catalogScene);   
            });
        goToMenuFromShoppingList.setOnAction(e -> {
                    updateMenu();
                    primaryStage.setScene(menuScene);   
            });

                
        primaryStage.setTitle("                                - M E A L - P L A N N E R -");
        primaryStage.setScene(menuScene);
        primaryStage.show();
        
        launching = false;       

    }
    
    Button goToCatalogFromMeal = makeButton("CATALOG", 100, false);
    Button goToCatalogFromMenu = makeButton("CATALOG", 100, false);
    Button goToMenuFromCatalog = makeButton("MENU", 100, false);
    Button goToMenuFromMeal = makeButton("MENU", 100, false);
    Button goToCatalogFromShoppingList = makeButton("CATALOG", 100, false);
    Button goToMenuFromShoppingList = makeButton("MENU", 100, false);
    Button goToShoppingListFromCatalog = makeButton("SHOPPING LIST", 200, false);
    Button goToShoppingListFromMenu = makeButton("SHOPPING LIST", 200, false);
    Button catalogSaveAll = makeButton("SAVE ALL", 100, false);
    Button menuSaveAll = makeButton("SAVE ALL", 100, false);
    Button shoppingListSaveAll = makeButton("SAVE ALL", 100, false);
    

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
        Catalog.add(new Meal(true, 11,"mealOne",true,false,false,true));
        Catalog.add(new Meal(true, 22,"mealTwo",true,true,false,false));
        Catalog.add(new Meal(true, 33,"mealThree",true,true,false,false));
        Catalog.add(new Meal(true, 44,"mealFour",false,true,true,false));
        Catalog.add(new Meal(true, 55,"mealFive",true,false,false,false));      
        
        save.exportData();
    }
    else         //Inport Data
        save.inportData();

    checkIngredients();
    updateCatalog();
    updateMeal();
    updateMenu();
    updateShoppingList();

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
    catalogControlBox.getChildren().addAll(wholeCatalog, catalogButtonsBox);
    catalogControlBox.setAlignment(Pos.CENTER); 
    //Meal
    goToCatalogFromMeal.setFont(DetailFont);
    goToCatalogFromMeal.setPrefWidth(100);
    goToMenuFromMeal.setFont(DetailFont);
    goToMenuFromMeal.setPrefWidth(100);
    mealButtonsBox.getChildren().addAll(goToCatalogFromMeal, goToMenuFromMeal);
    mealButtonsBox.setAlignment(Pos.CENTER);
    mealControlBox.getChildren().addAll(wholeMeal, mealButtonsBox);
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
    menuControlBox.getChildren().addAll(wholeMenu, menuButtonsBox);
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
    shoppingListControlBox.getChildren().addAll(wholeShoppingList, shoppingListButtonsBox);
    shoppingListControlBox.setAlignment(Pos.CENTER);    
    
    return;
}

private void checkIngredients() {
    for (int i = 0; i<Catalog.size(); i++) {
        if(!Catalog.get(i).Ingredients.isEmpty())
            return;
    }
    alert.error("No Ingredients were detected in any Catalog Meals - checkIngredients()");
    return;
}


//*****MEAL*****      
private void updateMeal() {
    idText.setText(Integer.toString(getCurrentMeal().getID()));
    nameText.setText(getCurrentMeal().getName());
    if(getCurrentMeal().isMeat())
        meatButton.setText("Meat");
    else
        meatButton.setText("-");
    if(getCurrentMeal().isCarb())
        carbButton.setText("Carbohydrate");
    else
        carbButton.setText("-");
    if(getCurrentMeal().isVegetable())
        vegButton.setText("Vegetable");
    else
        vegButton.setText("-");
    if(getCurrentMeal().isFruit())
        fruitButton.setText("Fruit");
    else
        fruitButton.setText("-");
    
    try {
    //Fill Food Only
    CatalogFoodOnly.clear();
    for(int i = 0; i<Catalog.size(); i++) {
        if(Catalog.get(i).isFood())
            CatalogFoodOnly.add(Catalog.get(i));  //link add
    } 
    //Get Catalog IngredientsID Count
    if(!Catalog.isEmpty())
        idNextIngredient.setText("Next Ingredient ID:   "+Integer.toString(Catalog.get(Catalog.size()-1).getID()+1));
    else
        idNextIngredient.setText("");
    } catch(NullPointerException ex) {
        idNextIngredient.setText("");
        alert.error("NullPointerException - Accessing Catalog Fianl Element - updateMeal().idNextIngredient.setText()");
    }
    
    
    ingredientsEditID.setText("");
    ingredientsTable.setItems(null);
    ingredientsTable.setItems(getCurrentMeal().Ingredients);
    ingredientsTable.refresh();
    catalogViewTable.setItems(null);
    catalogViewTable.setItems(CatalogFoodOnly);
    catalogViewTable.refresh();
    return;
}

//Ingredients
TableView<Meal> ingredientsTable = makeSimpleReferenceTable();
TextField ingredientsAddCustom = new TextField();
Button ingredientsaddButton = makeButton("ADD",75, false);
TextField ingredientsEditID = new TextField();
Button ingredientsremoveButton = makeButton("REMOVE",75, false);
HBox ingredientsControls = new HBox(25); //Spacing 
VBox ingredientsBox = fillReferenceTable("Ingredients", ingredientsTable, ingredientsAddCustom, ingredientsControls); //Spacing 

//CatalogView
TableView<Meal> catalogViewTable = makeDetailedReferenceTable();
VBox catalogViewBox = fillReferenceTable("Food Catalog", catalogViewTable);  

//Meal Members:
  Label idLabel = new Label("ID:");
  Label idNextIngredient = new Label("Next Ingredient: ");
  TextField idText = new TextField();
  Label nameLabel = new Label("Name:");
  TextField nameText = new TextField();        
  Button meatButton = makeButton("-",200,true);
  Button carbButton = makeButton("-",200,true);        
  Button vegButton = makeButton("-",200,true);
  Button fruitButton = makeButton("-",200,true);        

 HBox idBox = new HBox(2);
 HBox nameBox = new HBox(2);
 VBox mealProperties = new VBox(20);
 HBox wholeMeal = new HBox(50);
   
public void setMeal()
{

    //Meal Members:
        idLabel.setFont(TitleFont);
        idNextIngredient.setFont(DetailFont);
        idText.setFont(DetailFont);
        idText.setText(Integer.toString(theOrigionalMeal.getID()));     
        idLabel.setPrefWidth(50);
        idNextIngredient.setPrefWidth(200);
        idNextIngredient.setAlignment(Pos.CENTER_RIGHT);
        idText.setPrefWidth(50);
        nameText.setFont(DetailFont);
        nameLabel.setFont(TitleFont);
        nameText.setText(theOrigionalMeal.getName()); 
        nameLabel.setPrefWidth(100);
        nameText.setPrefWidth(200);

        
        //Meal Assembly VBOX
        ingredientsEditID.setPrefWidth(50);
        ingredientsEditID.setStyle( "-fx-alignment: CENTER;");
        ingredientsAddCustom.setPrefWidth(250);
        ingredientsControls.getChildren().addAll(ingredientsaddButton,ingredientsEditID,ingredientsremoveButton);
        ingredientsControls.setMaxWidth(250);
        ingredientsControls.setAlignment(Pos.TOP_CENTER);
        idBox.getChildren().addAll(idLabel,idText,idNextIngredient);
        idBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.getChildren().addAll(nameLabel,nameText);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        mealProperties.getChildren().addAll(idBox,nameBox,meatButton,carbButton,vegButton,fruitButton);
        mealProperties.setAlignment(Pos.CENTER);
        wholeMeal.getChildren().addAll(mealProperties,ingredientsBox,catalogViewBox);
        wholeMeal.setAlignment(Pos.TOP_CENTER);
               
        //Left Column Meal Properties Inital Set Calls
            meatButton.setText("Meat");
            carbButton.setText("Carb");
            vegButton.setText("Vegetable");
            fruitButton.setText("Fruit");
        return;
}

public void runMeal() {
      ingredientsremoveButton.setOnAction( e -> {     
  try {
   Meal selectedItem = ingredientsTable.getSelectionModel().getSelectedItem();
   ingredientsTable.getItems().remove(selectedItem);
    }catch(NullPointerException exe){}
    });
    
    ingredientsaddButton.setOnAction(e ->{
        if(print)
           System.out.println("Entry is: "+Integer.toString(getMealEditIDEntry()));
        if(getMealEditIDEntry() > 0){
             boolean found=false;
             for(int i=0; i<Catalog.size(); i++){
                 if(Catalog.get(i).getID()==getMealEditIDEntry()){
                     found=true;
                     getCurrentMeal().addIngredient(getMealEditIDEntry());
                     break;
                 }
             }
             if(!found)
                alert.error("Meal not found in Catalog - "+Integer.toString(getMealEditIDEntry())+" - addButton.setOnAction()");
        }
    });
     
   
//Custom Add Ingredients
    ingredientsAddCustom.setOnAction( e -> {  
            getCurrentMeal().Ingredients.add(new Meal(false,00,ingredientsAddCustom.getText(),false,false,false,false));
            ingredientsAddCustom.clear();
            });

//EditID:
    ingredientsTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
    try{
        if (newSelection  !=  null) {
            Meal selectedItem = ingredientsTable.getSelectionModel().getSelectedItem();
            ingredientsEditID.setText(Integer.toString(selectedItem.getID()));
        }
    }catch(NullPointerException e){}
    });
    catalogViewTable.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
    try{
        if (newSelection  !=  null) {
            Meal selectedItem = catalogViewTable.getSelectionModel().getSelectedItem();
            ingredientsEditID.setText(Integer.toString(selectedItem.getID()));
        }
    }catch(NullPointerException e){}
    });
    ingredientsEditID.setOnAction( e -> {
    try{
        if(print)
           System.out.println("ingredientsEditID - entry is: "+Integer.toString(getMealEditIDEntry()));
        if(getMealEditIDEntry() > 0){
             boolean found=false;
             for(int i=0; i<Catalog.size(); i++){
                 if(Catalog.get(i).getID()==getMealEditIDEntry()){
                     found=true;
                     getCurrentMeal().addIngredient(getMealEditIDEntry());
                     break;
                 }
             }
            if(!found) {
                alert.error("Meal not found in Catalog - "+Integer.toString(getMealEditIDEntry())+" - ingredientsEditID.setOnAction()--(adding ingredient)");
                ingredientsEditID.setText("");
            }
        }
    }catch(NullPointerException exe){}
    });
    
               
                
      //Left Column Meal Properties Update Calls
        idText.setOnAction( e -> {
        try {
            if(Integer.parseInt(idText.getText()) > 0) {                    
                boolean found=false;
                for(int i=0; i<Catalog.size(); i++){
                    if(Catalog.get(i).getID()==Integer.parseInt(idText.getText())){
                        found=true;
                        alert.error("Meal already in Catalog - "+idText.getText()+" - " + Catalog.get(i).getName() + " - Meal-idText.setOnAction()");
                        idText.setText(Integer.toString(getCurrentMeal().getID()));
                        Catalog.get(i).print();
                        break;
                    }
                }
                if(! found) {
                   getCurrentMeal().setID(Integer.parseInt(idText.getText()));
                   catalogEditID.setText(idText.getText());
                   catalogChangeID.setText(idText.getText());
                    idText.setText(idText.getText());
                    getCurrentMeal().print();
                }
            } else {
                idText.setText(Integer.toString(getCurrentMeal().getID()));
                     alert.error("Must change ID to save, must be greater than zero.");
                 }
          } catch(NumberFormatException exe) {
              idText.setText(Integer.toString(getCurrentMeal().getID()));
              exe.printStackTrace();
           alert.error("Input is not a valid integer - idText.setOnAction");
         }
       });
        
        nameText.setOnAction( e -> {     
       getCurrentMeal().setName(nameText.getText());
       getCurrentMeal().print();});
       
        meatButton.setOnAction( e -> {     
            if(meatButton.getText()=="Meat")
             { meatButton.setText("-");
                getCurrentMeal().setMeat(false);
             } else{
            meatButton.setText("Meat");
            getCurrentMeal().setMeat(true);
            }getCurrentMeal().print();});
        carbButton.setOnAction( e -> {     
            if(carbButton.getText()=="Carbohydrate")
             { carbButton.setText("-");
                getCurrentMeal().setCarb(false);
             } else{
            carbButton.setText("Carbohydrate");
            getCurrentMeal().setCarb(true);
            }getCurrentMeal().print();});
       vegButton.setOnAction( e -> {     
            if(vegButton.getText()=="Vegetable")
             { vegButton.setText("-");
                getCurrentMeal().setVegetable(false);
             } else{
            vegButton.setText("Vegetable");
            getCurrentMeal().setVegetable(true);
            }getCurrentMeal().print();});
       fruitButton.setOnAction( e -> {     
            if(fruitButton.getText()=="Fruit")
             { fruitButton.setText("-");
                getCurrentMeal().setFruit(false);
             } else{
            fruitButton.setText("Fruit");
            getCurrentMeal().setFruit(true);
            }getCurrentMeal().print();});
       return;
}  

//Convert editID text to Integer
private int getMealEditIDEntry(){
 int entry = 0;
    try {
        if(Integer.parseInt(catalogChangeID.getText()) > 0)                     
               entry = Integer.parseInt(ingredientsEditID.getText());
         else {
            entry = 0;
            alert.error("Input is not a valid integer - getMealEditIDEntry()");
            }
      } catch(NumberFormatException ex) {
          entry = 0;
       alert.error("Input is not a valid integer - getMealEditIDEntry()");
     }
return entry;
}

//*****CATALOG*****   
private void updateCatalog() {
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
//Catalog
public static ObservableList<Meal> Catalog = FXCollections.observableArrayList();
public static ObservableList<Meal> CatalogFoodOnly = FXCollections.observableArrayList();

Label catalogTitle = new Label("MEAL CATALOG");
TableView<Meal> catalogTable = new TableView<Meal>();
TextField catalogChangeID = new TextField();
Button catalogNewButton = makeButton("NEW", 125, false);
Button catalogEditButton = makeButton("EDIT", 125, false);
TextField catalogEditID = new TextField();
Button catalogRemoveButton = makeButton("REMOVE", 125, false);
HBox catalogControls = new HBox(25); //Spacing 
TableView<Meal> catalogIngredientsTable = makeSimpleReferenceTable();
Button testNotify = makeButton("Test Notify", 100, false);
Button testError = makeButton("Test Error", 100, false);
HBox testButtons = new HBox(10);
VBox catalogIngredientsBox = fillReferenceTable("Ingredients", catalogIngredientsTable); 
VBox catalogBox = new VBox(10); //Spacing 
HBox wholeCatalog = new HBox(50); //spacing


             
public void setCatalog()
{
    //Catalog Table:
    catalogTitle.setFont(TitleFont);
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
  //catalogTable.setItems(Catalog);
    catalogTable.getColumns().setAll(catalogIDColumn, catalogNameColumn,catalogMColumn,catalogCColumn,catalogVColumn,catalogFColumn);
         
    //Catalog Assembly VBOX
    catalogControls.getChildren().addAll(catalogChangeID, catalogEditButton, catalogEditID, catalogRemoveButton, catalogNewButton);
    catalogControls.setAlignment(Pos.CENTER);
    catalogBox.setAlignment(Pos.CENTER);
    catalogBox.getChildren().addAll(catalogTitle, catalogTable, catalogControls); 
    testButtons.getChildren().addAll(testNotify,testError);
    testButtons.setAlignment(Pos.BOTTOM_CENTER); 
    catalogIngredientsBox.getChildren().add(testButtons);
    
    wholeCatalog.setAlignment(Pos.TOP_CENTER);
    wholeCatalog.getChildren().addAll(catalogBox,catalogIngredientsBox);
    
     
}

public void runCatalog() {
    testNotify.setOnAction(e -> {new AlertBox().notify("Test Notification");});
    testError.setOnAction(e -> {alert.error("Test Error");});
    
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
        menuCatalogEditID.setText(Integer.toString(selectedItem.getID()));
        menuIngredientsTable.setItems(selectedItem.Ingredients);
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
                    menuCatalogTable.getSelectionModel().select(i);
                    menuIngredientsTable.setItems(Catalog.get(i).Ingredients);
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


    

//Return current Meal that is displayed in catalogEditID text field
Meal theOrigionalMeal = new Meal(true,0,"Origional",true,true,true,true);
private Meal getCurrentMeal(){
    if(launching)
        return new Meal(false, theOrigionalMeal);
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
            theOrigionalMeal.print();

         return new Meal(false, theOrigionalMeal);
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
private int getCatalogEditIDEntry(){
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



//*****MENU*****   
private void updateMenu() {
  
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

//MENU
public static ObservableList<Meal> Menu = FXCollections.observableArrayList();

private void resetMenu() {
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
CheckBox breakfast = new CheckBox("Breakfast");
CheckBox mondayLunch = new CheckBox("Monday Lunch");
CheckBox mondayDinner = new CheckBox("Monday Dinner");
CheckBox tuesdayLunch = new CheckBox("Tuesday Lunch");
CheckBox tuesdayDinner = new CheckBox("Tuesday Dinner");
CheckBox wednessdayLunch = new CheckBox("Wednessday Lunch");
CheckBox wednessdayDinner = new CheckBox("Wednessday Dinner");
CheckBox thursdayLunch = new CheckBox("Thursday Lunch");
CheckBox thursdayDinner = new CheckBox("Thursday Dinner");
CheckBox fridayLunch = new CheckBox("Friday Lunch");
CheckBox fridayDinner = new CheckBox("Friday Dinner");
CheckBox saturdayLunch = new CheckBox("Saturday Lunch");
CheckBox saturdayDinner = new CheckBox("Saturday Dinner");
CheckBox sundayLunch = new CheckBox("Sunday Lunch");
CheckBox sundayDinner = new CheckBox("Sunday Dinner");

Button selectAll = makeButton("ALL", 60, false);
Button deselectAll = makeButton("NONE", 60, false);

HBox selectButtons = new HBox(15);
VBox mealBoxes = new VBox(10);

//Menu Table Set
Label menuTitle = new Label("MENU");
TableView<Meal> menuTable = makeLeftReferenceTable();
TextField menuAddCustom = new TextField();
Button menuResetButton = makeButton("RESET", 80, false);
Button menuClearMealButton = makeButton("CLEAR", 80, false);
Button menuRemoveButton = makeButton("REMOVE", 80, false);
HBox menuControls = new HBox(15); //Spacing 
VBox menuBox = fillReferenceTable("Menu", menuTable, menuAddCustom, menuControls);

//Ingredients Table Set
TableView<Meal> menuIngredientsTable = makeSimpleReferenceTable();
Button menuIngredientsAddButton = makeButton("ADD", 65, false);
TextField menuIngredientsEditID = new TextField();
Button menuIngredientsAddAllButton = makeButton("ADD ALL", 85, false);
HBox menuIngredientsControls = new HBox(25); //Spacing 
VBox menuIngredientsBox = fillReferenceTable("Ingredients", menuIngredientsTable,menuIngredientsControls); 

//Catalog View Set:
TableView<Meal> menuCatalogTable = makeDetailedReferenceTable();
TextField menuCatalogEditID = new TextField();
Button menuCatalogEditButton = makeButton("EDIT", 135, false);
Button menuCatalogNewButton = makeButton("NEW", 135, false);
//HBox menuStats = new HBox(25); //Spacing 
HBox menuCatalogControls = new HBox(25); //Spacing 
VBox menuCatalogViewBox = fillReferenceTable("Catalog", menuCatalogTable, menuCatalogControls);

HBox combineMenuBox = new HBox(30); //Spacing 
VBox wholeMenu = new VBox(15); //spacing


             
public void setMenu()
{
    //Menu Table:
    menuTitle.setFont(TitleFont);
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
}

public void runMenu() {
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
    
    menuRemoveButton.setOnAction( e -> {
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
        catalogTable.getSelectionModel().select(selectedItem);
        catalogEditID.setText(Integer.toString(selectedItem.getID()));
        catalogChangeID.setText(Integer.toString(selectedItem.getID()));
        catalogIngredientsTable.setItems(selectedItem.Ingredients);
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
                 catalogTable.getSelectionModel().select(i);
                 catalogIngredientsTable.setItems(Catalog.get(i).Ingredients);
                 menuCatalogTable.getSelectionModel().select(i);
                 menuIngredientsTable.setItems(Catalog.get(i).Ingredients);
                 Catalog.get(i).print();
                 break;
             }
         }
         if(!found) {
            alert.error("Meal not found in Catalog - "+Integer.toString(getMenuCatalogEditIDEntry())+" - Menu-idText.setOnAction()");
            catalogEditID.setText("");
            catalogChangeID.setText("");
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
private int getMenuEditIDEntry(){
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

private int getMenuCatalogEditIDEntry(){
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

private int getMenuIndex(int ID) {
    for(int i = 0; i<Menu.size(); i++) {
        if(Menu.get(i).getID() == ID)
            return i;
    }
    return 0;  //default first place    
}

private final String indent = "     -";

private void menuAdd(Meal selected, int startID) {
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

private void menuInsert(Meal theMeal, int place, int remainingDepth){
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

private void menuInitiateAdd(Meal selectedItem) {
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

private void menuRemoveMeal(int mealID) {
        try{
            int start = getMenuIndex(mealID);
            int current = start+1;
            while((Menu.get(current).getID() >= 0) && (current < Menu.size()-1)) {
                Menu.remove(current);
            }
         }catch(NullPointerException exe){} 
            return;
}

private void menuInitiateRemoveMeal() {
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
        

//*****SHOPPING LIST*****   
private void updateShoppingList() {
  //Clear Properties
  ShoppingList.clear();
  for(int i =0; i<Menu.size(); i++) {
      Menu.get(i).resetQuantity();
  }
  
  //Fill from Menu
  for(int i =0; i<Menu.size(); i++) {
      if((Menu.get(i).getID() >= 0) && (Menu.get(i).getName().charAt(0) == indent.charAt(0))) {
          boolean found = false;
          for(int j = 0; j<ShoppingList.size(); j++) {
              if((Menu.get(i).getID() != 0) && (Menu.get(i).getID() == ShoppingList.get(j).getID())) {
                  found = true;
                  ShoppingList.get(j).increaseQuantity();
                  break;
              }
              else if((Menu.get(i).getID() == 0) && (Menu.get(i).getName().equals(ShoppingList.get(j).getName()))) {  //for Customs
                  found = true;
                  ShoppingList.get(j).increaseQuantity();
                  break;
              }
            }
          if(!found) { //for new & customs
              Menu.get(i).increaseQuantity();
              ShoppingList.add(new Meal(false,Menu.get(i)));  //meals linked
          }
      }
  }   
  //Removing extra Space
  for(int i =0; i<ShoppingList.size();i++) {
      String newName = ShoppingList.get(i).getName();
      newName = newName.substring(indent.length());
      ShoppingList.get(i).setName(newName);
      
  }
    shoppingListTable.setItems(null);
    shoppingListTable.setItems(ShoppingList);
    shoppingListTable.refresh();
   
    return;
}

private void resetShoppingList()
{
    shoppingListTable.setItems(null);
    shoppingListTable.setItems(ShoppingList);
    shoppingListTable.refresh();
    return;
}
//SHOPPING LIST
public static ObservableList<Meal> ShoppingList = FXCollections.observableArrayList();


//ShoppingList Table Set
TableView<Meal> shoppingListTable = makeQuantityReferenceTable();
TextField shoppingListAddCustom = new TextField();
Button shoppingListUpdateButton = makeButton("UPDATE", 112, false);
Button shoppingListRemoveButton = makeButton("REMOVE", 112, false);
Button increaseQuantity = makeButton("+", 30, false);
Button decreaseQuantity = makeButton("-", 30, false);
HBox quantities = new HBox(10); //spacing
HBox shoppingListControls = new HBox(145); //Spacing 
VBox shoppingListBox = fillReferenceTable("SHOPPING LIST", shoppingListTable, shoppingListAddCustom, shoppingListControls);

VBox wholeShoppingList = new VBox(15); //spacing


             
public void setShoppingList()
{
    //ShoppingList Table:
    shoppingListAddCustom.setPrefWidth(300);
    quantities.setAlignment(Pos.CENTER_LEFT);
    quantities.getChildren().addAll(increaseQuantity, decreaseQuantity);
    shoppingListControls.setAlignment(Pos.CENTER);
    shoppingListControls.getChildren().addAll(quantities, shoppingListUpdateButton, shoppingListRemoveButton);
          
    wholeShoppingList.setAlignment(Pos.TOP_CENTER);
    wholeShoppingList.getChildren().addAll(shoppingListBox, shoppingListControls);
}

public void runShoppingList() {
    increaseQuantity.setOnAction( e -> {
        try {
       Meal selectedItem = shoppingListTable.getSelectionModel().getSelectedItem();
       selectedItem.increaseQuantity();
       resetShoppingList();
       shoppingListTable.getSelectionModel().select(selectedItem);
       }catch(NullPointerException exe){}
    });
    
    decreaseQuantity.setOnAction( e -> {
        try{
       Meal selectedItem = shoppingListTable.getSelectionModel().getSelectedItem();
       selectedItem.decreaseQuantity();
       resetShoppingList();
       shoppingListTable.getSelectionModel().select(selectedItem);
       }catch(NullPointerException exe){}
       });
    
    shoppingListAddCustom.setOnAction( e -> {
        ShoppingList.add(new Meal(false,00,shoppingListAddCustom.getText(),false,false,false,false));
        shoppingListAddCustom.clear();
    });
    
    shoppingListUpdateButton.setOnAction( e -> {
        updateShoppingList();
            });
    
    shoppingListRemoveButton.setOnAction( e -> {
        try{
           Meal selectedItem = shoppingListTable.getSelectionModel().getSelectedItem();
           shoppingListTable.getItems().remove(selectedItem);
        if(menuTable.getSelectionModel().getSelectedIndex() != Menu.size()-1)
           menuTable.getSelectionModel().selectNext();  //next
           }catch(NullPointerException exe){}
    });

    
       return;
}  



//*******************************************************************************

private Button makeButton(String title, int wide, boolean titleFont)
 {
     Button temp = new Button(title);
     temp.setPrefWidth(wide);
     if(titleFont)
         temp.setFont(TitleFont);
     else
         temp.setFont(DetailFont);
     
     return temp;   
 }
 
  //Returns Table VBOX 
 private VBox fillReferenceTable(String title,TableView table)
 {
     Label tempTitle = new Label(title);
     tempTitle.setFont(TitleFont);
     tempTitle.setMaxWidth(250);
     tempTitle.setAlignment(Pos.CENTER);
     
     VBox tempBox = new VBox(10); //Spacing 
     tempBox.setAlignment(Pos.TOP_CENTER);
     tempBox.getChildren().addAll(tempTitle, table);  
     
     return tempBox;
 }
 
  //Returns Table VBOX 
 private VBox fillReferenceTable(String title,TableView table,HBox buttons)
 {
     Label tempTitle = new Label(title);
     tempTitle.setFont(TitleFont);
     tempTitle.setMaxWidth(250);
     tempTitle.setAlignment(Pos.CENTER);
     
     VBox tempBox = new VBox(10); //Spacing 
     tempBox.setAlignment(Pos.TOP_CENTER);
     tempBox.getChildren().addAll(tempTitle, table, buttons);  
     
     return tempBox;
 }
 
   //Returns Table VBOX 
 private VBox fillReferenceTable(String title, TableView table, TextField custom, HBox buttons)
 {
     Label tempTitle = new Label(title);
     tempTitle.setFont(TitleFont);
     tempTitle.setMaxWidth(250);
     tempTitle.setAlignment(Pos.CENTER);
     
     VBox tempBox = new VBox(10); //Spacing 
     tempBox.setAlignment(Pos.TOP_CENTER);
     tempBox.getChildren().addAll(tempTitle, table, custom, buttons);  
     
     return tempBox;
 }
 
    //Returns Table VBOX 
 private VBox fillReferenceTable(String title, TableView table, HBox stats, HBox buttons)
 {
     Label tempTitle = new Label(title);
     tempTitle.setFont(TitleFont);
     tempTitle.setMaxWidth(250);
     tempTitle.setAlignment(Pos.CENTER);
     
     VBox tempBox = new VBox(10); //Spacing 
     tempBox.setAlignment(Pos.TOP_CENTER);
     tempBox.getChildren().addAll(tempTitle, table, stats, buttons);  
     
     return tempBox;
 }
 
 
 //Returns Table, must still setItems & match with title & buttons HBox in a 
 private TableView makeSimpleReferenceTable()
 {
     TableView<Meal> tempTable = new TableView<Meal>();
     TableColumn<Meal, Integer> tempTableIDColumn = new TableColumn("ID");
    tempTableIDColumn.getStyleClass().add("Book Antiqua,15");
    tempTableIDColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("ID"));
    tempTableIDColumn.setPrefWidth(50);
    tempTableIDColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableNameColumn = new TableColumn("Name");
    tempTableNameColumn.getStyleClass().add("Book Antiqua,15");
    tempTableNameColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Name"));
    tempTableNameColumn.setPrefWidth(200);
    tempTableNameColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    tempTable.getColumns().setAll(tempTableIDColumn,tempTableNameColumn);

    return tempTable;
 }
 
 //Returns Table, must still setItems & match with title & buttons HBox in a 
 private TableView makeLeftReferenceTable()
 {
     TableView<Meal> tempTable = new TableView<Meal>();
     TableColumn<Meal, Integer> tempTableIDColumn = new TableColumn("ID");
    tempTableIDColumn.getStyleClass().add("Book Antiqua,15");
    tempTableIDColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("ID"));
    tempTableIDColumn.setPrefWidth(50);
    tempTableIDColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableNameColumn = new TableColumn("Name");
    tempTableNameColumn.getStyleClass().add("Book Antiqua,15");
    tempTableNameColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Name"));
    tempTableNameColumn.setPrefWidth(200);
    tempTable.getColumns().setAll(tempTableIDColumn,tempTableNameColumn);

    return tempTable;
 }
 
  //Returns Table, must still setItems & match with title & buttons HBox in a 
 private TableView makeDetailedReferenceTable()
 {
    TableView<Meal> tempTable = new TableView<Meal>();
    TableColumn<Meal, Integer> tempTableIDColumn = new TableColumn("ID");
    tempTableIDColumn.getStyleClass().add("Book Antiqua,15");
    tempTableIDColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("ID"));
    tempTableIDColumn.setPrefWidth(50);
    tempTableIDColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableNameColumn = new TableColumn("Name");
    tempTableNameColumn.getStyleClass().add("Book Antiqua,15");
    tempTableNameColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Name"));
    tempTableNameColumn.setPrefWidth(200);
    tempTableNameColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableMColumn = new TableColumn("M");
    tempTableMColumn.getStyleClass().add("Book Antiqua,15");
    tempTableMColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Meat"));
    tempTableMColumn.setPrefWidth(30);
    tempTableMColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableCColumn = new TableColumn("C");
    tempTableCColumn.getStyleClass().add("Book Antiqua,15");
    tempTableCColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Carb"));
    tempTableCColumn.setPrefWidth(30);
    tempTableCColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableVColumn = new TableColumn("V");
    tempTableVColumn.getStyleClass().add("Book Antiqua,15");
    tempTableVColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Vegetable"));
    tempTableVColumn.setPrefWidth(30);
    TableColumn<Meal, String> tempTableFColumn = new TableColumn("F");
    tempTableFColumn.getStyleClass().add("Book Antiqua,15");
    tempTableFColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Fruit"));
    tempTableFColumn.setPrefWidth(30);
    //tempTableTable.setItems(CatalogFoodOnly);
    tempTable.getColumns().setAll(tempTableIDColumn, tempTableNameColumn,tempTableMColumn,tempTableCColumn,tempTableVColumn,tempTableFColumn);

    return tempTable;
 }
 
 //Returns Table, must still setItems & match with title & buttons HBox in a 
 private TableView makeQuantityReferenceTable()
 {
    TableView<Meal> tempTable = new TableView<Meal>();
    TableColumn<Meal, Integer> tempTableQuantityColumn = new TableColumn("Quantity");
    tempTableQuantityColumn.getStyleClass().add("Book Antiqua,15");
    tempTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("Quantity"));
    tempTableQuantityColumn.setPrefWidth(75);
    tempTableQuantityColumn.setStyle( "-fx-alignment: CENTER;"); //center allign 
    TableColumn<Meal, Integer> tempTableIDColumn = new TableColumn("ID");
    tempTableIDColumn.getStyleClass().add("Book Antiqua,15");
    tempTableIDColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("ID"));
    tempTableIDColumn.setPrefWidth(75);
    tempTableIDColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableNameColumn = new TableColumn("Name");
    tempTableNameColumn.getStyleClass().add("Book Antiqua,15");
    tempTableNameColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Name"));
    tempTableNameColumn.setPrefWidth(250);
    //tempTableNameColumn.setStyle( "-fx-alignment: LEFT;"); //center allign
    TableColumn<Meal, String> tempTableMColumn = new TableColumn("M");
    tempTableMColumn.getStyleClass().add("Book Antiqua,15");
    tempTableMColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Meat"));
    tempTableMColumn.setPrefWidth(50);
    tempTableMColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableCColumn = new TableColumn("C");
    tempTableCColumn.getStyleClass().add("Book Antiqua,15");
    tempTableCColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Carb"));
    tempTableCColumn.setPrefWidth(50);
    tempTableCColumn.setStyle( "-fx-alignment: CENTER;"); //center allign
    TableColumn<Meal, String> tempTableVColumn = new TableColumn("V");
    tempTableVColumn.getStyleClass().add("Book Antiqua,15");
    tempTableVColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Vegetable"));
    tempTableVColumn.setPrefWidth(50);
    TableColumn<Meal, String> tempTableFColumn = new TableColumn("F");
    tempTableFColumn.getStyleClass().add("Book Antiqua,15");
    tempTableFColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Fruit"));
    tempTableFColumn.setPrefWidth(50);
    //tempTableTable.setItems(CatalogFoodOnly);
    tempTable.getColumns().setAll(tempTableQuantityColumn, tempTableNameColumn, tempTableIDColumn,tempTableMColumn,tempTableCColumn,tempTableVColumn,tempTableFColumn);

    return tempTable;
 }

 
    public static void main(String[] args) {
        launch(args);
    }

    private String toString(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
}
