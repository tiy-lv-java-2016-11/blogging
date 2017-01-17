package com.theironyard.controllers;


import com.theironyard.Utillities.PasswordStorage;
import com.theironyard.command.LoginCommand;
import com.theironyard.entities.User;
import com.theironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by sparatan117 on 1/11/17.
 */
@Controller
public class UserController {

    public static final String SESSION_USERNAME = "username";

    @Autowired
    UserRepository userRepository;



    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLogin(HttpSession session){
        if(session.getAttribute(SESSION_USERNAME) != null){
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, LoginCommand command) throws PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {
        User savedUser = userRepository.findFirstByUsername(command.getUsername());

        if(savedUser == null){
            savedUser = new User(command.getUsername(), PasswordStorage.createHash(command.getPassword()));
            userRepository.save(savedUser);
        }
        else if(!PasswordStorage.verifyPassword(command.getPassword(), savedUser.getPassword())){
            return "redirect:/login";
        }

        session.setAttribute(SESSION_USERNAME, savedUser.getUsername());
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
