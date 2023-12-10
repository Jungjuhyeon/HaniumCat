package hanieum_project.hanium.src.web.user.service;

import hanieum_project.hanium.config.BaseException;
import hanieum_project.hanium.src.web.user.dao.UserDao;
import hanieum_project.hanium.src.web.user.dto.*;
import hanieum_project.hanium.util.EncryptHelper;
import hanieum_project.hanium.util.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hanieum_project.hanium.config.BaseResponseStatus.*;


@Service
@Slf4j
public class UserService {

    private final UserDao userDao;
    private final JwtService jwtService;
    private final EncryptHelper encryptHelper;

    @Autowired
    public UserService(UserDao userDao, JwtService jwtService, EncryptHelper encryptHelper) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.encryptHelper = encryptHelper;
    }

    public UserDetails loadUserByUserIdx(Long userId){
        return userDao.loadUserByUserIdx(userId);
    }

    @Transactional(rollbackFor = BaseException.class)
    public PostSignUpRes signUp(PostSignUpReq postSignUpReq) throws BaseException {

        //비밀번호 Bcrypt암호화
        try{
            String encryptedPW= encryptHelper.encrypt(postSignUpReq.getPassword());
            postSignUpReq.setPassword(encryptedPW);
        } catch (Exception ignored){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try {
            int userIdx = userDao.signUp(postSignUpReq);
            PostSignUpRes postSignUpRes = userDao.selectUserId(userIdx);
            return postSignUpRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = BaseException.class)
    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException {

        //user객체
        User user;
        // 1) 아이디가 존재하는지 확인, 회원정보 우선 조회
        try {
            user = userDao.login(postLoginReq);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_LOGIN);
        }

        String jwt = jwtService.createJwt(user.getUserIdx());

        return new PostLoginRes(
                jwt,
                user.getUserIdx(),
                user.getUsername(),
                user.getUsers_id(),
                user.getEmail()
        );

    }
}

