package com.theironyard;

import com.theironyard.enitites.Comment;
import com.theironyard.enitites.Entry;
import com.theironyard.enitites.User;
import com.theironyard.repositories.CommentRepository;
import com.theironyard.repositories.EntryRepository;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntryControllerTests {
    User user;
    Entry entry1;
    Entry entry2;
    Comment comment1;
    Comment comment2;

    @Autowired
    WebApplicationContext wap;

    @Autowired
    UserRepository userRepo;

    @Autowired
    EntryRepository entryRepo;

    @Autowired
    CommentRepository commentRepo;

    MockMvc mockMvc;

    @Before
    public void before() throws PasswordStorage.CannotPerformOperationException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
        String username = "testUsername";
        String password = "testPassword";
        user = new User(username, PasswordStorage.createHash(password));
        userRepo.save(user);

        String title = "testTitle";
        String snippet = "testSnippet";
        String post = "testContent";
        entry1 = new Entry(title, snippet, post, user);
        entryRepo.save(entry1);
        entry2 = new Entry(title+"2", snippet+"2", post+"2", user);
        entryRepo.save(entry2);

        String content = "testContent";
        comment1 = new Comment(content, entry1, user);
        commentRepo.save(comment1);
        comment2 = new Comment(content+"2", entry1, user);
        commentRepo.save(comment2);
    }

    @After
    public void after(){
        commentRepo.delete(comment1);
        commentRepo.delete(comment2);
        entryRepo.delete(entry1);
        entryRepo.delete(entry2);
        userRepo.delete(user);
    }

    @Test
    public void testGetAllEntries() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
                    .sessionAttr("currentUsername", user.getUsername())
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(model().attribute("entries", hasSize(2))
        ).andExpect(view().name("index"));
    }

    @Test
    public void testGetOneEntry() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/entry/"+entry1.getId())
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(model().attribute("entry", hasProperty("title", is(entry1.getTitle())))
        ).andExpect(model().attribute("comments", hasSize(2))
        ).andExpect(view().name("entry"));
    }

    @Test
    public void testCreateEntry() throws Exception {
        String title = entry1.getTitle()+"3";
        String snippet = entry1.getSnippet()+"3";
        String post = entry1.getPost()+"3";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/create-entry")
                    .param("title", title)
                    .param("snippet", snippet)
                    .param("post", post)
                    .sessionAttr("currentUsername", user.getUsername())
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Entry savedEntry = entryRepo.findByTitle(title);
        assertNotNull("Entry object not saved correctly", savedEntry);
        assertEquals("Entry Title not saved correctly", title, savedEntry.getTitle());
        assertEquals("Entry Snippet not saved correctly", snippet, savedEntry.getSnippet());
        assertEquals("Entry Post not saved correctly", post, savedEntry.getPost());
        assertEquals("User object not saved to Entry object correctly", user.getId(), savedEntry.getUser().getId());

        entryRepo.delete(savedEntry);
    }

    @Test
    public void testCreateComment() throws Exception {
        String content = comment1.getContent()+"3";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/create-comment")
                    .param("content", content)
                    .param("entryId", String.valueOf(entry1.getId()))
                    .sessionAttr("currentUsername", user.getUsername())
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Comment savedComment = commentRepo.findByContent(content);
        assertNotNull("Comment object not saved correctly", savedComment);
        assertEquals("Comment content attribute not saved correctly", content, savedComment.getContent());
        assertEquals("Entry object not saved to Comment object correctly", entry1.getId(), savedComment.getEntry().getId());
        assertEquals("User object not saved to Comment object correctly", user.getId(), savedComment.getUser().getId());

        commentRepo.delete(savedComment);
    }
}
