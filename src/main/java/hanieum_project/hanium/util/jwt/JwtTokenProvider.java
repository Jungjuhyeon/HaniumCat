package hanieum_project.hanium.util.jwt;


import hanieum_project.hanium.config.JwtSecret;
import hanieum_project.hanium.src.web.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserService userService;

    //토큰 유효시간 30분 (나중에 유효시간 바꿀것)
    private long tokenValidTime = 30 *60 * 1000l;

    // JWT 토큰 생성
    public String createToken(String userIdx, String userType) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx) // jwt 토큰 내의 payload 정보 (key, value)
                .claim("userType", userType) // jwt 토큰 내의 payload 정보 (유저 타입)
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, JwtSecret.JWT_SECRET_KEY)  // 사용할 암호화 알고리즘,signature에 들어갈 secret값 세팅
                // signature 에 들어갈 secret값 세팅
                .compact();
    }


    // JWT 토큰에서 인증정보 조회
    public Authentication getAuthentication(String token){
        String userType = this.getUserType(token);
        UserDetails userDetails = null;

        if (userType.equals("USER")){
            userDetails = userService.loadUserByUserIdx(this.getUserId(token));
        }else{
            return null;
        }

        log.info("JwtTokenProvider : {}{}", userDetails.getUsername(), userDetails.getAuthorities().toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원정보 추출
    public Long getUserId(String token){
        //이는 정수형이므로 long으로 변환하여 반환
        return new Long(Jwts.parser().setSigningKey(JwtSecret.JWT_SECRET_KEY).parseClaimsJws(token).getBody().get("userIdx", Integer.class));
    }

    public String getUserType(String token){
        return Jwts.parser().setSigningKey(JwtSecret.JWT_SECRET_KEY).parseClaimsJws(token).getBody().get("userType", String.class);
    }

    // Request의 Header에서 token 값을 가져옴 "X-ACCESS-TOKEN" : "TOKEN 값"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-ACCESS-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JwtSecret.JWT_SECRET_KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
