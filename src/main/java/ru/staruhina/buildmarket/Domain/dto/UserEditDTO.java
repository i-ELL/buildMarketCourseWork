package ru.staruhina.buildmarket.Domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEditDTO {

    @NotEmpty(message = "Поле имя не может быть пустым")
    private String username;

    @NotEmpty(message = "Поле image не может быть пустым")
    private String image;
}
