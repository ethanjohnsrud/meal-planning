package MealPlanning;


import java.util.List;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author ethan
 */
public class Resources {
    
    public final Meal theOrigionalMeal = new Meal(true,0,"Origional",0, 0, true,true,true,true,false,false,false, false); 
    public final Meal theBlankMeal = new Meal(0,"");  

    
    public final Font TitleFont = new Font("Book Antiqua", 25);
    public final Font DetailFont = new Font("Book Antiqua", 13);
    public final String buttonColor = "-fx-base:  #b22222;";
    public final String panelColor = "GOLDENROD";
    

   
    
    public String catalogIDRequirements(List<Meal> Catalog, int specialLast, int mealsFirst, int mealsLast) {
        try{
            int mealsNext = mealsFirst;
            for(int j=0; j<Catalog.size(); j++) {
                if(Catalog.get(j).getID() == mealsNext && Catalog.get(j).getID()>=mealsFirst && Catalog.get(j).getID()<=mealsLast)
                    mealsNext++;
            }
        return  "Catalog ID Requirements:\n"
                + "     "+"1-"+specialLast+" = Special\n"
                + "     "+mealsFirst +"-"+mealsLast+" = Meals  ->  Next: " + mealsNext + "\n"
                + "     "+Integer.toString(mealsLast+1) +"+ = Ingredients  ->  Next: "+Integer.toString(Catalog.get(Catalog.size()-1).getID()+1);
    } catch(NullPointerException exe) {
        new AlertBox().notify("Catalog may be empty, returniing null - Resources.catalogIDRequirements()");
        return null;
    }
    }
    
public Integer random(int low, int high) { //includes bounds
    Random rand = new Random();

    if(low<high)
        return rand.nextInt(Math.abs(high-low+1)) + low;
    else if(high<low)
        return rand.nextInt(Math.abs(low-high+1)) + high;
    else //(low==high)
        return low;
}
public Button makeButton(String title, int wide, boolean titleFont)
 {
     Button temp = new Button(title);
     temp.setPrefWidth(wide);
//     temp.setStyle(buttonColor);
     if(titleFont)
         temp.setFont(TitleFont);
     else
         temp.setFont(DetailFont);
     
     return temp;   
 }

public String fillSpace(int space, String soFar, String c) {
        int count = space - soFar.length();
        String extra = "";
        while (count > 0) {
            extra = extra + c;
            count--;
        }
        return extra;
    }

public String updateIndicators(Meal current, Meal theMeal) {
    String status = "";
    if(current.isMeat() || theMeal.isMeat()) {
        current.setMeat(true);
        status = status + "M-";
    }else
        status = status + "---";
    if(current.isCarb() || theMeal.isCarb()) {
        current.setCarb(true);
        status = status + "C-";
    }else
        status = status + "--";
    if(current.isVegetable() || theMeal.isVegetable()) {
        current.setVegetable(true);
        status = status + "V-";
    }else
        status = status + "---";
    if(current.isFruit() || theMeal.isFruit()) {
        current.setFruit(true);
        status = status + "F";
    }else
        status = status + "--";
      
    return status;    
}

public String resetIndicators(Meal current) {
    String status = "----------";
    current.setMeat(false);
    current.setCarb(false);
    current.setVegetable(false);
    current.setFruit(false);
      
    return status;  
}

public HBox getIndicatorsBox(Label mealIndicators, CheckBox mealCheck) {
    HBox theBox = new HBox(5);
    mealIndicators.setFont(new Font("MONOSPACED",13));
    mealIndicators.setPrefWidth(65);
    mealIndicators.setAlignment(Pos.CENTER);
    mealCheck.setAlignment(Pos.CENTER_LEFT);
    theBox.setAlignment(Pos.CENTER_LEFT);
    theBox.getChildren().addAll(mealIndicators, mealCheck);
    theBox.setPrefWidth(200);
    
    return theBox;
}
 
public int getFirstIDPlace(List<Meal> theList, int num) {
    for(int i =0; i<theList.size();i++){
        if(theList.get(i).getID() == num)
            return i;
    }
    return 0; 
    }

  //Returns Table VBOX 
 public VBox fillReferenceTable(String title,TableView table)
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
 public VBox fillReferenceTable(String title,TableView table,HBox buttons)
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
 public VBox fillReferenceTable(String title, TableView table, TextField custom, HBox buttons)
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
 public VBox fillReferenceTable(TableView table, TextField custom, HBox buttons)
 {
    
     VBox tempBox = new VBox(10); //Spacing 
     tempBox.setAlignment(Pos.TOP_CENTER);
     table.setMaxHeight(300);
     tempBox.getChildren().addAll(table, custom, buttons);  
     
     return tempBox;
 }
 
    //Returns Table VBOX 
 public VBox fillReferenceTable(String title, TableView table, HBox stats, HBox buttons)
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
 public TableView makeSimpleReferenceTable()
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
    tempTable.setMaxWidth(250);

    return tempTable;
 }
 
 //Returns Table, must still setItems & match with title & buttons HBox in a 
 public TableView makeLeftReferenceTable()
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
    tempTable.setMaxWidth(250);


    return tempTable;
 }
 
  //Returns Table, must still setItems & match with title & buttons HBox in a 
 public TableView makeDetailedReferenceTable()
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
    tempTable.setMaxWidth(370);

    return tempTable;
 }
 
 //Returns Table, must still setItems & match with title & buttons HBox in a 
 public TableView makeServingsReferenceTable()
 {
    TableView<Meal> tempTable = new TableView<Meal>();
    TableColumn<Meal, Integer> tempTableServingsColumn = new TableColumn("Servings");
    tempTableServingsColumn.getStyleClass().add("Book Antiqua,15");
    tempTableServingsColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("Servings"));
    tempTableServingsColumn.setPrefWidth(75);
    tempTableServingsColumn.setStyle( "-fx-alignment: CENTER;"); //center allign 
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
    TableColumn<Meal, String> tempTableBColumn = new TableColumn("NB");
    tempTableBColumn.getStyleClass().add("Book Antiqua,15");
    tempTableBColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("NameBrand"));
    tempTableBColumn.setPrefWidth(50);
    //tempTableTable.setItems(CatalogFoodOnly);
    tempTable.getColumns().setAll(tempTableServingsColumn, tempTableNameColumn, tempTableIDColumn,tempTableMColumn,tempTableCColumn,tempTableVColumn,tempTableFColumn,tempTableBColumn);

    return tempTable;
 }
 
}

