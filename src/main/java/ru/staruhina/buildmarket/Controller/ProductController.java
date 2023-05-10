package ru.staruhina.buildmarket.Controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.staruhina.buildmarket.Service.AuthService;
import ru.staruhina.buildmarket.Service.ProductService;
import ru.staruhina.buildmarket.Service.UserService;


@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final AuthService authService;
    private final UserService userService;

    /**
     * Получение конкретного фильма
     * @return
     */
    @GetMapping("/{id}")
    public String getProduct(
            @PathVariable("id") int id,
            Model model
    ) {
        // Добавляем информацию о пользователе в модель
        model.addAttribute("userInfo", authService.getUserInfo());

        // Проверка на существование продукта
        var product = productService.getById(id);
        if (product == null) {
            return "redirect:/error";
        }
        model.addAttribute("product", product);
        model.addAttribute("isBought", userService.isBought(product));
        return "product";
    }
}