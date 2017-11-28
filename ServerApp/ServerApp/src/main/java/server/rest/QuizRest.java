package server.rest;

import server.model.Quiz;
import server.service.QuizService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuizRest {
    
    @Autowired
    QuizService quizService;

    @RequestMapping(value="/quiz/show/" , method=RequestMethod.GET , headers="Accept=application/json")
    public @ResponseBody List<Quiz> getQuizList(){
        List<Quiz> quizes = quizService.getQuizList();
        
        return quizes;
    }

    @RequestMapping(value="/quiz/add/" , method=RequestMethod.POST)
    public @ResponseBody Quiz add(@RequestBody Quiz quiz){
        
        return quizService.saveQuiz(quiz);
        
    }
        
}
