package com.hellonature.hellonature_back.config.security;

import com.hellonature.hellonature_back.config.security.main.MemberAuthenticationProvider;
import com.hellonature.hellonature_back.config.security.main.MemberLoginFailureHandler;
import com.hellonature.hellonature_back.config.security.main.MemberLogoutHandler;
import com.hellonature.hellonature_back.config.security.main.MemberSuccessHandler;
import com.hellonature.hellonature_back.service.AdminDetailsService;
import com.hellonature.hellonature_back.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class MultipleSecurityConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    private final MemberDetailsService memberDetailsService;
    private final AdminDetailsService adminDetailsService;

    @Order(1)
    @Configuration
    public class MemberSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private MemberAuthenticationProvider provider;

        @Override protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/user/**")
                    .httpBasic().disable()
                    .csrf().disable()
                    .authorizeRequests()
//                    .antMatchers("/user/mypage*").hasRole("MEMBER")
                    .anyRequest().permitAll()
                    .and().formLogin()
                    .loginPage("/user/mypage_userLogin")
//                    .loginProcessingUrl("/user/login_proc")
                    .usernameParameter("userid")
                    .passwordParameter("userpw")
                    .failureUrl("/user/mypage_userLogin?error")
                    .successHandler(memberAuthenticationSuccessHandler())
                    .failureHandler(memberAuthenticationFailureHandler())
                    .permitAll()
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutUrl("/user/logout")
                    .logoutSuccessHandler(memberLogoutHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("remember-me", "JSESSIONID")
                    .and()
                    .rememberMe()
                    .key("helloNature")
                    .rememberMeParameter("remember-me")
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(memberDetailsService);
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring()
                    // /css/**, /images/**, /js/** 등 정적 리소스는 보안필터를 거치지 않게 한다.
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .antMatchers("/api/**")
                    .antMatchers("/user/css/**")
                    .antMatchers("/user/images/**")
                    .antMatchers("/user/js/**")
                    .antMatchers("/user/mypage/mypage_layout/**")
                    .antMatchers("/user/mypage_userRegist");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(memberAuthenticationProvider());
        }

        @Bean
        public MemberAuthenticationProvider memberAuthenticationProvider(){
            return new MemberAuthenticationProvider();
        }

        @Bean
        public AuthenticationSuccessHandler memberAuthenticationSuccessHandler() {
            return new MemberSuccessHandler();
        }

        @Bean
        public AuthenticationFailureHandler memberAuthenticationFailureHandler() {
            return new MemberLoginFailureHandler();
        }

        @Bean
        public MemberLogoutHandler memberLogoutHandler() {
            return new MemberLogoutHandler();
        }

//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }
//
//        @Bean
//        public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean());
//            return jwtAuthenticationFilter;
//        }
    }

//    @Order(2)
//    @Configuration
//    public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        @Override protected void configure(HttpSecurity http) throws Exception {
//            http
//                .antMatcher("/admin/**")
//                .httpBasic().disable().authenticationProvider(adminAuthenticationProvider())
//                .csrf().disable()
////                    .addFilter(jwtAuthorizationFilter())
//                    .anonymous()
//                .and().authorizeRequests()
//                .antMatchers("/admin/Login").permitAll()
//                .anyRequest().hasRole("ADMIN")
//                .and().formLogin()
//                .loginPage("/admin/Login")
//                .loginProcessingUrl("/admin/login_proc")
//                .usernameParameter("userid")
//                .passwordParameter("userpw")
//                .successHandler(adminAuthenticationSuccessHandler())
//                .failureUrl("/admin/Login")
//                .failureHandler(adminAuthenticationFailureHandler())
//                .permitAll()
//                .and().logout()
//                .logoutUrl("/admin/logout")
//                .logoutSuccessUrl("/admin/index")
//                .invalidateHttpSession(true)
//                .deleteCookies("remember-me", "JSESSIONID")
//                .and()
//                    .rememberMe()
//                    .key("helloNature")
//                    .rememberMeParameter("remember-me")
//                    .tokenRepository(persistentTokenRepository())
//                    .userDetailsService(adminDetailsService);
//        }
//
//        @Override
//        public void configure(WebSecurity web) {
//            web.ignoring()
//                    // /css/**, /images/**, /js/** 등 정적 리소스는 보안필터를 거치지 않게 한다.
//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                    .antMatchers("/api/**")
//                    .antMatchers("/admin/css/**")
//                    .antMatchers("/admin/images/**")
//                    .antMatchers("/admin/js/**");
//        }
//
//        @Bean
//        public AdminAuthenticationProvider adminAuthenticationProvider(){
//            return new AdminAuthenticationProvider();
//        }
//
//        @Bean
//        public AuthenticationSuccessHandler adminAuthenticationSuccessHandler() {
//            return new AdminSuccessHandler();
//        }
//
//        @Bean
//        public AuthenticationFailureHandler adminAuthenticationFailureHandler() {
//            return new AdminLoginFailureHandler();
//        }
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

}