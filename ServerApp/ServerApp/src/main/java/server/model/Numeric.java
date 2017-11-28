package server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="quesType",discriminatorType=DiscriminatorType.STRING)   
@DiscriminatorValue(value="numeric")         
public class Numeric implements Serializable {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String question;
    private String answer;
    private int marks;
    
    @ManyToOne    
    @JoinColumn(name = "quiz_id") 
    private Quiz quiz;
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }

    
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion(){
        return question;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setMarks(int marks) {
        this.marks = marks;
    }
   
    public int getMarks() {
        return marks;
    }
    
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
   
    public Quiz getQuiz() {
        return quiz;
    }
    
}

