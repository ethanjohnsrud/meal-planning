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
import javafx.scene.control.ScrollPane;
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
                     this.notify("><File - MealPlanning_Errors_Log - Sucessfully Saved>< - AlertBox.logErrors()");

            } catch(NumberFormatException exe) {
            this.error("***><><***NumberFormatException EXPORT FAILED***><><*** - AlertBox.logErrors()");
            }catch(IOException exe) {
            this.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - AlertBox.logErrors()");
            }
    }
    
    public boolean actionRequest(String message, String suggestedAction) {
        Date date = new Date();
        System.out.println("**Action Request: "+message+"\nRecommeneded Action:  ->  "+suggestedAction+"\n\n\t- "+new Timestamp(date.getTime()));
        return displayAction(message, suggestedAction+"\n\n\t- "+new Timestamp(date.getTime()));
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


    private boolean displayAction(String message, String suggestedAction) {
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

        ignoreButton.setOnAction(e -> {this.logAction(output3.getText(), false); window3.close(); takeAction = false;});
        
        actionButton.setOnAction(e -> {this.logAction(output3.getText(), true); window3.close(); takeAction = true;});
        
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
                     this.notify("><File - MealPlanning_Errors_Log - Sucessfully Saved>< - AlertBox.logAction()");

            } catch(NumberFormatException exe) {
            this.error("***><><***NumberFormatException EXPORT FAILED***><><*** - AlertBox.logAction()");
            }catch(IOException exe) {
            this.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - AlertBox.logAction()");
            }
    }
        

}
