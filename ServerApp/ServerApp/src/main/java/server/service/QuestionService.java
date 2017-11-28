package server.service;

import server.dao.QuestionDao;
import server.model.Mcq;
import server.model.Numeric;
import server.model.TrueFalse;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuestionService {

    QuestionDao questionDao;
    
    @Autowired
    public void setQuestionDao(QuestionDao questionDao){
        this.questionDao=questionDao;
    }
    
    public List<Numeric> getQuestionList(int id) {
        return questionDao.getQuestionList(id);
    }

    public void saveList(List<Numeric> questions){
        questionDao.saveList(questions);
    }
   
    public void saveMcq(Mcq question) {
        questionDao.saveMcq(question);
    }
    
    public void saveTrueFalse(TrueFalse question) {
        questionDao.saveTrueFalse(question);
    }
    
    public void saveNumeric(Numeric question) {
        questionDao.saveNumeric(question);
    }
        
}
