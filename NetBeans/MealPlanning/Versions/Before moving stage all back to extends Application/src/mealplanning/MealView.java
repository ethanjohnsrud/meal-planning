/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 *
 * @author ethan
 */
public class MealView {
    
    MealView() {
        setMeal();
        updateMeal();
    }
    
    private Stage primaryStage = MealPlanning.primaryStage;
    private CatalogView catalogView = MealPlanning.catalogView;
    private MealView mealView = MealPlanning.mealView;
    private MenuView menuView = MealPlanning.menuView;
    private ShoppingListView shoppingListView = MealPlanning.shoppingListView;
    private AlertBox alert = MealPlanning.alert;
    private boolean print = MealPlanning.print;

        
    public ObservableList<Meal> Catalog = CatalogView.Catalog;
    public ObservableList<Meal> FoodOnly = CatalogView.CatalogFoodOnly;
    
    static final boolean launching = true;  //overrides catalogView.getCurrentMeal() exclusively while loading inports
    private Resources R = new Resources();
     
    
    
    //*****MEAL*****      
public void updateMeal() {
    idText.setText(Integer.toString(catalogView.getCurrentMeal().getID()));
    nameText.setText(catalogView.getCurrentMeal().getName());
    if(catalogView.getCurrentMeal().isMeat())
        meatButton.setText("Meat");
    else
        meatButton.setText("-");
    if(catalogView.getCurrentMeal().isCarb())
        carbButton.setText("Carbohydrate");
    else
        carbButton.setText("-");
    if(catalogView.getCurrentMeal().isVegetable())
        vegButton.setText("Vegetable");
    else
        vegButton.setText("-");
    if(catalogView.getCurrentMeal().isFruit())
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
    ingredientsTable.setItems(catalogView.getCurrentMeal().Ingredients);
    ingredientsTable.refresh();
    catalogViewTable.setItems(null);
    catalogViewTable.setItems(CatalogFoodOnly);
    catalogViewTable.refresh();
    return;
}

//Ingredients
public TableView<Meal> ingredientsTable = R.makeSimpleReferenceTable();
public TextField ingredientsAddCustom = new TextField();
public Button ingredientsaddButton = R.makeButton("ADD",75, false);
public TextField ingredientsEditID = new TextField();
public Button ingredientsremoveButton = R.makeButton("REMOVE",75, false);
public HBox ingredientsControls = new HBox(25); //Spacing 
public VBox ingredientsBox = R.fillReferenceTable("Ingredients", ingredientsTable, ingredientsAddCustom, ingredientsControls); //Spacing 

//CatalogView
public TableView<Meal> catalogViewTable = R.makeDetailedReferenceTable();
public VBox catalogViewBox = R.fillReferenceTable("Food Catalog", catalogViewTable);  

//Meal Members:
  public Label idLabel = new Label("ID:");
  public Label idNextIngredient = new Label("Next Ingredient: ");
  public TextField idText = new TextField();
  public Label nameLabel = new Label("Name:");
  public TextField nameText = new TextField();        
  public Button meatButton = R.makeButton("-",200,true);
  public Button carbButton = R.makeButton("-",200,true);        
  public Button vegButton = R.makeButton("-",200,true);
  public Button fruitButton = R.makeButton("-",200,true);        

 public HBox idBox = new HBox(2);
 public HBox nameBox = new HBox(2);
 public VBox mealProperties = new VBox(20);
 public HBox wholeMeal = new HBox(50);
 
