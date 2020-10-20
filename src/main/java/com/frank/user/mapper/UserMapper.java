package com.frank.user.mapper;

import com.frank.user.jpa.UserDab;
import com.frank.user.service.dto.Address;
import com.frank.user.service.dto.User;
import com.frank.user.jpa.AddressDab;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDab map(User user);
    User map(UserDab user);
    AddressDab map(Address address);
    Address map(AddressDab address);
}
