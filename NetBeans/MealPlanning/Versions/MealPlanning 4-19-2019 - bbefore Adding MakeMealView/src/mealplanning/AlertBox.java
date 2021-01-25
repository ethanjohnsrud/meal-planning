/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
//import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ethan
 */
public class AlertBox {
    
    AlertBox() {
                window2.initModality(Modality.APPLICATION_MODAL);
                //window.alwaysOnTopProperty();
                window3.initModality(Modality.APPLICATION_MODAL);
                window4.initModality(Modality.APPLICATION_MODAL);
                window.initModality(Modality.APPLICATION_MODAL);
    }
    
    ObservableList<CheckBox> errorsList = FXCollections.observableArrayList();
    ListView<CheckBox> listCollections = new ListView<CheckBox>();
    private final Font TitleFont = new Font("Book Antiqua", 25);
    private final Font DetailFont = new Font("Book Antiqua", 13);
    
    public void error(String message) {
        Date date = new Date();
        System.out.println(message+" - "+new Timestamp(date.getTime()));
        errorsList.add(errorsList.size(), makeError(message+" - "+new Timestamp(date.getTime())));
        window.close();
        displayError();
    }
        Stage window = new Stage();
        Label title = new Label();
        //Button unlockButton = new Button("UNLOCK");
        Button saveButton = new Button("Save to Log");
        Button dismissButton = new Button("Dismiss");
        Label notesTitle = new Label();
        TextField notes = new TextField();
        VBox layout = new VBox(15);
        HBox buttons = new HBox(30);
        Scene scene = new Scene(layout);
        ListView<CheckBox> listView = new ListView<CheckBox>();


    private void displayError() {
        window.setTitle("---E-R-R-O-R---M-E-S-S-A-G-E---");
        window.setMinWidth(500);
        
        listView.setPrefWidth(500);
        listView.setPrefHeight(250);
        listView.setItems(errorsList);
        
        title.setText("---E-R-R-O-R---M-E-S-S-A-G-E---\n\n");
        title.setAlignment(Pos.CENTER);
        notesTitle.setText("Notes");
        notes.setPrefHeight(30);


        //unlockButton.setOnAction(e -> {window.close(); window.show();layout.getChildren().remove(unlockButton);});

        saveButton.setOnAction(e -> {window.close(); this.logErrors(); errorsList.clear();});

        dismissButton.setOnAction(e -> {window.close(); errorsList.clear();});
        
        buttons.getChildren().clear();
        buttons.getChildren().addAll(saveButton, dismissButton);
        buttons.setAlignment(Pos.CENTER);


        layout.getChildren().clear();
        layout.getChildren().addAll(title,listView,notesTitle, notes, buttons);
        
        window.setHeight(400);
        layout.setAlignment(Pos.CENTER);

        window.setAlwaysOnTop(true);
        window.setScene(scene);
        window.show();
    }

    private CheckBox makeError(String message){
        CheckBox click = new CheckBox(message);
        click.setOnAction(e -> {errorsList.remove(errorsList.indexOf(click)); this.displayError();});
        return click;
    }
    
    
    public void notify(String message) {
        Date date = new Date();
        System.out.println(message+" - "+new Timestamp(date.getTime()));
        displayNotification(message+" - "+new Timestamp(date.getTime()));
    }
        Stage window2 = new Stage();
        Label title2 = new Label();
        Label output = new Label();
        Button closeButton = new Button("CLOSE");
        VBox layout2 = new VBox(30);
        Scene scene2 = new Scene(layout2);


    private void displayNotification(String message) {
        window2.setTitle("---N-O-T-I-F--I-C-A-T--I-O-N---M-E-S-S-A-G-E---");
        window2.setMinWidth(500);
        //window.alwaysOnTopProperty();
        
        title2.setText("---N-O-T-I-F--I-C-A-T--I-O-N---M-E-S-S-A-G-E---");
        title2.setAlignment(Pos.CENTER);
        output.setText(message);
        output.setAlignment(Pos.CENTER);

        closeButton.setOnAction(e -> {window2.close();});
        
        layout2.getChildren().clear();
        layout2.getChildren().addAll(title2,output, closeButton);
        window2.setHeight(200);
        window2.setWidth(700);
        layout2.setAlignment(Pos.CENTER);

        window2.setAlwaysOnTop(true);
        window2.setScene(scene2);
        window2.showAndWait();
    }
    
    
    
