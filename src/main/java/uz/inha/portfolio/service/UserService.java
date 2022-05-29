package uz.inha.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.inha.portfolio.model.User;
import uz.inha.portfolio.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User editUser(Integer id, User user){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User editUser = new User();
            editUser.setFullName(user.getFullName());
            editUser.setUserName(user.getUserName());
            editUser.setPassword(user.getPassword());
            //editUser.setAttachment(user.getAttachment());
            return userRepository.save(editUser);
        }
        return null;
    }

    public boolean deleteUser(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent())
            userRepository.deleteById(id);
        return false;
    }

}
