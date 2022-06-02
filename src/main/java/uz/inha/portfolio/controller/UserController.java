package uz.inha.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.inha.portfolio.dto.UserDto;
import uz.inha.portfolio.model.User;
import uz.inha.portfolio.service.UserService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userlar")
    public String userList(Model model){
        List<User> userList = userService.getAll();
        model.addAttribute("user_list", userList);
        return "user_list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model){
        User byId = userService.getById(id);
        model.addAttribute("user", byId);
        return "edit_user_page";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, User user){
        userService.edit(id, user);
        return "redirect:/user/userlar";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delet(id);
        return "redirect:/user/userlar";
    }

    @GetMapping("/save")
    public String savePage(Model model){
        UserDto users = new UserDto();
        model.addAttribute("user", users);
        return "user_save_page";
    }

    @PostMapping("/save")
    public String save(UserDto userDto, @RequestParam("resume") MultipartFile resume,
                       @RequestParam("photo") MultipartFile photo) throws IOException {
        userService.save(userDto, resume, photo);
        return "user_page";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Integer id, User user){
        userService.getById(id);
        return "user/profile";
    }

}
