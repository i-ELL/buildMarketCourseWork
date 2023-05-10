package ru.staruhina.buildmarket.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.staruhina.buildmarket.Domain.dto.UserEditDTO;
import ru.staruhina.buildmarket.Service.AuthService;
import ru.staruhina.buildmarket.Service.ProductService;
import ru.staruhina.buildmarket.Service.UserService;


@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ProductService productService;
    private final AuthService authService;
    private final UserService userService;

    /**
     * Добавление товара в корзину по id товара
     *
     * @param model
     * @param id
     * @return
     */
    @PostMapping("/add-to-cart/{id}")
    public String addToCart(Model model, @PathVariable int id) {
        model.addAttribute("userInfo", authService.getUserInfo());
        userService.addToCartByProductId(id);
        return "redirect:/";
    }

    /**
     * Страница профиля пользователя
     *
     * @return
     */
    @GetMapping("/profile")
    public String profile(Model model) {

        // Добавляем информацию о пользователе в модель
        model.addAttribute("userInfo", authService.getUserInfo());

        var user = authService.getUserInfo().getUser();

        var cart = user.getProducts();
        model.addAttribute("cart", cart);
        model.addAttribute("cartTotal", ProductService.getCartTotal(cart));

        model.addAttribute("boughtProducts", productService.getBoughtProductsByUser(user));

        return "profile";
    }


    /**
     * Удаление из корзины
     *
     * @return
     */
    @PostMapping("/remove-from-cart/{id}")
    public String removeFromCart(Model model, @PathVariable int id) {
        model.addAttribute("userInfo", authService.getUserInfo());

        if (userService.removeFromCartByProductId(id)) {
            return "redirect:/user/profile?success" + id;
        } else {
            return "redirect:/user/profile?error" + id;
        }
    }

    @GetMapping("/edit")
    public String edit(
            @ModelAttribute("userDTO") UserEditDTO userEditDTO,
            Model model
    ) {

        // Добавляем информацию о пользователе в модель
        model.addAttribute("userInfo", authService.getUserInfo());

        userEditDTO = userService.getUserEditDTO();
        model.addAttribute("userDTO", userEditDTO);

        return "edit-profile";
    }

    /**
     * Изменение данных пользователя
     *
     * @return
     */
    @PostMapping("/edit")
    public String editPost(
            @Valid @ModelAttribute("userDTO") UserEditDTO userEditDTO,
            BindingResult result,
            Model model
    ) {
        model.addAttribute("userInfo", authService.getUserInfo());

        if (result.hasErrors()) {
            return "edit-profile";
        }

        if (userService.update(userEditDTO)) {
            return "redirect:/user/profile";
        }
        return "edit-profile";
    }

    @PostMapping("/checkout")
    public String checkout() {
        if (userService.checkout()) {
            return "redirect:/user/profile?success";
        } else {
            return "redirect:/user/profile?error";
        }
    }
}