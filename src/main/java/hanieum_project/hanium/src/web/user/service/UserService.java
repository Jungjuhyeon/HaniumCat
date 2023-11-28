package hanieum_project.hanium.src.web.user.service;

import hanieum_project.hanium.config.BaseException;
import hanieum_project.hanium.src.web.user.dao.UserDao;
import hanieum_project.hanium.src.web.user.dto.PostSignUpReq;
import hanieum_project.hanium.src.web.user.dto.PostSignUpRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hanieum_project.hanium.config.BaseResponseStatus.*;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserDao userDao;

    public UserDetails loadUserByUserIdx(Long userId){
        return userDao.loadUserByUserIdx(userId);
    }

    @Transactional(rollbackFor = BaseException.class)
    public PostSignUpRes signUp(PostSignUpReq postSignUpReq) throws BaseException {
        try {
            int userIdx = userDao.signUp(postSignUpReq);

            PostSignUpRes postSignUpRes = userDao.selectUserId(userIdx);
            return postSignUpRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    }

