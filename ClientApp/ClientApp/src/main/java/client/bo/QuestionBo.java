package client.bo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import client.model.Mcq;
import client.model.Numeric;
import client.model.Quiz;
import client.model.TrueFalse;
import java.io.IOException;


public class QuestionBo {
    
    public static final String REST_SERVICE_URI = "http://localhost:8080/server"; 
    RestTemplate restTemplate = new RestTemplate();
    List<Numeric> quesList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    
    /* GET */
    public List<Numeric> getQuestions(int id) throws IOException{
        
        ResponseEntity<List<Numeric>> res;
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(REST_SERVICE_URI+"/question/show/"+id, Object[].class);
        Object[] objects = responseEntity.getBody();
        
        List<Numeric> questions = new ArrayList<>();
        for(int i=0; i<objects.length; i++){
            String questionInJson  = mapper.writeValueAsString(objects[i]);
            if(questionInJson.contains("optionA") && questionInJson.contains("optionB") && questionInJson.contains("optionC") && questionInJson.contains("optionD")){
                Mcq ques = mapper.readValue(questionInJson, Mcq.class);
                questions.add(ques);
            }
            else if(questionInJson.contains("optionTrue") && questionInJson.contains("optionFalse")){
                TrueFalse ques = mapper.readValue(questionInJson, TrueFalse.class);
                questions.add(ques);
            }
            else{
                Numeric ques = mapper.readValue(questionInJson, Numeric.class);
                questions.add(ques);
            }
            
        }
        
        return questions;
    }
    
    public void addNumeric(String quest, String answer, int marks, Quiz quiz) {
        
        Numeric question = new Numeric();
        question.setQuestion(quest);
        question.setAnswer(answer);
        question.setMarks(marks);
        question.setQuiz(quiz);
        
        quesList.add(question);
    }
    
    public void addTrueFalse(String quest, String answer, int marks, Quiz quiz, String optTrue, String optFalse) {
        
        TrueFalse question = new TrueFalse();
        question.setQuestion(quest);
        question.setAnswer(answer);
        question.setMarks(marks);
        question.setQuiz(quiz);
        question.setOptionFalse(optFalse);
        question.setOptionTrue(optTrue);
        
        quesList.add(question);
    }
    
    public void addMCQ(String quest, String answer, int marks, Quiz quiz, String optA, String optB, String optC, String optD) {
        
        Mcq question = new Mcq();
        question.setQuestion(quest);
        question.setAnswer(answer);
        question.setMarks(marks);
        question.setQuiz(quiz);
        question.setOptionA(optA);
        question.setOptionB(optB);
        question.setOptionC(optC);
        question.setOptionD(optD);
        
        quesList.add(question);
    }
    
    public void saveQuestions() {
         
        quesList.forEach((Numeric question) -> {
            
            if(question instanceof TrueFalse)
                restTemplate.postForObject( REST_SERVICE_URI+"/question/add/truefalse/" , (TrueFalse)question , TrueFalse.class);
             
            else if (question instanceof Mcq)
                restTemplate.postForObject( REST_SERVICE_URI+"/question/add/mcq/" , (Mcq)question , Mcq.class);
           
            else 
                restTemplate.postForObject( REST_SERVICE_URI+"/question/add/numeric/" , question , Numeric.class);
            
        });
        
        
    }
    
}
