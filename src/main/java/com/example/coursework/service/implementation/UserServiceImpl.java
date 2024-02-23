package com.example.coursework.service.implementation;

import com.example.coursework.dto.response.GetUserResponseDto;
import com.example.coursework.entity.User;
import com.example.coursework.mappers.UserMapper;
import com.example.coursework.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public GetUserResponseDto getUser() {
        var user = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if(user instanceof User){

          return UserMapper.INSTANCE.getUserResponseDto((User)user);
        }

        throw new SecurityException("Server error cannot get current user");
    }
}