    private String errorFile = "MealPlanning_Errors_Log.txt";
    
    private void logErrors() {
            try {
                FileWriter writer = new FileWriter(errorFile, true);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

               // print.println("\t----------E-R-R-O-R-S---R-E-P-O-R-T----------\n");
                Date date = new Date();
                print.println("Log Reported: "+new Timestamp(date.getTime()));
                if(!notes.getText().equals("")) {
                    print.println("Notes:");
                    print.println("     " +notes.getText());
                }
                for(int i = 0; i<errorsList.size(); i++) {
                     print.println(errorsList.get(i).getText());
                    }  
                print.println();
                print.println();
                notes.setText("");
                     print.flush();
                     print.close();
//                     this.notify("><File - MealPlanning_Errors_Log - Sucessfully Saved>< - AlertBox.logErrors()");

            } catch(NumberFormatException exe) {
            this.error("***><><***NumberFormatException EXPORT FAILED***><><*** - AlertBox.logErrors()");
            }catch(IOException exe) {
            this.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - AlertBox.logErrors()");
            }
    }
    
    public boolean actionRequest(boolean logResult, String message, String suggestedAction) {
        Date date = new Date();
        System.out.println("**Action Request: "+message+"\nRecommeneded Action:  ->  "+suggestedAction+"\n\n\t- "+new Timestamp(date.getTime()));
        return displayAction(logResult, message, suggestedAction+"\n\n\t- "+new Timestamp(date.getTime()));
    }

        Stage window3 = new Stage();
        Label title3 = new Label();
        Label output3 = new Label();
        Label output35 = new Label();
        Label notesTitle3 = new Label();
        TextField notes3 = new TextField();
        Button ignoreButton = new Button("IGNORE");
        Button actionButton = new Button("TAKE ACTION");
        HBox buttonsBox3 = new HBox(50);
        VBox layout3 = new VBox(15);
        Scene scene3 = new Scene(layout3);
        private boolean takeAction;


    private boolean displayAction(boolean logResult, String message, String suggestedAction) {
        window3.setTitle("---A-C-T-I-O-N---N-E-E-D-E-D---M-E-S-S-A-G-E---");
        window3.setMinWidth(500);
        //window.alwaysOnTopProperty();
        
        title3.setText("---A-C-T-I-O-N---N-E-E-D-E-D---M-E-S-S-A-G-E---");
        title3.setAlignment(Pos.CENTER);
        output3.setText(message);
        //output3.setPrefHeight(300);
        output3.setAlignment(Pos.CENTER);
        output35.setText("Recommended Action:   ->   "+suggestedAction);
        //output35.setPrefHeight(150);
        output35.setAlignment(Pos.CENTER);
        notesTitle3.setText("Notes");
        notes3.setPrefHeight(30);

        ignoreButton.setOnAction(e -> {
            if(logResult)
                this.logAction(output3.getText(), false); 
            window3.close(); 
            takeAction = false;});
        
        actionButton.setOnAction(e -> {
            if(logResult)
                this.logAction(output3.getText(), true); 
            window3.close(); 
            takeAction = true;});
        buttonsBox3.getChildren().clear();
        buttonsBox3.getChildren().addAll(actionButton, ignoreButton);
        buttonsBox3.setAlignment(Pos.BOTTOM_CENTER);
        layout3.getChildren().clear();
        layout3.getChildren().addAll(title3,output3,output35,notesTitle3,notes3,buttonsBox3);
        window3.setMaxHeight(1000);
        window3.setWidth(400);
        layout3.setAlignment(Pos.CENTER);

        window3.setAlwaysOnTop(true);
        window3.setScene(scene3);
        window3.showAndWait();
        
        return takeAction;
    }
    
