package com.dtech.Ecommerce.auth.mapper;

import com.dtech.Ecommerce.auth.authModel.User;
import com.dtech.Ecommerce.auth.dto.UserDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}
