package hanieum_project.hanium.src.web.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginRes {
    private String jwt;
    private int userIdx;
    private String username;
    private String users_id;
    private String email;

}
