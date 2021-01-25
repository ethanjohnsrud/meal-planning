/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static mealplanning.CatalogView.Catalog;
import static mealplanning.MenuView.Menu;
import static mealplanning.MenuView.MenuRecent;
import static mealplanning.MenuView.MenusList;
import static mealplanning.ShoppingListView.ShoppingList;
import javafx.collections.ObservableList;
import static mealplanning.MealPlanning.alert;


/**
 *
 * @author ethan
 */
public class Save {
    
    private AlertBox alert = MealPlanning.alert;
    private String catalogSaveFile = "Meals.csv";
    private ObservableList<Meal> Catalog = CatalogView.Catalog;
//    private String menuSaveFile = "Menu.csv";
//    private String menuPrintFile = "Menu.txt";
    private ObservableList<Meal> Menu = MenuView.Menu;
    private String recentSaveFile = "Recent.csv";
    private ObservableList<Meal> Recent = MenuView.MenuRecent;
    private String menusListSaveFile = "MenusList.csv";
    private ObservableList<String> MenusList = MenuView.MenusList;
    private String shoppingListPrintFile = "ShoppingList.txt";
    private ObservableList<Meal> ShoppingList = ShoppingListView.ShoppingList;
    private boolean sampleFill = MealPlanning.sampleFill;
    private CatalogView catalogView = MealPlanning.catalogView;

    private Resources R = new Resources();

    
    public int importData() {
        int mealsLastID = 19; //origionally
        if(!sampleFill) {
            mealsLastID = catalogImport(false);
//            menuImport("Menu");
            recentImport(false);
            menusListImport(false);
           new AlertBox().notify("><File - Total Load Completed>< - Save.importData()");
        }
        return mealsLastID;
}
    
    public void exportData(String currentMenu, int mealsLastID) {
        catalogExport(false, mealsLastID);
        menuExport(false, currentMenu);
        recentExport(false);
        shoppingListExport(false);
        new AlertBox().notify("><File - Total Save Completed>< - Save.importData()");
//        emailFiles();
        return;
    }
    
    public int catalogImport(boolean prompt) {
        int mealsLastID = 29; //originally
    try {
        File file = new File(catalogSaveFile);
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        int line = 0;
        
        Catalog.clear();

    try {
        mealsLastID = Integer.parseInt(inputStream.next());
        while(inputStream.hasNext()) { 
           lastRead = inputStream.next();
            int id = Integer.parseInt(lastRead);  
            String name = inputStream.next();  
            Boolean meat = Boolean.parseBoolean(inputStream.next());  
            Boolean carb = Boolean.parseBoolean(inputStream.next()); 
            Boolean vegetable = Boolean.parseBoolean(inputStream.next()); 
            Boolean fruit = Boolean.parseBoolean(inputStream.next()); 
            Boolean brand = Boolean.parseBoolean(inputStream.next());
            Boolean pack = Boolean.parseBoolean(inputStream.next()); 
            Boolean base = Boolean.parseBoolean(inputStream.next());
            lastRead=inputStream.next(); //reading in "|"


            Meal newMeal = new Meal(true,id,name,meat,carb,vegetable,fruit,brand,pack,base);

            while(inputStream.hasNext() && (!((lastRead=inputStream.next()).equals("\n")))) {
                int ingredientID = Integer.parseInt(lastRead); 
                String ingredientName = inputStream.next();  
                newMeal.temporaryIngredients.add(new Meal(false,ingredientID,ingredientName, false, false, false, false,false,false,false));
                lastRead=inputStream.next(); //reading in "|"
            }
            Catalog.add(newMeal); 
            line++;            
        }

        inputStream.close();

        //link Ingredients
        for (int i =0; i<Catalog.size(); i++) {
            if(!Catalog.get(i).temporaryIngredients.isEmpty()) {
                Catalog.get(i).addIngredient();
            }
        }
        
        if(prompt)
         new AlertBox().notify("><File - Catalog - Sucessfully Loaded>< - Save.catalogImport()");
        return mealsLastID;
    } catch(NumberFormatException exe) {
    alert.error("***><><***NumberFormatException - Meal skipped ***><><*** - Save.catalogImport()");
    alert.error("Line: "+line);
    alert.error("LastRead: >"+lastRead+"<");
    String skipped = inputStream.nextLine(); line++;
    alert.error("Skipped Line: "+ skipped);
    }
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.catalogImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.catalogImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.catalogImport()");               
    }
    return mealsLastID;
    }

