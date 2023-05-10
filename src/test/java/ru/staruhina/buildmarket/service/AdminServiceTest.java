package ru.staruhina.buildmarket.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.staruhina.buildmarket.Domain.model.Order;
import ru.staruhina.buildmarket.Domain.model.User;
import ru.staruhina.buildmarket.Mapper.UserMapper;
import ru.staruhina.buildmarket.Service.AdminService;
import ru.staruhina.buildmarket.Service.OrderService;
import ru.staruhina.buildmarket.Service.ProductService;
import ru.staruhina.buildmarket.Service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AdminService adminService;


    @Test
    public void getAllUsers() {
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = adminService.getAllUsers();

        assertEquals(users, result);
        verify(userService).getAllUsers();
    }

    @Test
    public void getUserById() {
        User user = new User();
        when(userService.getById(1)).thenReturn(user);

        User result = adminService.getUserById(1);

        assertEquals(user, result);
        verify(userService).getById(1);
    }


    @Test
    public void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        when(orderService.getAll()).thenReturn(orders);

        List<Order> result = adminService.getAllOrders();

        assertEquals(orders, result);
        verify(orderService).getAll();
    }
}