    public boolean actionConfirmation(String message) {
        Date date = new Date();
        System.out.println("**Action Request: "+message+"\n\t- "+new Timestamp(date.getTime()));
        return displayConfirmationn(message+"\n\t- "+new Timestamp(date.getTime()));
    }

        Stage window4 = new Stage();
        Label title4 = new Label();
        Label output4 = new Label();
        Button cancelButton = new Button("CANCEL");
        Button confirmButton = new Button("CONFIRM");
        HBox buttonsBox4 = new HBox(50);
        VBox layout4 = new VBox(15);
        Scene scene4 = new Scene(layout4);
        private boolean confirmAction;


    private boolean displayConfirmationn(String message) {
        window4.setTitle("---A-C-T-I-O-N---C-O-N-F-I-R-M-A-T-I-O-N---M-E-S-S-A-G-E---");
        window4.setMinWidth(500);
        //window.alwaysOnTopProperty();
        
        title4.setText("---A-C-T-I-O-N---C-O-N-F-I-R-M-A-T-I-O-N---M-E-S-S-A-G-E---");
        title4.setAlignment(Pos.CENTER);
        output4.setText(message);
        //output4.setPrefHeight(400);
        output4.setAlignment(Pos.CENTER);
        //output45.setPrefHeight(150);

        cancelButton.setOnAction(e -> {window4.close(); confirmAction = false;});
        
        confirmButton.setOnAction(e -> {window4.close(); confirmAction = true;});
        
        buttonsBox4.getChildren().addAll(cancelButton, confirmButton);
        buttonsBox4.setAlignment(Pos.BOTTOM_CENTER);
        layout4.getChildren().clear();
        layout4.getChildren().addAll(title4,output4,buttonsBox4);
        window4.setMaxHeight(250);
        window4.setWidth(400);
        layout4.setAlignment(Pos.CENTER);

        window4.setAlwaysOnTop(true);
        window4.setScene(scene4);
        window4.showAndWait();
        
        return confirmAction;
    }
         
    private void logAction(String message, boolean actionTaken) {
            try {
                FileWriter writer = new FileWriter(errorFile, true);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

               // print.println("\t----------E-R-R-O-R-S---R-E-P-O-R-T----------\n");
                Date date = new Date();
                print.println("Log Reported: "+new Timestamp(date.getTime()));
                if(!notes.getText().equals("")) {
                    print.println("Notes:");
                    print.println("     " +notes3.getText());
                }
                print.println("Message:");
                print.println("     " +message);
                if(actionTaken)
                    print.println("***Action Taken***");
                else
                    print.println("***Action Ignored");
                print.println();
                print.println();
                notes3.setText("");
                     print.flush();
                     print.close();
//                     this.notify("><File - MealPlanning_Errors_Log - Sucessfully Saved>< - AlertBox.logAction()");

            } catch(NumberFormatException exe) {
            this.error("***><><***NumberFormatException EXPORT FAILED***><><*** - AlertBox.logAction()");
            }catch(IOException exe) {
            this.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - AlertBox.logAction()");
            }
    }
        
    
        Stage window5 = new Stage();
    Label title5 = new Label();
    Label label5 = new Label("String: ");
    TextField text5 = new TextField();
    Button enterButton5 = makeButton("ENTER");
    GridPane grid5 = new GridPane();
    VBox layout5 = new VBox(15);
    Scene scene5 = new Scene(layout5); 
    String value5;
    
    public String getString(String defaultValue, String prompt){  
        System.out.println("String Needed: "+prompt);
        this.logAction("String Needed: "+prompt, true);
        value5 = defaultValue;
        text5.setText(defaultValue);
        window5.setTitle("---S-T-R-I-N-G---N-E-E-D-E-D---");
        window5.setMinWidth(300);
        //window.alwaysOnTopProperty();
        
        title5.setText(prompt);
        title5.setAlignment(Pos.CENTER);
        title5.setFont(DetailFont);
        text5.setFont(DetailFont);
        
        grid5.getChildren().clear();
        grid5.setAlignment(Pos.CENTER);
        grid5.getChildren().clear();
        grid5.add(label5, 0,0);
        grid5.add(text5,1,0);
        
        enterButton5.setFont(DetailFont);
        
        enterButton5.setOnAction(e -> {
            value5 = text5.getText();
            window5.close();        
        });
        layout5.getChildren().clear();
        layout5.getChildren().addAll(title5,grid5,enterButton5);
        layout5.setAlignment(Pos.CENTER);    
        window5.setAlwaysOnTop(true);
        window5.setScene(scene5);
        window5.showAndWait();   
        System.out.println("Return Value: "+value5);
            this.logAction("Return Value: "+value5, true);
        return value5;
    }
        
