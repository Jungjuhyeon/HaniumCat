package hanieum_project.hanium.src;

import hanieum_project.hanium.util.jwt.JwtAuthenticationFilter;
import hanieum_project.hanium.util.jwt.JwtTokenProvider;
import lombok.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        //http.httpBasic().disable(); // 일반적인 루트가 아닌 다른 방식으로 요청시 거절, header에 id, pw가 아닌 token(jwt)을 달고 간다. 그래서 basic이 아닌 bearer를 사용한다.
        http.httpBasic().disable()
                .authorizeRequests()// 요청에 대한 사용권한 체크

                //회원관련 api는 비밀번호 재설정 빼고는 토큰 필요 x
                .antMatchers("/uploader/users/password-restore").hasRole("USER")
                .antMatchers("/uploader/users/**").permitAll()
                //그외 웹으로 들어오는 요청 모두 권한 확인
                .antMatchers("/uploader/**").hasRole("USER")
                //말고 그냥 들어오는 모든 요청 거절
                .anyRequest().denyAll()
                .and()
                // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성합니다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다


    }
}
