package server.dao;

import server.model.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }


    public void saveOrUpdate(User user) {
        getSession().saveOrUpdate(user);
    }

    public User findUser(User user) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.like("username", user.getUserName()));
        criteria.add(Restrictions.like("password", user.getPassword()));
        return (User) criteria.uniqueResult();
    }
    
    
}
