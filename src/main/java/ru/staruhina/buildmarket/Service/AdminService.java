package ru.staruhina.buildmarket.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.staruhina.buildmarket.Domain.model.Order;
import ru.staruhina.buildmarket.Domain.model.Product;
import ru.staruhina.buildmarket.Domain.model.User;
import ru.staruhina.buildmarket.Mapper.UserMapper;


import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserService userService;
    private final ProductService filmService;
    private final UserMapper userMapper;
    private final OrderService orderService;

    /**
     * Получение всех пользователей
     */
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Получение пользователя по id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userService.getById(id);
    }


    /**
     * Получение всех Фильмов
     *
     * @return
     */
    public List<Product> getAllProducts() {
        return filmService.getAllProducts();
    }


    /**
     * Получение фильма по id
     *
     * @param id
     * @return
     */
    public Product getProductById(int id) {
        return filmService.getById(id);
    }


    /**
     * Сохранение фильма
     *
     * @param product
     */
    @Transactional
    public void saveProduct(Product product) {
        filmService.save(product);
    }

    public List<Order> getAllOrders() {
        return orderService.getAll();
    }
}