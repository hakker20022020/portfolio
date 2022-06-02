package uz.inha.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.inha.portfolio.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Long countById(Integer id);

    Optional<User> findByPhone(String phoneNumber);

    Optional<User> findByPhoneAndPassword(String phone, String password);
}