    public boolean catalogExport(boolean prompt, int mealsLastID) {
            try {
                FileWriter writer = new FileWriter(catalogSaveFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);                    
                print.print(Integer.toString(mealsLastID)+",");
                
                for(int i = 0; i<Catalog.size(); i++) {
                     print.print(Integer.toString(Catalog.get(i).getID())+","); 
                     print.print(Catalog.get(i).getName()+",");
                     print.print(Boolean.toString(Catalog.get(i).isMeat())+",");
                     print.print(Boolean.toString(Catalog.get(i).isCarb())+",");
                     print.print(Boolean.toString(Catalog.get(i).isVegetable())+",");
                     print.print(Boolean.toString(Catalog.get(i).isFruit())+",");
                     print.print(Boolean.toString(Catalog.get(i).isNameBrand())+",");
                     print.print(Boolean.toString(Catalog.get(i).isPackable())+",");
                     print.print(Boolean.toString(Catalog.get(i).isCarb())+",|,");
                     if(!Catalog.get(i).Ingredients.isEmpty()) {
                        Catalog.get(i).Ingredients.forEach(j -> {
                            print.print(Integer.toString(j.getID())+",");
                            print.print(j.getName()+",|,");                     

                        });                 
                     }
                     print.print("\n,");

                }  
                     print.flush();
                     print.close();
                     if(prompt)
                        new AlertBox().notify("><File - Catalog - Sucessfully Saved>< - Save.catalogExport()");

                    return true;
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.catalogExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.catalogExport()");
            }
            return false;
    }
    
    public boolean menuImport(boolean prompt, String theMenu) {
        System.out.println("MENUS IS:"+theMenu);
    try {
        File file = new File(theMenu+".csv");
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        String mealNames[] = {"Breakfast", "Monday Lunch", "Monday Dinner", "Tuesday Lunch", "Tuesday Dinner", "Wednessday Lunch", "Wednessday Dinner", "Thursday Lunch", "Thursday Dinner", "Friday Lunch", "Friday Dinner", "Saturday Lunch", "Saturday Dinner", "Sunday Lunch", "Sunday Dinner", "*************", "ERROR_OverFlow_1x", "ERROR_OverFlow_2x", "ERROR_OverFlow_3x"};
        int currentMealIndex = 0;
        int line = 0;
        
        Menu.clear();
        
    while(inputStream.hasNext()) { 
        try{
            inputStream.useDelimiter(",");
           lastRead = inputStream.next();
            int id = Integer.parseInt(lastRead);  
            String name = inputStream.next();  
            Boolean meat = Boolean.parseBoolean(inputStream.next());  
            Boolean carb = Boolean.parseBoolean(inputStream.next()); 
            Boolean vegetable = Boolean.parseBoolean(inputStream.next()); 
            Boolean fruit = Boolean.parseBoolean(inputStream.next()); 
            Boolean brand = Boolean.parseBoolean(inputStream.next()); 
            Boolean pack = Boolean.parseBoolean(inputStream.next()); 
            Boolean base = Boolean.parseBoolean(inputStream.next()); 
            lastRead=inputStream.next(); //reading in "|"
            lastRead=inputStream.next(); //reading in "\n|"

            Meal newMeal;
            
            if(id < 0) {
                newMeal = new Meal(false,((currentMealIndex+1)*-1),"*****"+mealNames[currentMealIndex]+"*****",false,false,false,false,false,false,false);
                currentMealIndex++;               
            }
            else
                newMeal = new Meal(false,id,name,meat,carb,vegetable,fruit,brand,pack,base);

            Menu.add(newMeal);
            line++;
            
            
        } catch(NumberFormatException exe) {
        alert.error("***><><***NumberFormatException - Meal skipped ***><><***- Save.menuImport()");
        alert.error("Line: "+line);
        alert.error("LastRead: >"+lastRead+"<");
        String skipped = inputStream.nextLine(); line++;
        alert.error("Skipped Line: "+ skipped);
         }
    }

    inputStream.close();
    if(prompt)
        new AlertBox().notify("><File - Menu - "+theMenu+" - Sucessfully Loaded>< - Save.menuImport()");

    return true;
    
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.menuImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.menuImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.menuImport()");               
    }
    return false;
    }