    Stage window6 = new Stage();
    Label title6 = new Label();
    Label label6 = new Label("Integer: ");
    TextField text6 = new TextField();
    Button enterButton6 = makeButton("ENTER");
    GridPane grid6 = new GridPane();
    VBox layout6 = new VBox(16);
    Scene scene6 = new Scene(layout6); 
    Integer value6;
    
    public Integer getInteger(Integer defaultValue, String prompt){ 
        System.out.println("String Needed: "+prompt);
        this.logAction("String Needed: "+prompt, true);
        value6 = defaultValue;
        text6.setText(Integer.toString(defaultValue));
        window6.setTitle("---I-N-T-E-G-E-R---N-E-E-D-E-D---");
        window6.setMinWidth(300);
        //window.alwaysOnTopProperty();
        
        title6.setText(prompt);
        title6.setAlignment(Pos.CENTER);
        title6.setFont(DetailFont);
        text6.setFont(DetailFont);
        
        grid6.getChildren().clear();
        grid6.setAlignment(Pos.CENTER);
        grid6.getChildren().clear();
        grid6.add(label6, 0,0);
        grid6.add(text6,1,0);
        
        enterButton6.setFont(DetailFont);
        
        enterButton6.setOnAction(e -> {
            try{
            value6 = Integer.parseInt(text6.getText());
            System.out.println("Return Value: "+text6.getText());
            this.logAction("Return Value: "+text6.getText(), true);
            } catch(NumberFormatException ex) {
               this.error("<><> - NumberFormatException ->"+text6.getText()+"<- Input is not a valid Integer - AlertBox.getInteger()");
            }
            window6.close();        
        });
        layout6.getChildren().clear();
        layout6.getChildren().addAll(title6,grid6,enterButton6);
        layout6.setAlignment(Pos.CENTER);    
        window6.setAlwaysOnTop(true);
        window6.setScene(scene6);
        window6.showAndWait();   
        return value6;
    }

        Stage window7 = new Stage();
    Label title7 = new Label();
    Label label7 = new Label("Double: ");
    TextField text7 = new TextField();
    Button enterButton7 = makeButton("ENTER");
    GridPane grid7 = new GridPane();
    VBox layout7 = new VBox(17);
    Scene scene7 = new Scene(layout7); 
    Double value7;

public Double getDouble(Double defaultValue, String prompt){  
        System.out.println("Double Needed: "+prompt);
        this.logAction("Double Needed: "+prompt, true);
        value7 = defaultValue;
        text7.setText(Double.toString(defaultValue));
        window7.setTitle("---D-O-U-B-L-E---N-E-E-D-E-D---");
        window7.setMinWidth(300);
        //window.alwaysOnTopProperty();
        
        title7.setText(prompt);
        title7.setAlignment(Pos.CENTER);
        title7.setFont(DetailFont);
        text7.setFont(DetailFont);
        
        grid7.getChildren().clear();
        grid7.setAlignment(Pos.CENTER);
        grid7.getChildren().clear();
        grid7.add(label7, 0,0);
        grid7.add(text7,1,0);
        
        enterButton7.setFont(DetailFont);
        
        enterButton7.setOnAction(e -> {
            try{
            value7 = Double.parseDouble(text7.getText());
            System.out.println("Return Value: "+text7.getText());
            this.logAction("Return Value: "+text7.getText(), true);
            } catch(NumberFormatException ex) {
               this.error("<><> - NumberFormatException ->"+text7.getText()+"<- Input is not a valid Double - AlertBox.getDouble()");
            }
            window7.close();        
        });
        layout7.getChildren().clear();
        layout7.getChildren().addAll(title7,grid7,enterButton7);
        layout7.setAlignment(Pos.CENTER);    
        window7.setAlwaysOnTop(true);
        window7.setScene(scene7);
        window7.showAndWait();   
        return value7;
    }

