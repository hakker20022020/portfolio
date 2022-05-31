package uz.inha.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.inha.portfolio.model.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Long countById(Integer id);

    Optional<Users> findByPhone(String phoneNumber);

    Optional<Users> findByPhoneAndPassword(String phone, String password);
}
