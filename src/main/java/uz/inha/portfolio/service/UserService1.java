package uz.inha.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import uz.inha.portfolio.model.User;
import uz.inha.portfolio.repository.UserRepository1;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService1 {

    private UserRepository1 userRepository;

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


    // Parot bovotgan joylari
    public User login(@RequestParam(name = "userName") String userName,
                      @RequestParam(name = "password") String password, Model model){
        Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);
        model.addAttribute("user", user);

        return null;
    }

}
