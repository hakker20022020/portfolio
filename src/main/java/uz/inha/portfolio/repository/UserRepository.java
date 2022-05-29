package uz.inha.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.inha.portfolio.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
