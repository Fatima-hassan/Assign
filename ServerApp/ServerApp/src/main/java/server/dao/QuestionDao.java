package server.dao;

import server.model.Mcq;
import server.model.Numeric;
import server.model.Quiz;
import server.model.TrueFalse;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<Numeric> getQuestionList(int id) {
        
        Criteria cQuiz = sessionFactory.getCurrentSession().createCriteria(Quiz.class);
        cQuiz.add(Restrictions.like("id", id));
        Quiz quiz = (Quiz) cQuiz.uniqueResult();
        
        Criteria cQuest = sessionFactory.getCurrentSession().createCriteria(Numeric.class);
        cQuest.add(Restrictions.like("quiz", quiz));
        
        List<Numeric> dcQuest = (List<Numeric>) cQuest.list();
        
        return (List<Numeric>) cQuest.list();
    
    }

    public void saveList(List<Numeric> questions){
        
        questions.forEach((Numeric question) -> {
            if(question instanceof TrueFalse)
                getSession().save((TrueFalse)question);
            
            else if(question instanceof Mcq)
                getSession().save((Mcq)question);
            
            else
                getSession().save(question);
        });
       
    }
        
    public void saveMcq(Mcq question) {
        getSession().save(question);
    }
    
    public void saveTrueFalse(TrueFalse question) {
        getSession().save(question);
    }
    
    public void saveNumeric(Numeric question) {
        getSession().save(question);
    }
    
}
