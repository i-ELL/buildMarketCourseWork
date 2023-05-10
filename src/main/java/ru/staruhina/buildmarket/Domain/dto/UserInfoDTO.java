package ru.staruhina.buildmarket.Domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.staruhina.buildmarket.Domain.model.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDTO {
    private boolean isAuth;
    private User user;
}