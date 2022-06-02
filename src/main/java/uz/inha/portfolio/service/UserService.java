package uz.inha.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.inha.portfolio.dto.UserDto;
import uz.inha.portfolio.model.Attachment;
import uz.inha.portfolio.model.AttachmentContent;
import uz.inha.portfolio.model.User;
import uz.inha.portfolio.repository.AttachmentContentRepository;
import uz.inha.portfolio.repository.AttachmentRepository;
import uz.inha.portfolio.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AttachmentRepository attachmentRepository;

    private final AttachmentContentRepository attachmentContentRepository;

    public User hasPhone(String phone) {
        Optional<User> byPhone = userRepository.findByPhone(phone);
        if (byPhone.isPresent()) {
            return byPhone.get();
        }

        return null;
    }

    public boolean hasUser(UserDto userDto) {
        Optional<User> byPhoneAndPassword = userRepository.findByPhoneAndPassword(userDto.getPhone(), userDto.getPassword());
        if (byPhoneAndPassword.isPresent())
            return true;
        else
            return false;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    public void edit(Integer id, User user) {
        Optional<User> byId = userRepository.findById(id);
        User user1 = byId.get();
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setPhone(user.getPhone());
        user1.setIsAdmin(user.getIsAdmin());
        user1.setLastName(user.getLastName());
        user1.setFirstName(user.getFirstName());
        userRepository.save(user1);
    }

    public void delet(Integer id) {
        userRepository.deleteById(id);
    }

    public User save(UserDto userDto, MultipartFile resume, MultipartFile photo) throws IOException {

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);

        Attachment a_r = new Attachment();
        a_r.setContentType(resume.getContentType());
        a_r.setUser(user);
        a_r.setFileOriginalName(resume.getOriginalFilename());
        a_r.setSize(resume.getSize());
        attachmentRepository.save(a_r);
        user.addAttachment(a_r);
        userRepository.save(user);

        AttachmentContent ac_r = new AttachmentContent();
        ac_r.setAttachment(a_r);
        ac_r.setBytes(resume.getBytes());
        attachmentContentRepository.save(ac_r);
        Attachment attachment = new Attachment(
                photo.getOriginalFilename(),
                photo.getSize(),
                photo.getContentType()
        );
        attachment.setUser(user);
        attachmentRepository.save(attachment);
        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setAttachment(attachment);
        attachmentContent.setBytes(photo.getBytes());
        attachmentContentRepository.save(attachmentContent);
        user.addAttachment(attachment);
        return userRepository.save(user);

    }
}