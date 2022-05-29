package uz.inha.portfolio.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.inha.portfolio.model.User;
import uz.inha.portfolio.repository.AttachmentContentRepository;
import uz.inha.portfolio.repository.AttachmentRepository;
import uz.inha.portfolio.repository.UserRepository;
import uz.inha.portfolio.service.UserService;


@RestController
@RequiredArgsConstructor
public class UserController {

    private AttachmentRepository attachmentRepository;

    private AttachmentContentRepository attachmentContentRepository;

    private UserRepository userRepository;

    private UserService userService;

    @GetMapping
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.status(user != null ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(user);
    }

    //
    @PostMapping("/register")
    public HttpEntity<?> addUser(@RequestBody User user, @RequestParam(name = "userName") String userName,
                                 @RequestParam(name = "password") String password) {
        User addUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(addUser);

    }

    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Integer id, @RequestBody User user) {
        User editUser = userService.editUser(id, user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(editUser);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Integer id){
        boolean deleteUser = userService.deleteUser(id);
        return ResponseEntity.status(id).body(deleteUser);
    }

}
