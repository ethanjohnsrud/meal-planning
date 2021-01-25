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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static mealplanning.MealPlanning.save;
import static mealplanning.MenuView.Menu;

/**
 *
 * @author ethan
 */
public class ShoppingListView {
    
    ShoppingListView() {
        setShoppingList();
        runShoppingList();
    }
  
    //SHOPPING LIST
    static final ObservableList<Meal> ShoppingList = FXCollections.observableArrayList();
    
    private CatalogView catalogView = MealPlanning.catalogView;
    private MealView mealView = MealPlanning.mealView;
    private MenuView menuView = MealPlanning.menuView;
    private ShoppingListView shoppingListView = MealPlanning.shoppingListView;
    private AlertBox alert = MealPlanning.alert;
    private ObservableList<Meal> Menu = MenuView.Menu;

    
    private Resources R = new Resources();
    
    
//*****SHOPPING LIST*****   
public void updateShoppingList() {
  //Clear Properties
  ShoppingList.clear();
  for(int i =0; i<Menu.size(); i++) {
      Menu.get(i).resetQuantity();
  }
  //adding temporary dividers
  
  //Fill from Menu
  for(int i =0; i<Menu.size(); i++) {
      if((Menu.get(i).getID() >= 0) && (Menu.get(i).getName().charAt(0) == menuView.indent.charAt(0))) {
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
      newName = newName.substring(menuView.indent.length());
      ShoppingList.get(i).setName(newName);
      
  }
    shoppingListTable.setItems(null);
    shoppingListTable.setItems(ShoppingList);
    shoppingListTable.refresh();
   
    return;
}

public void resetShoppingList()
{
    shoppingListTable.setItems(null);
    shoppingListTable.setItems(ShoppingList);
    shoppingListTable.refresh();
    return;
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
        ShoppingList.add(new Meal(false,00,shoppingListAddCustom.getText(),false,false,false,false,false));
        shoppingListAddCustom.clear();
    });
    
    shoppingListUpdateButton.setOnAction( e -> {
        updateShoppingList();
            });
    
    shoppingListRemoveButton.setOnAction( e -> {
        try{
           Meal selectedItem = shoppingListTable.getSelectionModel().getSelectedItem();
           shoppingListTable.getItems().remove(selectedItem);
        if(menuView.menuTable.getSelectionModel().getSelectedIndex() != Menu.size()-1)
           menuView.menuTable.getSelectionModel().selectNext();  //next
           }catch(NullPointerException exe){}
    });

    
       return;
}  

//ShoppingList Table Set
public TableView<Meal> shoppingListTable = R.makeQuantityReferenceTable();
public TextField shoppingListAddCustom = new TextField();
public Button shoppingListUpdateButton = R.makeButton("UPDATE", 112, false);
public Button shoppingListRemoveButton = R.makeButton("REMOVE", 112, false);
public Button increaseQuantity = R.makeButton("+", 30, false);
public Button decreaseQuantity = R.makeButton("-", 30, false);
public HBox quantities = new HBox(10); //spacing
public HBox shoppingListControls = new HBox(145); //Spacing 
public VBox shoppingListBox = R.fillReferenceTable("SHOPPING LIST", shoppingListTable, shoppingListAddCustom, shoppingListControls);
public VBox wholeShoppingList = new VBox(15); //spacing
public Button goToCatalogFromShoppingList = R.makeButton("CATALOG", 100, false);
public Button goToMenuFromShoppingList = R.makeButton("MENU", 100, false);
public Button shoppingListSaveAll = R.makeButton("SAVE ALL", 100, false);

             
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


}
