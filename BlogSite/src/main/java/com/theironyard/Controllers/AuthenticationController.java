package com.theironyard.Controllers;

import com.theironyard.CommandObjects.UserCommand;
import com.theironyard.Entities.User;
import com.theironyard.Repositories.UserRepository;
import com.theironyard.Utilities.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by darionmoore on 1/14/17.
 */
@Controller
public class AuthenticationController {
    public static final String CURRENT_USER = "userName";

    @Autowired
    UserRepository userRepository;


    /*
    Gets the login page
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginPage(HttpSession session){
        if(session.getAttribute(CURRENT_USER) == null){
            return "/login";
        }

        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String userLogin(HttpSession session, UserCommand command) throws PasswordHash.CannotPerformOperationException, PasswordHash.InvalidHashException {
        User loggedUser = userRepository.findFirstByUserName(command.getUserName());

        if(loggedUser == null){
            loggedUser = new User(command.getUserName(), PasswordHash.createHash(command.getPassword()));
            userRepository.save(loggedUser);
        } else if(!PasswordHash.verifyPassword(command.getPassword(), loggedUser.getPassword())){
            return "redirect:/login";
        }

        session.setAttribute(CURRENT_USER, loggedUser.getUserName());
        return "redirect:/entry-list";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }




}
