package Me.ChallengeJava.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/logout", "/api/users").permitAll()

                // USER TEACHER
                .antMatchers(HttpMethod.GET,
                        "/api/users/current",
                        "/api/inscriptions/current").hasAnyAuthority("USER", "TEACHER", "ADMIN")

                .antMatchers(HttpMethod.POST, "/api/inscriptions").hasAnyAuthority("USER", "TEACHER")

                .antMatchers(HttpMethod.DELETE, "/api/inscriptions/{id]").hasAnyAuthority("USER", "TEACHER", "ADMIN")

                // ADMIN
                .antMatchers(HttpMethod.GET,
                        "/api/users",
                        "/api/courses",
                        "/api/inscriptions",
                        "/api/users/{id}",
                        "/h2-console/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST,
                        "/api/courses",
                        "/api/inscriptions",
                        "/api/users").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.PUT,
                        "/api/users/{id}",
                        "/api/courses/{id}",
                        "/api/inscriptions/{id}").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.DELETE,
                        "/api/users/{id}",
                        "/api/courses/{id}").hasAuthority("ADMIN")

                .anyRequest().denyAll();

        httpSecurity.formLogin()
                .usernameParameter("Email")
                .passwordParameter("Password")
                .loginPage("/api/login");

        httpSecurity.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        httpSecurity.csrf().disable();
        //disabling frameOptions so h2-console can be accessed
        httpSecurity.headers().frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response
        httpSecurity.exceptionHandling().authenticationEntryPoint(((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)));
        // if login is successful, just clear the flags asking for authentication
        httpSecurity.formLogin().successHandler(((request, response, authentication) -> clearAuthenticationAttributes(request)));
        // if login fails, just send an authentication failure response
        httpSecurity.formLogin().failureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if logout is successful, just send a success response
        httpSecurity.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return httpSecurity.build();
        }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
