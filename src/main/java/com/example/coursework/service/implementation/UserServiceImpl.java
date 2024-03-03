package com.example.coursework.service.implementation;

import com.example.coursework.dto.response.GetUserResponseDto;
import com.example.coursework.entity.User;
import com.example.coursework.mappers.UserMapper;
import com.example.coursework.service.UserService;
import com.example.coursework.utils.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public GetUserResponseDto getUser() {
        var user = UserUtils.getCurrentUser();

        if(user != null){

          return UserMapper.INSTANCE.userToUserResponseDto((User)user);
        }

        throw new SecurityException("Server error cannot get current user");
    }
}
