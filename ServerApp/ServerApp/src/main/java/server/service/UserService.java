package server.service;

import server.dao.UserDao;
import server.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    UserDao userDao;
    
    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao=userDao;
    }
    
    public void saveOrUpdate(User user) {
        userDao.saveOrUpdate(user);
    }

    public User findUser(User user) {
        return userDao.findUser(user);
    }
    
}
