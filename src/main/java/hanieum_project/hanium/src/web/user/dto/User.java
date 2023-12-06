package hanieum_project.hanium.src.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userIdx;
    private String username;
    private String users_id;
    private String password;
    private String email;
    private String phone;

}
