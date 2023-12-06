package hanieum_project.hanium.util.jwt;

import hanieum_project.hanium.config.BaseException;
import hanieum_project.hanium.config.BaseResponseStatus;
import hanieum_project.hanium.config.JwtSecret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtService {


    //토큰 유효시간 30분 (나중에 유효시간 바꿀것)
    private long tokenValidTime = 30 *60 * 1000l;
    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(int userIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+ tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, JwtSecret.JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
    */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public int getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(JwtSecret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("userIdx",Integer.class);
    }




}
