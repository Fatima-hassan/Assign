package server.service;

import server.dao.QuizDao;
import server.model.Quiz;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuizService {

    QuizDao quizDao;
    
    @Autowired
    public void setQuizDao(QuizDao quizDao){
        this.quizDao=quizDao;
    }
    
    public List<Quiz> getQuizList() {
        return quizDao.getQuizList();
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizDao.saveQuiz(quiz);
    }

    public Quiz findQuizById(int id) {
        return quizDao.findQuizById(id);
    }
    
}
