/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
                window.initModality(Modality.APPLICATION_MODAL);
    }
    
    ObservableList<CheckBox> errorsList = FXCollections.observableArrayList();
    ListView<CheckBox> listCollections = new ListView<CheckBox>();
    
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
        notes.setPrefHeight(20);

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
        window.close();
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
                     this.notify("><File - MealPlanning_Errors_Log - Sucessfully Saved>< - AlertBox.logErrors()");

            } catch(NumberFormatException exe) {
            this.error("***><><***NumberFormatException EXPORT FAILED***><><*** - AlertBox.logErrors()");
            }catch(IOException exe) {
            this.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - AlertBox.logErrors()");
            }
    }
        

}
