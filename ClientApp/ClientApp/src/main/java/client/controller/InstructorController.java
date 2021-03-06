package client.controller;

import client.bo.QuestionBo;
import client.bo.QuizBo;
import client.model.Mcq;
import client.model.Numeric;
import client.model.Quiz;
import client.model.TrueFalse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

//Controller Class for Instructor
public class InstructorController {
    @FXML    private Label lblStatus;
    @FXML    private Label lblQuesNum;
    @FXML    private Label lblMarks;
    @FXML    private Label lblErrorMake;
    @FXML    private Label lblErrorDone;    
    @FXML    private Label lblErrorMCQ;
    @FXML    private Label lblErrorTrueFalse;
    @FXML    private Label lblErrorNumeric;
    @FXML    private TextArea txtQues1;
    @FXML    private TextField txtUser;
    @FXML    private TextField txtPass;
    @FXML    private TextField quizTitle;
    @FXML    private TextField txtchngIndex;
    @FXML    private TextArea quizDesc;
    @FXML    private TextArea txtQuesMCQ;
    @FXML    private TextArea txtQuesTrueFalse;
    @FXML    private TextArea txtQuesNumeric;
    @FXML    private TextArea txtAnsNumeric;
    @FXML    private TextArea txtAns;
    @FXML    private TextField txtMCQa;
    @FXML    private TextField txtMCQb;
    @FXML    private TextField txtMCQc;
    @FXML    private TextField txtMCQd;
    @FXML    private TextField txtMarks;
    @FXML    private RadioButton radMCQa;
    @FXML    private RadioButton radMCQb;
    @FXML    private RadioButton radMCQc;
    @FXML    private RadioButton radMCQd;
    @FXML    private RadioButton radTrue;
    @FXML    private RadioButton radFalse;
    @FXML    private RadioButton radMCQa1;
    @FXML    private RadioButton radMCQb1;
    @FXML    private RadioButton radMCQc1;
    @FXML    private RadioButton radMCQd1;
    @FXML    private RadioButton radTrue1;
    @FXML    private RadioButton radFalse1;
    @FXML    private Pane makeQuizPane;
    @FXML    private Pane subQuesPane;
    @FXML    private Pane makeMCQPane;
    @FXML    private Pane makeTrueFalsePane;
    @FXML    private Pane makeNumericPane;
    @FXML    private Pane stdSelectPane;
    @FXML    private Pane editOrMakePane;
    @FXML    private Button btnMakeNew;
    @FXML    private Button btnNext;
    @FXML    private Button btnEditQuiz;
    @FXML    private Pane insNumericPane;
    @FXML    private Pane insMCQPane;
    @FXML    private Pane insTrueFalsePane;
    @FXML    private Pane editQuizPane;
    
    QuizBo quizbo = new QuizBo();
    Quiz quiz =  new Quiz();
    
    QuestionBo questionbo = new QuestionBo();
    List<Numeric> questions = new ArrayList<>();
    Numeric quest = null;
    
    public void makeQuizPaneVis(ActionEvent event) throws IOException{    
        makeQuizPane.setVisible(true);
        editOrMakePane.setVisible(false);
        
    }
    
