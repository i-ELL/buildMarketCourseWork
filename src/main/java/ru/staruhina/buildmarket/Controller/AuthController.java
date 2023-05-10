package ru.staruhina.buildmarket.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.staruhina.buildmarket.Domain.dto.UserRegisterDTO;
import ru.staruhina.buildmarket.Service.UserService;


@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") UserRegisterDTO userRegisterDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(
            @Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO,
            BindingResult result
    ) {
        var existing = userService.findByUserName(userRegisterDTO.getUsername());
        if (existing.isPresent()) {
            result.rejectValue("username", null, "Пользователь с таким username уже существует");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.register(userRegisterDTO);

        return "redirect:/login";
    }

}