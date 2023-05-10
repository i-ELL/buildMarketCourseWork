package ru.staruhina.buildmarket.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.staruhina.buildmarket.Domain.model.Product;
import ru.staruhina.buildmarket.Service.AdminService;


@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;


    /**
     * Страница пользователей
     *
     * @param model
     * @return
     */
    @GetMapping("/users")
    public String users(
            Model model
    ) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/users";
    }

    /**
     * Страница со списом товаров
     *
     * @param model
     */
    @GetMapping("/products")
    public String products(
            Model model
    ) {
        model.addAttribute("products", adminService.getAllProducts());
        return "admin/products";
    }

    /**
     * Редактирование товара
     *
     * @param id
     */
    @GetMapping("/products/edit/{id}")
    public String editProduct(
            @PathVariable("id") int id,
            Model model
    ) {
        model.addAttribute("product", adminService.getProductById(id));
        return "admin/edit-product";
    }

    /**
     * Редактирование товара
     */
    @PostMapping("/products/edit")
    public String editProductPost(
            @ModelAttribute("film") Product product
    ) {
        adminService.saveProduct(product);
        return "redirect:/admin/products?success";
    }

    /**
     * Добавление товара
     *
     * @return
     */
    @GetMapping("/products/add")
    public String addProduct(
            Model model
    ) {
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    /**
     * Добавление товара
     *
     * @return
     */
    @PostMapping("/products/add")
    public String addProductPost(
            @ModelAttribute("product") Product product
    ) {
        adminService.saveProduct(product);
        return "redirect:/admin/products?success";
    }

    @GetMapping("/orders")
    public String orders(
            Model model
    ) {
        model.addAttribute("orders", adminService.getAllOrders());
        return "admin/orders";
    }
}
