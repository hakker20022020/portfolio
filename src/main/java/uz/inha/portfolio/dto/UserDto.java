package uz.inha.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String email;
    private String lastName;
    private String firstName;
    private String phone;
    private String password;
    private Boolean isAdmin=false;
}
