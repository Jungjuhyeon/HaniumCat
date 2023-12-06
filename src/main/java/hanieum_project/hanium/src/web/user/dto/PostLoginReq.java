package hanieum_project.hanium.src.web.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginReq {
    private String users_id;
    private String password;
}
