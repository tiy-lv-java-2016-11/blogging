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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class EntryController {
    public static final String SESSION_USER_ID = "userId";

    @Autowired
    UserRepository userRepo;

    @Autowired
    EntryRepository entryRepo;

    @Autowired
    CommentRepository commentRepo;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getAllEntries(Model model){
        List<Entry> entries = entryRepo.findAll();
        model.addAttribute("entries", entries);
        return "index";
    }

    @RequestMapping(path = "/entry", method = RequestMethod.GET)
    public String getOneEntry(Model model, int id){
        Entry entry = entryRepo.findOne(id);
        List<Comment> comments = commentRepo.findByEntry(entry);
        model.addAttribute("entry", entry);
        model.addAttribute("comments", comments);
        return "entry";
    }

    @RequestMapping(path = "/create-entry", method = RequestMethod.POST)
    public String createEntry(EntryCommand command, HttpSession session){
        User user = userRepo.findOne((int)session.getAttribute(SESSION_USER_ID));
        if (user != null){
            Entry entry = new Entry(command.getTitle(), command.getSnippet(), command.getPost(), user);
            entryRepo.save(entry);
        }
        return "redirect:/entry";
    }

    @RequestMapping(path = "/create-comment", method = RequestMethod.POST)
    public String createComment(CommentCommand command, int entryId, HttpSession session){
        User user = userRepo.findOne((int)session.getAttribute(SESSION_USER_ID));
        Entry entry = entryRepo.findOne(entryId);
        if (user != null){
            Comment comment = new Comment(command.getContent(), entry, user);
            commentRepo.save(comment);
        }
        return "redirect:/entry";
    }

}