    public boolean menuExport(boolean prompt, String theMenu) {
            try {
                //Write saving file Menu.csv
                FileWriter writer = new FileWriter(theMenu+".csv", false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

                for(int i = 0; i<Menu.size(); i++) {

                     print.print(Integer.toString(Menu.get(i).getID())+","); //new start w/,
                     print.print(Menu.get(i).getName()+",");
                     print.print(Boolean.toString(Menu.get(i).isMeat())+",");
                     print.print(Boolean.toString(Menu.get(i).isCarb())+",");
                     print.print(Boolean.toString(Menu.get(i).isVegetable())+",");
                     print.print(Boolean.toString(Menu.get(i).isFruit())+",");
                     print.print(Boolean.toString(Menu.get(i).isNameBrand())+",");
                     print.print(Boolean.toString(Menu.get(i).isPackable())+",");
                     print.print(Boolean.toString(Menu.get(i).isCarb())+",|,");
                     
                     print.print("\n,");

                }  
                     print.flush();
                     print.close();
                
                //write printing file Menu.txt
                FileWriter writer2 = new FileWriter(theMenu+".txt", false);
                BufferedWriter buffer2 = new BufferedWriter(writer2);
                PrintWriter print2 = new PrintWriter(buffer2);
               
                print2.println("\t----------M-E-N-U----------");
                print2.println();
                print2.println("ID:\t\tNAME:");
                for(int i = 0; i<Menu.size(); i++) {
                    if(Menu.get(i).getID() < 0)
                        print2.println();
                    else
                        print2.print(Integer.toString(Menu.get(i).getID())+"\t");                              
                    print2.print(Menu.get(i).getName());
                    print2.println();

                }  
                     print2.flush();
                     print2.close();
                     if(prompt)
                        new AlertBox().notify("><File - Menu - "+theMenu+" - Sucessfully Saved>< - Save.menuExport()");
                     return true;
                     
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><***- Menu -");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><***- Menu -");
            }
            return false;
    }
    public boolean shoppingListExport(boolean prompt) {
            try {
                FileWriter writer = new FileWriter(shoppingListPrintFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);

                print.println("\t----------S-H-O-P-P-I-N-G---L-I-S-T----------");
                print.println();
                print.println("QUANTITY:\tNAME:\t\tID:");
                for(int i = 0; i<ShoppingList.size(); i++) {
                     print.print(ShoppingList.get(i).getServings()+"\t\t");
                     print.print(ShoppingList.get(i).getName()+"\t\t");
                     print.print(Integer.toString(ShoppingList.get(i).getID()));
                     print.println();
                }  
                     print.flush();
                     print.close();
                     if(prompt)
                        new AlertBox().notify("><File - ShoppingList - Sucessfully Saved>< - Save.shoppingListExport()");

                     return true;
                     
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.shoppingListExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.shoppingListExport()");
            }
            return false;
    }
    
