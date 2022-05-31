package uz.inha.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.inha.portfolio.dto.UserDto;
import uz.inha.portfolio.model.Users;
import uz.inha.portfolio.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Users hasPhone(String phone){
        Optional<Users> byPhone = userRepository.findByPhone(phone);
        if (byPhone.isPresent()){
            return byPhone.get();
        }

        return null;
    }

    public boolean hasUser(UserDto userDto) {
        Optional<Users> byPhoneAndPassword = userRepository.findByPhoneAndPassword(userDto.getPhone(), userDto.getPassword());
        if(byPhoneAndPassword.isPresent())
            return true;
        else
            return false;

    }

    public List<Users> getAll() {
    return userRepository.findAll();
    }

    public Users getById(Integer id) {
        return userRepository.getById(id);
    }

    public void edit(Integer id, Users users) {
        Optional<Users> byId = userRepository.findById(id);
        Users users1 = byId.get();
        users1.setEmail(users.getEmail());
        users1.setPassword(users.getPassword());
        users1.setPhone(users.getPhone());
        users1.setIsAdmin(users.getIsAdmin());
        users1.setLastName(users.getLastName());
        users1.setFirstName(users.getFirstName());
        userRepository.save(users1);
    }

    public void delet(Integer id) {
        userRepository.deleteById(id);
    }

    public void save(UserDto userDto) {
        Users users = new Users();
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setPhone(userDto.getPhone());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        userRepository.save(users);
        /*if (!users.getPhone().equals(userDto.getPhone())){
            userRepository.save(users);
        }
        return;*/

    }
}
