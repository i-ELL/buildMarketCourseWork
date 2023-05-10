package ru.staruhina.buildmarket.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.staruhina.buildmarket.Domain.model.Product;
import ru.staruhina.buildmarket.Domain.model.User;
import ru.staruhina.buildmarket.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Получение всех фильмов
     *
     * @return
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * Получение конкретного фильма
     *
     * @return
     */
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    /**
     * Получение конкретного фильма
     *
     * @return
     */
    public Product getById(int id) {
        return findById(id).orElse(null);
    }

    /**
     * Получение списка избранных фильмов
     *
     * @return
     */

    public static double getCartTotal(Set<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    /**
     * Получение всех продуктов
     *
     * @return
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Получение всех продуктов которые купил пользователь
     *
     * @param user
     * @return
     */
    public List<Product> getBoughtProductsByUser(User user) {
        return productRepository.findAllByOrders_User(user);
    }
}