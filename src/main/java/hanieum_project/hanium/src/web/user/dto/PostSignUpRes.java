package hanieum_project.hanium.src.web.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSignUpRes {
    private int userIdx;
    private String users_id;
    private String username;
    private String phone;
    private String email;
}
