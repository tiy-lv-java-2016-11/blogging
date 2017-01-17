package com.theironyard.Controllers;

import com.theironyard.CommandObjects.EntryCommand;
import com.theironyard.Entities.Entry;
import com.theironyard.Entities.User;
import com.theironyard.Repositories.CommentRepository;
import com.theironyard.Repositories.EntryRepository;
import com.theironyard.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import static com.theironyard.Controllers.AuthenticationController.CURRENT_USER;

/**
 * Created by darionmoore on 1/14/17.
 */
@Controller
public class EntryController {
    public static final Logger log = LoggerFactory.getLogger(EntryController.class);

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @RequestMapping(path = "/entry-list", method = RequestMethod.GET)
    public String  getEntryList(HttpSession session, Model model){
        model.addAttribute("entry-list", entryRepository.findAll());
        model.addAttribute("userName", session.getAttribute(CURRENT_USER));
        return "entry-list";
    }

    @RequestMapping(path = "entry-details/{id}", method = RequestMethod.GET)
    public String getDetails(Model model, @PathVariable int id){
        Entry entry = entryRepository.findOne(id);

        if(entry == null){
            log.debug("Entry Not Found: 404" + id);
            return "404";
        }
        model.addAttribute("entry", entry);
        model.addAttribute("comment-list", commentRepository.findAll());
        return "entry-details";
    }

    @RequestMapping(path = "/create-entry", method = RequestMethod.GET)
    public String createEntryPage(HttpSession session){
        if(session.getAttribute(CURRENT_USER) == null){
            return "redirect:/login";
        }
        return "create-entry";
    }

    @RequestMapping(path = "/create-entry", method = RequestMethod.POST)
    public String createEntry(HttpSession session, EntryCommand command){
        User user = userRepository.findFirstByUserName((String)session.getAttribute(CURRENT_USER));
        if(user == null){
            return "redirect:/login";
        }
        Entry entry = new Entry(command.getEntryTitle(), command.getEntry(), command.getEntryDescription(), user);
        entryRepository.save(entry);
        return "redirect:/entry-list";
    }


}
