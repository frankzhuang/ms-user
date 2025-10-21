package com.frank.user.mapper;

import com.frank.user.jpa.AddressDab;
import com.frank.user.jpa.UserDab;
import com.frank.user.service.dto.Address;
import com.frank.user.service.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userId", ignore = true)
    UserDab map(User user);
    User map(UserDab user);
    AddressDab map(Address address);
    Address map(AddressDab address);
}
