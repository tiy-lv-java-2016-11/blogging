package com.theironyard.controllers;

import com.theironyard.commands.LoginCommand;
import com.theironyard.enitites.User;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthenticationController {
    private static final String SESSION_USER_ID = "userId";
//    public static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserRepository userRepo;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginCommand command, HttpSession session, RedirectAttributes redAtt) throws PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {
        String message = "";
        User user = userRepo.findFirstByUsername(command.getUsername());
        if (user == null){
            message = "Invalid Username/Password.";
        }
        else {
            if (!PasswordStorage.verifyPassword(command.getPassword(), user.getPassword())){
                message = "Invalid Username/Password.";
            }
            else {
                session.setAttribute(SESSION_USER_ID, user.getId());
            }
        }
        redAtt.addAttribute("message", message);
        return "redirect:/";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid @RequestBody LoginCommand command, HttpSession session, RedirectAttributes redAtt){
        String message = "";
        User user = userRepo.findFirstByUsername(command.getUsername());
        if (user != null){
            message = "That username is taken.";
        }
        else {
            user = new User(command.getUsername(), command.getPassword());
            session.setAttribute(SESSION_USER_ID, user.getId());
            message = "Registration successful.";
        }
        redAtt.addAttribute("message", message);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
