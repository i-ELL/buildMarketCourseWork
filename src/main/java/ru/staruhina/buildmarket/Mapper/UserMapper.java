package ru.staruhina.buildmarket.Mapper;

import groovy.lang.Lazy;
import jdk.jfr.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.staruhina.buildmarket.Domain.dto.UserEditDTO;
import ru.staruhina.buildmarket.Domain.dto.UserRegisterDTO;
import ru.staruhina.buildmarket.Domain.model.User;


@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegisterDTO.getPassword()))")
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "image", constant = "https://yt3.ggpht.com/ytc/AMLnZu8acYs8ed0Ni_tByNwuIKb_Wl3UKb6Sx2TmFkh7=s900-c-k-c0x00ffffff-no-rj")
    public abstract User registerDTOToUser(UserRegisterDTO userRegisterDTO);

    public abstract UserEditDTO userToUserEditDTO(User user);
}