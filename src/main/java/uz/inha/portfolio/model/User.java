package uz.inha.portfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lastName;

    private String firstName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String password;

    private Boolean isAdmin = false;

    @OneToMany(mappedBy = "user")
    private List<Attachment> profilePhoto = new ArrayList<>();


    public void addAttachment(Attachment attachment) {
        profilePhoto.add(attachment);
    }
}
