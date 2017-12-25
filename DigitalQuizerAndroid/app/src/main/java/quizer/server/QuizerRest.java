package quizer.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import quizer.enitity.Mcq;
import quizer.enitity.Numeric;
import quizer.enitity.Quiz;
import quizer.enitity.TrueFalse;
import quizer.enitity.User;

public class QuizerRest {

    RestTemplate restTemplate = new RestTemplate();
    List<Numeric> quesList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    List<Quiz> quizes = new ArrayList<>();

    public User getUser(String username, String password){

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        user = restTemplate.postForObject("http://10.0.2.2:8080/server/user/login/" , user , User.class);

        return user;
    }

    public List<Quiz> getQuizes(){

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Quiz[]> quizes = restTemplate.getForEntity( "http://10.0.2.2:8080/server/quiz/show/" , Quiz[].class);
        return Arrays.asList(quizes.getBody());
    }

    public Quiz addQuiz(String title, String description) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Quiz quiz_with_id = restTemplate.postForObject( "http://10.0.2.2:8080/server/quiz/add/" , quiz , Quiz.class);

        quizes.add(quiz_with_id);
        return quiz_with_id;
    }

    public List<Numeric> getQuestions(int id) throws IOException {

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<List<Numeric>> res;
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity("http://10.0.2.2:8080/server/question/show/"+id, Object[].class);
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
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        for(int i=0;i<quesList.size();i++){
            Numeric question = quesList.get(i);

            if(question instanceof TrueFalse)
                restTemplate.postForObject( "http://10.0.2.2:8080/server/question/add/truefalse/" , (TrueFalse)question , TrueFalse.class);

            else if (question instanceof Mcq)
                restTemplate.postForObject( "http://10.0.2.2:8080/server/question/add/mcq/" , (Mcq)question , Mcq.class);

            else
                restTemplate.postForObject( "http://10.0.2.2:8080/server/question/add/numeric/" , question , Numeric.class);
        }

    }

}