    public void startMakingQuiz(ActionEvent event) throws IOException{
        
        String qTitle = quizTitle.getText();
        String qDesc = quizDesc.getText();
        
        if((qTitle.equals("") || qDesc.equals(""))){
            lblErrorMake.setText("You must give a title and description for quiz");
        }
        else{
            
            quiz = quizbo.addQuiz(qTitle, qDesc);
            
            subQuesPane.setVisible(true);
            makeQuizPane.setVisible(false);
        }
    
    } 
   
    
    public void makeQuiz(ActionEvent event) throws IOException{    
        
        //POST Request to server to create quiz if questions exist
        if(questions != null){
            
            questionbo.saveQuestions();
        
            //closing the current stage window 
            Stage stage = (Stage) txtAns.getScene().getWindow();
            stage.close();
            
            //opening the main stage window
            Parent root = FXMLLoader.load(getClass().getResource("Instructor.fxml"));
            Scene scene = new Scene(root,600,600);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        
    }
    
    public void makeMCQ(ActionEvent event) throws IOException{    
        makeNumericPane.setVisible(false);
        makeTrueFalsePane.setVisible(false);
        makeMCQPane.setVisible(true);
    
    }
    
    public void makeTrueFalse(ActionEvent event) throws IOException{    
        makeMCQPane.setVisible(false);
        makeTrueFalsePane.setVisible(true);
        makeNumericPane.setVisible(false);
      
    }
    
    public void makeNumeric(ActionEvent event) throws IOException{    
        makeMCQPane.setVisible(false);
        makeTrueFalsePane.setVisible(false);
        makeNumericPane.setVisible(true);
      
    }
    
    public void subMCQ(ActionEvent event) throws IOException{    
        
        //retrieving input values
        String qQues = txtQuesMCQ.getText();
        String qAns = "";
        String qMarks = txtMarks.getText();
        String qMCQa = txtMCQa.getText();
        String qMCQb = txtMCQb.getText();
        String qMCQc = txtMCQc.getText();
        String qMCQd = txtMCQd.getText();
        
        if(qMarks.equals("") || qQues.equals("") || qMCQa.equals("") || qMCQb.equals("") || qMCQc.equals("") || qMCQd.equals("")){
            
            if(txtMarks.getText().equals("")){
                lblErrorMCQ.setText("You must enter Max Marks.");
            }
            lblErrorMCQ.setText("You must enter a question and all mcq options.");
        }
        else{
            
            //call function to save current MCQ 
            if(radMCQa.isSelected())
                qAns = txtMCQa.getText();
            
            else if(radMCQb.isSelected())
                qAns = txtMCQb.getText();
            
            else if(radMCQc.isSelected())
                qAns = txtMCQc.getText();
            
            else if(radMCQd.isSelected())
                qAns = txtMCQd.getText();
            
            questionbo.addMCQ(qQues, qAns, Integer.parseInt(qMarks), quiz, qMCQa, qMCQb, qMCQc, qMCQd);
            
            txtQuesMCQ.setText("");
            txtMCQa.setText("");
            txtMCQb.setText("");
            txtMCQc.setText("");
            txtMCQd.setText("");
            txtMarks.setText("");
            lblErrorDone.setText("");
            lblErrorMCQ.setText("");
            lblErrorNumeric.setText("");
            lblErrorTrueFalse.setText("");
            
        }
           
    }
    
    public void subTrueFalse(ActionEvent event) throws IOException{    
        
        //retrieving input values
        String qMarks = txtMarks.getText();
        String qQues = txtQuesTrueFalse.getText();
        String qAns = "";
        String qTrue = "True";
        String qFalse = "False";
        
        if(qQues.equals("") || qMarks.equals("")){
            
            if(qMarks.equals("")){
                lblErrorMCQ.setText("You must enter Max Marks.");
            }
            lblErrorTrueFalse.setText("You must enter a question and mark its correct answer.");
        
        }
        else{
            
            //call function to save current TrueFalse 
            if(radTrue.isSelected())
                qAns = qTrue;
            
            else if(radFalse.isSelected())
                qAns = qFalse;
            
            else
                lblErrorTrueFalse.setText("You must select a correct answer");
            
            
            questionbo.addTrueFalse(qQues, qAns, Integer.parseInt(qMarks), quiz, qTrue, qFalse);
            
            txtQuesTrueFalse.setText("");
            txtMarks.setText("");
            lblErrorDone.setText("");
            lblErrorMCQ.setText("");
            lblErrorNumeric.setText("");
            lblErrorTrueFalse.setText("");
            
        }
    }
    
    public void subNumeric(ActionEvent event) throws IOException{    
        
        String qMarks = txtMarks.getText();
        String qQues = txtQuesNumeric.getText();
        String qAns = txtAnsNumeric.getText();
        
        if(qQues.equals("") || qAns.equals("") || qMarks.equals("")){
            
            if(txtMarks.getText().equals("")){
                lblErrorNumeric.setText("You must enter Max Marks.");
            }
            lblErrorNumeric.setText("You must enter a question and its answer.");
        
        }
        else{
            
            //call function to save numeric question
            questionbo.addNumeric(qQues, qAns, Integer.parseInt(qMarks), quiz);
            
            txtQuesNumeric.setText("");
            txtAnsNumeric.setText("");
            txtMarks.setText("");
            lblErrorDone.setText("");
            lblErrorMCQ.setText("");
            lblErrorNumeric.setText("");
            lblErrorTrueFalse.setText("");
            
        }
    }
    
        
    public void endQuiz() throws IOException{
        
        editOrMakePane.setVisible(true);
        editQuizPane.setVisible(false);
        insMCQPane.setVisible(false);
        insTrueFalsePane.setVisible(false);
        insNumericPane.setVisible(false);
            
        Stage stage = (Stage) txtAns.getScene().getWindow();
        stage.close();
            
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Instructor.fxml"));
        Scene scene = new Scene(root,600,600);
        Stage primaryStage=new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    
    }
}
