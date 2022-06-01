package uz.inha.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.inha.portfolio.model.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    List<Attachment> findAllByUsersId(Integer id);
}
