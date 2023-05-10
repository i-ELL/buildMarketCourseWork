package ru.staruhina.buildmarket.Domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(name = "users", attributeNodes = @NamedAttributeNode("users"))
})
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    /*
     * Описание фильма
     */
    @Column(name = "description")
    private String description;

    /*
     * Цена
     */

    @Column(name = "price")
    private double price;

    /*
     * Картинка товара
     */
    @Column(name = "image")
    private String image;

    /**
     * Список покупателей у которых товар в корзине
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    /**
     * Список пользователей, которые купили товар
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_bought_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> usersBought = new HashSet<>();


    /**
     * Список заказов, в которых есть товар
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_orders",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<Order> orders = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, image);
    }
}