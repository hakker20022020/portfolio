package uz.inha.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.inha.portfolio.dto.UserDto;
import uz.inha.portfolio.model.User;
import uz.inha.portfolio.service.UserService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login_page";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute UserDto userDto) {
        User user = userService.hasPhone(userDto.getPhone());
        if (user == null || !user.getPassword().equals(userDto.getPassword())) {
            return "index";
        }
        if (user.getIsAdmin()) {
            return "admin_page";
        }
        return "user_page";
    }
}