 public Button goToCatalogFromMeal = R.makeButton("CATALOG", 100, false);
 public Button goToMenuFromMeal = R.makeButton("MENU", 100, false);
 
public HBox mealButtonsBox = new HBox(50);
public VBox mealControlBox = new VBox(30);
 
public Scene mealScene;

   
public void setMeal()
{

    //Meal Members:
        idLabel.setFont(R.TitleFont);
        idNextIngredient.setFont(R.DetailFont);
        idText.setFont(R.DetailFont);
        idText.setText(Integer.toString(R.theOrigionalMeal.getID()));     
        idLabel.setPrefWidth(50);
        idNextIngredient.setPrefWidth(200);
        idNextIngredient.setAlignment(Pos.CENTER_RIGHT);
        idText.setPrefWidth(50);
        nameText.setFont(R.DetailFont);
        nameLabel.setFont(R.TitleFont);
        nameText.setText(R.theOrigionalMeal.getName()); 
        nameLabel.setPrefWidth(100);
        nameText.setPrefWidth(200);

        //Left Column Meal Properties Inital Set Calls
            meatButton.setText("Meat");
            carbButton.setText("Carb");
            vegButton.setText("Vegetable");
            fruitButton.setText("Fruit");
        
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
        
        //Meal
        goToCatalogFromMeal.setFont(R.DetailFont);
        goToCatalogFromMeal.setPrefWidth(100);
        goToMenuFromMeal.setFont(R.DetailFont);
        goToMenuFromMeal.setPrefWidth(100);
        mealButtonsBox.getChildren().addAll(goToCatalogFromMeal, goToMenuFromMeal);
        mealButtonsBox.setAlignment(Pos.CENTER);
        mealControlBox.getChildren().addAll(mealButtonsBox);
        mealControlBox.setAlignment(Pos.CENTER); 
        
        mealScene = new Scene(mealControlBox);
                      
        return;
}

public void runMeal() {
    //Actions in MealScene
        goToCatalogFromMeal.setOnAction(e -> {
                    catalogView.updateCatalog();
                    primaryStage.setScene(catalogView.catalogScene);   
            });
        goToMenuFromMeal.setOnAction(e -> {
                    menuView.updateMenu();
                    primaryStage.setScene(menuView.menuScene);   
            });
        
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
                     catalogView.getCurrentMeal().addIngredient(getMealEditIDEntry());
                     break;
                 }
             }
             if(!found)
                alert.error("Meal not found in Catalog - "+Integer.toString(getMealEditIDEntry())+" - addButton.setOnAction()");
        }
    });
     
   
//Custom Add Ingredients
    ingredientsAddCustom.setOnAction( e -> {  
            catalogView.getCurrentMeal().Ingredients.add(new Meal(false,00,ingredientsAddCustom.getText(),false,false,false,false));
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
                     catalogView.getCurrentMeal().addIngredient(getMealEditIDEntry());
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
                        idText.setText(Integer.toString(catalogView.getCurrentMeal().getID()));
                        Catalog.get(i).print();
                        break;
                    }
                }
                if(! found) {
                   catalogView.getCurrentMeal().setID(Integer.parseInt(idText.getText()));
                   catalogView.catalogEditID.setText(idText.getText());
                   catalogView.catalogChangeID.setText(idText.getText());
                    idText.setText(idText.getText());
                    catalogView.getCurrentMeal().print();
                }
            } else {
                idText.setText(Integer.toString(catalogView.getCurrentMeal().getID()));
                     alert.error("Must change ID to save, must be greater than zero.");
                 }
          } catch(NumberFormatException exe) {
              idText.setText(Integer.toString(catalogView.getCurrentMeal().getID()));
              exe.printStackTrace();
           alert.error("Input is not a valid integer - idText.setOnAction");
         }
       });
        
        nameText.setOnAction( e -> {     
       catalogView.getCurrentMeal().setName(nameText.getText());
       catalogView.getCurrentMeal().print();});
       
        meatButton.setOnAction( e -> {     
            if(meatButton.getText()=="Meat")
             { meatButton.setText("-");
                catalogView.getCurrentMeal().setMeat(false);
             } else{
            meatButton.setText("Meat");
            catalogView.getCurrentMeal().setMeat(true);
            }catalogView.getCurrentMeal().print();});
        carbButton.setOnAction( e -> {     
            if(carbButton.getText()=="Carbohydrate")
             { carbButton.setText("-");
                catalogView.getCurrentMeal().setCarb(false);
             } else{
            carbButton.setText("Carbohydrate");
            catalogView.getCurrentMeal().setCarb(true);
            }catalogView.getCurrentMeal().print();});
       vegButton.setOnAction( e -> {     
            if(vegButton.getText()=="Vegetable")
             { vegButton.setText("-");
                catalogView.getCurrentMeal().setVegetable(false);
             } else{
            vegButton.setText("Vegetable");
            catalogView.getCurrentMeal().setVegetable(true);
            }catalogView.getCurrentMeal().print();});
       fruitButton.setOnAction( e -> {     
            if(fruitButton.getText()=="Fruit")
             { fruitButton.setText("-");
                catalogView.getCurrentMeal().setFruit(false);
             } else{
            fruitButton.setText("Fruit");
            catalogView.getCurrentMeal().setFruit(true);
            }catalogView.getCurrentMeal().print();});
       return;
}  

//Convert editID text to Integer
public  int getMealEditIDEntry(){
 int entry = 0;
    try {
        if(Integer.parseInt(catalogView.catalogChangeID.getText()) > 0)                     
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
}
