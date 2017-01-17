package com.theironyard;

import com.theironyard.commands.LoginCommand;
import com.theironyard.enitites.User;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTests {
    User user;
    String username;
    String password;

    @Autowired
    WebApplicationContext wap;

    @Autowired
    UserRepository userRepo;

    MockMvc mockMvc;

    @Before
    public void before() throws PasswordStorage.CannotPerformOperationException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
        username = "testUsername";
        password = "testPassword";
        user = new User(username, PasswordStorage.createHash(password));
        userRepo.save(user);
    }

    @After
    public void after(){
        userRepo.delete(user);
    }

    @Test
    public void testLoginValid() throws Exception {

    }

    @Test
    public void testLoginInvalidUsername() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .param("username", username+"2")
                        .param("password", password)
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection()
        ).andExpect(flash().attribute("message", "Invalid Username/Password.")
        ).andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testLoginInvalidPassword() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .param("username", username)
                        .param("password", password+"2")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection()
        ).andExpect(flash().attribute("message", "Invalid Username/Password.")
        ).andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testRegisterValid() throws Exception {
        username = user.getUsername()+"2";
        password = user.getPassword()+"2";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/register")
                        .param("username", username)
                        .param("password", password)
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection()
        ).andExpect(flash().attribute("message", "Registration successful.")
        ).andExpect(redirectedUrl("/"));
    }

    @Test
    public void testRegisterUsernameExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/register")
                        .param("username", username)
                        .param("password", password)
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection()
        ).andExpect(flash().attribute("message", "That username is taken.")
        ).andExpect(redirectedUrl("/register"));
    }
}
