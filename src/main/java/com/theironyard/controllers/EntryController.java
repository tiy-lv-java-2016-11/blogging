package com.theironyard.controllers;

import com.theironyard.commands.CommentCommand;
import com.theironyard.commands.EntryCommand;
import com.theironyard.enitites.Comment;
import com.theironyard.enitites.Entry;
import com.theironyard.enitites.User;
import com.theironyard.repositories.CommentRepository;
import com.theironyard.repositories.EntryRepository;
import com.theironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EntryController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    EntryRepository entryRepo;

    @Autowired
    CommentRepository commentRepo;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getAllEntries(Model model, HttpSession session){
        User user = userRepo.findFirstByUsername((String) session.getAttribute(UserController.SESSION_USERNAME));
        model.addAttribute("user", user);

        List<Entry> entries = entryRepo.findAll();
        model.addAttribute("entries", entries);

        return "index";
    }

    @RequestMapping(path = "/entry/{entryId}", method = RequestMethod.GET)
    public String getOneEntry(Model model, HttpSession session, @PathVariable int entryId){
        User user = userRepo.findFirstByUsername((String) session.getAttribute(UserController.SESSION_USERNAME));
        Entry entry = entryRepo.findOne(entryId);
        List<Comment> comments = commentRepo.findByEntry(entry);
        model.addAttribute("user", user);
        model.addAttribute("entry", entry);
        model.addAttribute("comments", comments);
        return "entry";
    }

    @RequestMapping(path = "/create-entry", method = RequestMethod.GET)
    public String getCreateEntryForm(Model model, HttpSession session){
        User user = userRepo.findFirstByUsername((String) session.getAttribute(UserController.SESSION_USERNAME));
        if (user == null){
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "create-entry";
    }

    @RequestMapping(path = "/create-entry", method = RequestMethod.POST)
    public String createEntry(EntryCommand command, HttpSession session){
        User user = userRepo.findFirstByUsername((String) session.getAttribute(UserController.SESSION_USERNAME));
        if (user != null){
            Entry entry = new Entry(command.getTitle(), command.getSnippet(), command.getPost(), user);
            entryRepo.save(entry);
            return "redirect:/entry/"+entry.getId();
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/create-comment", method = RequestMethod.POST)
    public String createComment(CommentCommand command, int entryId, HttpSession session){
        User user = userRepo.findFirstByUsername((String) session.getAttribute(UserController.SESSION_USERNAME));
        Entry entry = entryRepo.findOne(entryId);
        if (user != null){
            Comment comment = new Comment(command.getContent(), entry, user);
            commentRepo.save(comment);
        }
        return "redirect:/entry/"+entryId;
    }

}
