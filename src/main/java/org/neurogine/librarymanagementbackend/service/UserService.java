package org.neurogine.librarymanagementbackend.service;


import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.User;
import org.neurogine.librarymanagementbackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


     //register / add user
    public User register(User user){
        return userRepository.save(user);
    }

    public boolean login(User user){
        return userRepository.existsByUserNameAndPassword(user.getUserName(), user.getPassword());
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public User findById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> findAll(){return userRepository.findAll();}

    public List<User> findByKeyword(String keyword){return userRepository.findByUserNameContaining(keyword);}

    // login check
    public boolean isPasswordRight(String userName, String oldPassword){
        return userRepository.existsByUserNameAndPassword(userName, oldPassword);
    }

    //Change Password
    public boolean changePassword(
            String username,
            String currentPassword,
            String newPassword
    ) {
        Optional<User> optionalUser =
                userRepository.findByUserName(username);

        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();

        if (!user.getPassword().equals(currentPassword)) {
            return false;
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }







}
