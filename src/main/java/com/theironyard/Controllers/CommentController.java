package com.theironyard.Controllers;

import com.theironyard.CommandObjects.CommentCommand;
import com.theironyard.Entities.Comment;
import com.theironyard.Repositories.CommentRepository;
import com.theironyard.Repositories.EntryRepository;
import com.theironyard.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.theironyard.Controllers.AuthenticationController.CURRENT_USER;

/**
 * Created by darionmoore on 1/14/17.
 */
@Controller
public class CommentController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    CommentRepository commentRepository;

    @RequestMapping(path = "/create-comment", method = RequestMethod.GET)
    public String createCommentPage(HttpSession session){
        if(session.getAttribute(CURRENT_USER) == null) {
            return "redirect:/login";
        }

        return "create-comment";
    }

    @RequestMapping(path = "/create-comment", method = RequestMethod.POST)
    public String createComment(HttpSession session, CommentCommand command){
        List comment = commentRepository.findAll();
        Comment newComment = new Comment(command.getComment());
        commentRepository.save(newComment);
        return "redirect:/entry-list";
    }




}