        Stage window8 = new Stage();
        Label title8 = new Label();
        Button falseButton = makeButton("FALSE");
        Button trueButton = makeButton("TRUE");
        HBox buttonsBox8 = new HBox(50);
        VBox layout8 = new VBox(15);
        Scene scene8 = new Scene(layout8);
        boolean value8;


    public boolean getBoolean(Boolean defaultValue, String prompt) {
        System.out.println("Boolean Needed: "+prompt);
        this.logAction("Boolean Needed: "+prompt, true);
        value8 = defaultValue;
        window8.setTitle("---B-O-O-L-E-A-N---N-E-E-D-E-D---");
        window8.setMinWidth(300);
        title8.setText(prompt);
        title8.setAlignment(Pos.CENTER);

        falseButton.setOnAction(e -> {value8 = false; System.out.println("Return Value: FALSE"); this.logAction("Return Value: FALSE", true); window8.close();});
        
        trueButton.setOnAction(e -> {value8 = true; System.out.println("Return Value: TRUE"); this.logAction("Return Value: TRUE", true); window8.close();});
        
        buttonsBox8.getChildren().addAll(trueButton, falseButton);
        buttonsBox8.setAlignment(Pos.BOTTOM_CENTER);
        layout8.getChildren().clear();
        layout8.getChildren().addAll(title8,buttonsBox8);
        layout8.setAlignment(Pos.CENTER);
        window8.setAlwaysOnTop(true);
        window8.setScene(scene8);
        window8.showAndWait();    
        return value8;
    }
    
        
        private ObservableList<Button> selectionList = FXCollections.observableArrayList();
    private ListView<Button> selectionView = new ListView<Button>();
       
    private Stage window12 = new Stage();
    private Label title12 = new Label();
    private Button errorButton12 = new Button("ERROR");
    private VBox layout12 = new VBox(10);
    private Scene scene12 = new Scene(layout12); 
    private Integer value12;

public Integer getSelection(List<String> L, String prompt){  //returns place in List not Index, so first is 1 , 0 is ERROR
        System.out.println("List Selection Needed: "+prompt);
        this.logAction("List Selection Needed: "+prompt, true);
        value12 = 0;
        if(L.isEmpty()){
            this.error("NULL POINTER EXCEPTION - Empty List Passed In, Returning Zero - AlertBox.getSelection()");
            return 0; //ERROR
        }
        //FIll List
        selectionList.clear();
        for(int i = 0; i<L.size();i++){
           selectionList.add(this.makeSelection((i+1)+" - "+L.get(i)));
        }
        selectionView.setItems(selectionList);       
        title12.setText("  "+prompt+"  ");
        title12.setAlignment(Pos.CENTER);
        title12.setFont(TitleFont);
        
        errorButton12.setOnAction(e -> {
            value12 = 0;
            window12.close();
        });
        layout12.getChildren().clear();
        layout12.getChildren().addAll(title12,selectionView,errorButton12);
        layout12.setAlignment(Pos.CENTER);
        window12.setTitle("---S-E-L-E-C--T-I--O-N---N-E-E-D-E-D---");
        window12.setMinWidth(150);
        window12.setMaxWidth(700);
        window12.setMinHeight(150);
        window12.setMaxHeight(700);
        window12.setAlwaysOnTop(true);
        window12.setScene(scene12);
        window12.showAndWait();   
        return value12;
    }

private Button makeSelection(String message){
    Button click = this.makeButton(message);
    click.setAlignment(Pos.CENTER_LEFT);
    click.setMaxWidth(Double.MAX_VALUE);
    click.setOnAction(e -> {
        value12 = selectionList.indexOf(click)+1;
        window12.close();
    });
    return click;
    }

    private Button makeButton(String title)
    {
     Button temp = new Button(title);
     temp.setMinWidth(100);
//     temp.setStyle(buttonColor);
         temp.setFont(DetailFont);
     return temp;   
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
}