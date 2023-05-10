package ru.staruhina.buildmarket.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import ru.staruhina.buildmarket.Service.ProductService;
import ru.staruhina.buildmarket.Service.AuthService;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final AuthService authService;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        // Провряем, авторизован ли пользователь добавляя переменную
//        model.addAttribute("userInfo", authService.getUserInfo());
        // Провряем, авторизован ли пользователь добавляя переменную
        model.addAttribute("userInfo", authService.getUserInfo());


        // Если пользователь авторизован, то добавляем его в модель
        authService.getAuthUser().ifPresent(user -> model.addAttribute("user", user));
        return "index";
    }
}