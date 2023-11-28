package hanieum_project.hanium.src.web.user;

import hanieum_project.hanium.config.BaseException;
import hanieum_project.hanium.config.BaseResponse;
import hanieum_project.hanium.src.web.user.dto.PostSignUpReq;
import hanieum_project.hanium.src.web.user.dto.PostSignUpRes;
import hanieum_project.hanium.src.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/uploader/users")
public class UserController {


    private final UserService userService;


    /**
     * 23.06.29 작성자 : 김성인
     * 회원가입 요청 후, 판매자 회원가입 정보 반환
     * Post /uploader/users
     * @param @RequestBody postSigunUpReq
     * @return BaseResponse<PostSignUpRes>
     */
    @ResponseBody
    @PostMapping("register")
    public BaseResponse<PostSignUpRes> signUp(@RequestBody PostSignUpReq postSigunUpReq){
        try{
            PostSignUpRes postSignUpRes = userService.signUp(postSigunUpReq);
            return new BaseResponse<>(postSignUpRes);
        }catch (BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

}
