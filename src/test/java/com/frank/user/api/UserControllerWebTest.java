package com.frank.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frank.user.jpa.UserDab;
import com.frank.user.mapper.UserMapper;
import com.frank.user.service.UserService;
import com.frank.user.service.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(UserController.class)
class UserControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @MockBean
    private UserMapper userMapper;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_withInvalidUser_returnsValidationErrors() throws Exception {
        User user = new User(); // Missing mandatory fields
        
    mockMvc.perform(post("/userdetails/")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Title is mandatory"))
            .andExpect(jsonPath("$.firstName").value("FirstName is mandatory"))
            .andExpect(jsonPath("$.lastName").value("LastName is mandatory"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUser_withInvalidId_returnsTypeMismatchError() throws Exception {
        mockMvc.perform(get("/userdetails/abc"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userId").value("userId's type must be Long"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUser_withNegativeId_returnsConstraintViolation() throws Exception {
        mockMvc.perform(get("/userdetails/-1"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("UserId must be great than 0")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_withInvalidBody_returnsValidationErrors() throws Exception {
        User user = new User();
        user.setFirstName(""); // Empty string will trigger @NotBlank
        
    mockMvc.perform(put("/userdetails/1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Title is mandatory"))
            .andExpect(jsonPath("$.firstName").value("FirstName is mandatory"))
            .andExpect(jsonPath("$.lastName").value("LastName is mandatory"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveUser_withValidData_returnsOk() throws Exception {
        User user = new User();
        user.setTitle("MR");
        user.setFirstName("John");
        user.setLastName("Doe");
        
        UserDab userDab = new UserDab();
        userDab.setFirstName("John");
        userDab.setLastName("Doe");
        userDab.setTitle("MR");
        
        when(userMapper.map(any(User.class))).thenReturn(userDab);
        when(userService.saveUser(any(UserDab.class))).thenReturn(userDab);
        when(userMapper.map(any(UserDab.class))).thenReturn(user);
        
    mockMvc.perform(post("/userdetails/")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
            .andDo(print())
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void saveUser_withDbError_throwsException() throws Exception {
        User user = new User();
        user.setTitle("MR");
        user.setFirstName("John");
        user.setLastName("Doe");
        
        UserDab userDab = new UserDab();
        when(userMapper.map(any(User.class))).thenReturn(userDab);
        when(userService.saveUser(any(UserDab.class))).thenThrow(new RuntimeException("DB Error"));
        
    mockMvc.perform(post("/userdetails/")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }
}