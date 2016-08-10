package com.amazing.config;

import com.amazing.entity.User;
import com.amazing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    getGrantedAuthority(user));
        }

        private List<GrantedAuthority> getGrantedAuthority(User user) {
            return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(new UserDetailsService())
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/api/**", "/auth", "/auth/**").permitAll();

        http.csrf().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, e) -> res.sendRedirect("/access-denied"))
                .accessDeniedPage("/access-denied")
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/access-denied"));
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler((req, res, authentication) -> {
                    res.setStatus(HttpServletResponse.SC_OK);
                    res.sendRedirect("/");
                    res.getWriter().flush();
                }).permitAll();

        http.formLogin().loginPage("/login").permitAll().failureUrl("/login")
                .loginProcessingUrl("/auth").usernameParameter("username").passwordParameter("password")
                .successHandler((req, res, authentication) -> res.sendRedirect("/chat"))
                .failureHandler((req, res, authentication) -> res.sendRedirect("/login"));

    }
}
