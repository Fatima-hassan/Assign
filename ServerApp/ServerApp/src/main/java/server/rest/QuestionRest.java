package server.rest;

import server.model.Mcq;
import server.model.Numeric;
import server.model.TrueFalse;
import server.service.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionRest {
    
    @Autowired
    QuestionService questionService;

    @RequestMapping(value="/question/show/{id}" , method=RequestMethod.GET , headers="Accept=application/json")
    public @ResponseBody List<Numeric> getQuestionList(@PathVariable("id") int id){
        List<Numeric> questions = questionService.getQuestionList(id);
        
        return questions;
    }
    
    @RequestMapping(value="/question/add/mcq/" , method=RequestMethod.POST)
    public @ResponseBody Mcq add(@RequestBody Mcq question){
        questionService.saveMcq(question);
        
        return question;
    }
    
    @RequestMapping(value="/question/add/truefalse/" , method=RequestMethod.POST)
    public @ResponseBody TrueFalse add(@RequestBody TrueFalse question){
        questionService.saveTrueFalse(question);
        
        return question;
    }
    
    @RequestMapping(value="/question/add/numeric/" , method=RequestMethod.POST)
    public @ResponseBody Numeric add(@RequestBody Numeric question){
        questionService.saveNumeric(question);
        
        return question;
    }

    @RequestMapping(value="/question/add/" , method=RequestMethod.POST)
    public @ResponseBody List<Numeric> add(@RequestBody List<Numeric> quesList){
        questionService.saveList(quesList);
        
        return quesList;
    }
    
}
