package com.theironyard.controllers;

import com.theironyard.commands.LoginCommand;
import com.theironyard.enitites.User;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    public static final String SESSION_USERNAME = "currentUsername";

    @Autowired
    UserRepository userRepo;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginForm(){
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(LoginCommand command, HttpSession session, RedirectAttributes redAtt) throws PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {
        String message = "";
        User user = userRepo.findFirstByUsername(command.getUsername());
        if (user == null || !PasswordStorage.verifyPassword(command.getPassword(), user.getPassword())){
            message = "Invalid Username/Password.";
        }
        else {
            session.setAttribute(SESSION_USERNAME, user.getUsername());
            return "redirect:/";
        }
        redAtt.addFlashAttribute("message", message);
        return "redirect:/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterForm(){
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(LoginCommand command, HttpSession session, RedirectAttributes redAtt) throws PasswordStorage.CannotPerformOperationException {
        String retVal = "redirect:/";
        String message = "";
        User user = userRepo.findFirstByUsername(command.getUsername());
        if (user != null){
            message = "That username is taken.";
            retVal += "register";
        }
        else {
            user = new User(command.getUsername(), PasswordStorage.createHash(command.getPassword()));
            userRepo.save(user);
            session.setAttribute(SESSION_USERNAME, user.getUsername());
            message = "Registration successful.";
        }
        redAtt.addFlashAttribute("message", message);
        return retVal;
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
