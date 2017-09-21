/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author OK
 */
public class main {
    @FXML
    private Label txtStatus;
    
      @FXML
    private TextField  userName;
      
        @FXML
    private TextField Password;
        @FXML
        private AnchorPane pane1;
        @FXML private AnchorPane pane2;
        @FXML private AnchorPane pane7;
       

@FXML private AnchorPane pane3;
@FXML private AnchorPane pane4;
@FXML private AnchorPane pane5;
@FXML private AnchorPane pane6;
  public void Login(ActionEvent event) throws Exception
  {
      if(userName.getText().equals("user") && Password.getText().equals("pass"))
  
      {
          txtStatus.setText("Login Sucess");
          Stage primaryStage= new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("/quiz/main.fxml"));
          
        
        
        
        Scene scene = new Scene(root,600,600);        
        primaryStage.setScene(scene);
        primaryStage.show();
        
       
      }
      else
      {
           txtStatus.setText("Login failed");
      }
  }
  
    public void Button_click(ActionEvent next_button) throws Exception
  {
      pane1.setVisible(true);
      pane2.setVisible(true);
  }
    
    public void mcqClick(ActionEvent next_button) throws Exception
  {pane2.setVisible(false);
      pane3.setVisible(true);
      pane4.setVisible(false);
      pane5.setVisible(false);
      
      
  }
    
     public void truefalseClick(ActionEvent next_button) throws Exception
  { pane2.setVisible(false);
      pane3.setVisible(false);
      pane4.setVisible(true);
      pane5.setVisible(false);
      
          
      
  }
     
      public void numericClick(ActionEvent next_button) throws Exception
  {  pane2.setVisible(false);
      pane3.setVisible(false);
      pane4.setVisible(false);
      pane5.setVisible(true);
      pane6.setVisible(true);
      pane6.setVisible(true);
      pane7.setVisible(true);
         
      
      
      
  }
      
    
      
    
    
}