    public boolean recentImport(boolean prompt) {
    try {
        File file = new File(recentSaveFile);
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        int line = 0;
        
        Recent.clear();

    while(inputStream.hasNext()) { //not eof
        try{
            lastRead = inputStream.next();
            int id = Integer.parseInt(lastRead);  
            String name = inputStream.next();
            inputStream.next();  //  |  = next element
            Meal newMeal;
            
            newMeal = new Meal(false,id,name,false,false,false,false,false,false,false);

            Recent.add(newMeal);
            line++;

        } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException - Meal skipped ***><><*** - Save.recentImport()");
            alert.error("Line: "+line);
            alert.error("LastRead: >"+lastRead+"<");
            String skipped = inputStream.nextLine(); line++;
            alert.error("Skipped Line: "+ skipped);
        }
    }

    inputStream.close();
    if(prompt)
        new AlertBox().notify("><File - RecentList - Sucessfully Loaded>< - Save.recentImport()");

    return true;
    
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.recentImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.recentImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.recentImport()");               
    }
    return false;
    }
    
    public boolean recentExport(boolean prompt) {
            try {
                FileWriter writer = new FileWriter(recentSaveFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);                    
  
                for(int i = 0; i<Recent.size(); i++) {
                    print.print(Integer.toString(Recent.get(i).getID())+",");                              
                    print.print(Recent.get(i).getName()+",\n,");

                }  
                print.flush();
                print.close(); 
                if(prompt)
                    new AlertBox().notify("><File - RecentList - Sucessfully Saved>< - Save.recentExport()");
                return true;    
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.recentExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.recentExport()");
            }
            return false;
    }
    
    
    public boolean menusListImport(boolean prompt) {
    try {
        File file = new File(menusListSaveFile);
        Scanner inputStream = new Scanner(file);
        inputStream.useDelimiter(",");
        String lastRead = null;
        int line = 0;
        
        MenusList.clear();

    while(inputStream.hasNext()) { //not eof
        try{
            MenusList.add(inputStream.next());
            inputStream.next();  //  \n  = next element
            
            line++;

        } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException - Meal skipped ***><><*** - Save.menusListImport()");
            alert.error("Line: "+line);
            alert.error("LastRead: >"+lastRead+"<");
            String skipped = inputStream.nextLine(); line++;
            alert.error("Skipped Line: "+ skipped);
        }
    }

    inputStream.close();
    if(prompt)
        new AlertBox().notify("><File - MenusList - Sucessfully Loaded>< - Save.menusListImport()");
    return true;
    
    } catch(FileNotFoundException ex) {
    alert.error("***><><***FILE NOT FOUND INPORT FAILED***><><*** - Save.menusListImport()");
    } catch(NullPointerException ee) {
        alert.error("***><><***NULL POINTER INPORT FAILED***><><*** - Save.menusListImport()");
    } catch(NoSuchElementException e) {
    alert.error("***><><***NumberFormatException INPORT FAILED***><><*** - Save.menusListImport()");               
    }
    return false;
    }
    
    public boolean menusListExport(boolean prompt) {
            try {
                FileWriter writer = new FileWriter(menusListSaveFile, false);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);                    
  
                for(int i = 0; i<MenusList.size(); i++) {
                    print.print(MenusList.get(i)+",\n,");
                }  
                print.flush();
                print.close(); 
                if(prompt)
                     new AlertBox().notify("><File - MenusList - Sucessfully Saved>< - Save.recentExport()");
                return true;    
            } catch(NumberFormatException exe) {
            alert.error("***><><***NumberFormatException EXPORT FAILED***><><*** - Save.menusListExport()");
            }catch(IOException exe) {
            alert.error("***><><***IOEXCEPTION EXPORT FAILED***><><*** - Save.menusListExport()");
            }
            return false;
    }
    
    public void emailFiles() {
//      // Recipient's email ID needs to be mentioned.
//      String to = "ethanjohnsrud@gmail.com";
//
//      // Sender's email ID needs to be mentioned
//      String from = "ethanjohnsrud@gmail.com";
//
//      final String username = "ethanjohnsrud";//change accordingly
//      final String password = "Fd$82018";//change accordingly
//
//      // Assuming you are sending email through relay.jangosmtp.net
//      String host = "relay.jangosmtp.net";
//
//      Properties props = new Properties();
//      props.put("mail.smtp.auth", "true");
//      props.put("mail.smtp.starttls.enable", "true");
//      props.put("mail.smtp.host", host);
//      props.put("mail.smtp.port", "25");
//
//      // Get the Session object.
//      Session session = Session.getInstance(props,
//         new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//               return new PasswordAuthentication(username, password);
//            }
//         });
//
//      try {
//         // Create a default MimeMessage object.
//         javax.mail.Message message = new MimeMessage(session);
//
//         // Set From: header field of the header.
//         message.setFrom(new InternetAddress(from));
//
//         // Set To: header field of the header.
//         message.setRecipients(javax.mail.Message.RecipientType.TO,
//            InternetAddress.parse(to));
//
//         // Set Subject: header field
//         Date date = new Date();
//         message.setSubject("MealPlanning"+" - "+new Timestamp(date.getTime()));
//
//         // Create the message part
//         BodyPart messageBodyPart = new MimeBodyPart();
//
//         // Now set the actual message
//         messageBodyPart.setText("Files!");
//
//         // Create a multipar message
//         Multipart multipart = new MimeMultipart();
//
//         // Set text message part
//         multipart.addBodyPart(messageBodyPart);
//
//         // Part two is attachment
////         messageBodyPart = new MimeBodyPart();
////         String filename = "/home/manisha/file.txt";
////         DataSource source = new FileDataSource(filename);
////         messageBodyPart.setDataHandler(new DataHandler(source));
////         messageBodyPart.setFileName(filename);
////         multipart.addBodyPart(messageBodyPart);
//
//         // Send the complete message parts
//         message.setContent(multipart);
//
//         // Send message
//         Transport.send(message);
//
//         new AlertBox().notify("Email of Files Successfully Sent to: "+to);
//  
//      } catch (javax.mail.MessagingException e) {
//         throw new RuntimeException(e);
//      }
   }
  
    
}