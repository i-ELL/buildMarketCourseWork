package ru.staruhina.buildmarket.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.staruhina.buildmarket.Domain.model.Order;
import ru.staruhina.buildmarket.Domain.model.Product;
import ru.staruhina.buildmarket.Domain.model.User;
import ru.staruhina.buildmarket.Mapper.UserMapper;
import ru.staruhina.buildmarket.Repository.UserRepository;
import ru.staruhina.buildmarket.Service.AuthService;
import ru.staruhina.buildmarket.Service.OrderService;
import ru.staruhina.buildmarket.Service.ProductService;
import ru.staruhina.buildmarket.Service.UserService;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthService authService;

    @Mock
    private ProductService productService;


    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testEncodePassword() {
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = userService.encodePassword(password);

        assertEquals(encodedPassword, result);
    }

    @Test
    public void testSave() {
        User user = new User();

        userService.save(user);

        verify(userRepository).save(user);
    }

    @Test
    public void testFindByUserName() {
        String username = "test";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUserName(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    public void testFindById() {
        int id = 1;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }


    @Test
    public void testAddToCart() {
        Product product = new Product();
        product.setId(1);
        User user = new User();
        user.setId(1);
        user.setProducts(new HashSet<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(authService.getAuthUser()).thenReturn(Optional.of(user));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean result = userService.addToCart(product);

        assertTrue(result);
        assertEquals(1, user.getProducts().size());
        assertTrue(user.getProducts().contains(product));
    }

    @Test
    public void testRemoveFromCart() {
        Product product = new Product();
        product.setId(1);
        User user = new User();
        user.setId(1);
        user.setProducts(new HashSet<>(Arrays.asList(product)));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(authService.getAuthUser()).thenReturn(Optional.of(user));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean result = userService.removeFromCart(product);

        assertTrue(result);
        assertEquals(0, user.getProducts().size());
        assertFalse(user.getProducts().contains(product));
    }

    @Test
    public void testAddToBought_whenProductAlreadyBought() {
        User user = new User();
        Product product = new Product();
        Set<Product> boughtProducts = new HashSet<>();
        boughtProducts.add(product);
        user.setBoughtProducts(boughtProducts);

        boolean result = userService.addToBought(user, product);

        assertFalse(result);
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testAddToBought_whenProductNotBought() {
        User user = new User();
        Product product = new Product();
        Set<Product> boughtProducts = new HashSet<>();
        user.setBoughtProducts(boughtProducts);

        boolean result = userService.addToBought(user, product);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);
        assertTrue(user.getBoughtProducts().contains(product));
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(expectedUsers, result);
    }

    @Test
    public void testGetById() {
        User expectedUser = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(expectedUser));

        User result = userService.getById(1);

        assertEquals(expectedUser, result);
    }

    @Test
    public void testGetById_whenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User result = userService.getById(1);

        assertNull(result);
    }

    @Test
    public void testDeleteById() {
        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }

}
