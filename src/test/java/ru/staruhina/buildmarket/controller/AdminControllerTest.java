package ru.staruhina.buildmarket.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.staruhina.buildmarket.Controller.AdminController;
import ru.staruhina.buildmarket.Service.AdminService;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(adminService)).build();
    }

    @Test
    public void usersTest() throws Exception {
        mockMvc.perform(get("/admin/users")).andExpect(status().isOk()).andExpect(view().name("admin/users"));
    }


    @Test
    public void ordersTest() throws Exception {
        mockMvc.perform(get("/admin/orders")).andExpect(status().isOk()).andExpect(view().name("admin/orders"));
    }

    @Test
    public void productsTest() throws Exception {
        mockMvc.perform(get("/admin/products")).andExpect(status().isOk()).andExpect(view().name("admin/products"));
    }
}