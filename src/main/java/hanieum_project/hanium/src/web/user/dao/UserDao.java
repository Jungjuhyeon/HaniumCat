package hanieum_project.hanium.src.web.user.dao;

import hanieum_project.hanium.src.web.user.dto.*;
import hanieum_project.hanium.util.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;


@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void serDataSource(DataSource dataSource){
        this.jdbcTemplate =new JdbcTemplate(dataSource);
    }


    public UserAuthentication loadUserByUserIdx(Long userIdx){
        String query = " SELECT userIdx, users_id, role FROM User\n"+
                "WHERE userIdx = ?";

        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new UserAuthentication(
                        Long.valueOf(rs.getInt("userIdx")),
                        rs.getString("uid"),
                        Collections.singletonList(rs.getString("role"))
                ), userIdx);
    }

    /**
     * UserDao - 1
     * 23.11.07 작성자 : 정주현
     * 회원가입 INSERT 쿼리
     */
    @Transactional
    public int signUp(PostSignUpReq postSignUpReq){

        String query = "INSERT INTO User(username, users_id, password, email, phone)\n" +
                " VALUES (?,?,?,?,?); ";
        Object[] params = new Object[]{
                postSignUpReq.getUsername(),
                postSignUpReq.getUsers_id(),
                postSignUpReq.getPassword(),
                postSignUpReq.getEmail(),
                postSignUpReq.getPhone()
        };

        this.jdbcTemplate.update(query,params);
        return this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", int.class);

    }

    /**
     * UserDao - 2
     * 23.11.08 작성자 : 정주현
     * 회원가입 Select 쿼리
     */

    public PostSignUpRes selectUserId(int userIdx){
        String query = "SELECT * FROM User WHERE userIdx= ?";

        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new PostSignUpRes(
                        rs.getInt("userIdx"),
                        rs.getString("users_id"),
                        rs.getString("username"),
                        rs.getString("phone"),
                        rs.getString("email")
                ),userIdx);
    }

    public User login(PostLoginReq postLoginReq) {
        String query = "SELECT * FROM User WHERE users_id = ?";

        return this.jdbcTemplate.queryForObject(query,
                (rs,rownum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("username"),
                        rs.getString("users_id"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone")
                        ),postLoginReq.getUsers_id());
    }


}
