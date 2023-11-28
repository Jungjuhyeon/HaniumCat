package hanieum_project.hanium.src.web.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSignUpReq {
    private String username;
    private String phone;
    private String users_id;
    private String password;
    private String email;

}

