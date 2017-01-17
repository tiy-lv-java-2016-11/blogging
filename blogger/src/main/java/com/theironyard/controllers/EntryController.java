package com.theironyard.controllers;

import com.theironyard.command.CommentCommand;
import com.theironyard.command.EntryCommand;
import com.theironyard.entities.Comment;
import com.theironyard.entities.Entry;
import com.theironyard.entities.User;
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
import javax.validation.Valid;
import java.util.List;

/**
 * Created by sparatan117 on 1/12/17.
 */
@Controller
public class EntryController {

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getEntries(HttpSession session, Model model) {

        if (session.getAttribute(UserController.SESSION_USERNAME) == null) {
            return "redirect:/login";
        }
        model.addAttribute("entries", entryRepository.findAll());
        model.addAttribute("comments", commentRepository.findAll());
        model.addAttribute("username", session.getAttribute(UserController.SESSION_USERNAME));
        return "entries";
    }


    @RequestMapping(path = "/create-entry", method = RequestMethod.POST)
    public String createEntry(HttpSession session, @Valid EntryCommand command) {
        User user = userRepository.findFirstByUsername((String) session.getAttribute(UserController.SESSION_USERNAME));
        if (user == null) {
            return "redirect:/login";
        }
        Entry entry = new Entry(command.getTitle(), command.getShortDescription(), command.getPost(), user);
        entryRepository.save(entry);

        return "redirect:/";
    }

    @RequestMapping(path = "/create-entry", method = RequestMethod.GET)
    public String getCreateEntry(HttpSession session) {
        if (session.getAttribute(UserController.SESSION_USERNAME) == null) {
            return "redirect:/login";
        }
        return "create-entry";
    }

    @RequestMapping(path = "/entry-detail/{entryId}", method = RequestMethod.GET)
    public String getEntry(Model model, @PathVariable int entryId) {
        Entry entry = entryRepository.findOne(entryId);

        if (entry == null) {
            return "404";
        }
        model.addAttribute("entry", entry);
        model.addAttribute("comments", commentRepository.findByEntry(entry));
        return "entry-detail";
    }

    @RequestMapping(path = "/create-comment", method = RequestMethod.POST)
    public String createComments(@Valid CommentCommand command, int id) {
        Entry entry = entryRepository.findOne(id);
        Comment comment = new Comment(command.getComment(), entry);
        commentRepository.save(comment);

        return "redirect:/";
    }

    @RequestMapping(path = "/delete-entry", method = RequestMethod.POST)
    public String deleteEntry(int id) {
        Entry entry = entryRepository.findOne(id);
        List <Comment> comments  = commentRepository.findByEntry(entry);
        commentRepository.delete(comments);
        entryRepository.delete(entry);
        return "redirect:/";
    }

}
