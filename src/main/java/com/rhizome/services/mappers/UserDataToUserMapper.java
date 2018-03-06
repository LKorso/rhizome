package com.rhizome.services.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.rhizome.domain.User;
import com.rhizome.services.api.dto.UserData;

@Mapper(componentModel = "spring")
public interface UserDataToUserMapper {

    User toUser(UserData userdata);

    UserData toUserData(User user);

    List<UserData> toUsersData(List<User> users);

}
