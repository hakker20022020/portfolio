package uz.inha.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.inha.portfolio.model.User;

import java.util.Optional;

@Repository
public interface UserRepository1 extends JpaRepository<User, Integer> {
    Optional<User> findByUserNameAndPassword(String userName, String password);
}
