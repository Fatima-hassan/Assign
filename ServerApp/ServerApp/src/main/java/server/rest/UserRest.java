package server.rest;

import server.model.User;
import server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRest {
    
    @Autowired
    UserService userService;

    @RequestMapping(value="/user/login/" , method=RequestMethod.POST , headers="Accept=application/json")
    public @ResponseBody User verifyLogin(@RequestBody User user){ 
        
        User validuser = userService.findUser(user);
        
        return validuser;
    }
    
    @RequestMapping(value="/user/update/{id}" , method=RequestMethod.PUT)
    public @ResponseBody User update(@PathVariable("id") int id, @RequestBody User user ){
        user.setId(id);
        userService.saveOrUpdate(user);
        
        return user;
    }
  
}
