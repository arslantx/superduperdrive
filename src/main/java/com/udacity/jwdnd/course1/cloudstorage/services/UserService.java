package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.util.EncoderUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }
    
    public int createUser(User user) {
        String encodedSalt = EncoderUtil.getRandomEncodedStr();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setSalt(encodedSalt);
        user.setPassword(hashedPassword);
        return userMapper.insert(user);
    }
    
    public Integer getCurrentUserId(Authentication auth) {
        String username = auth.getName();
        User user = getUser(username);
        return user.getUserid();
    }
    
    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }
}
