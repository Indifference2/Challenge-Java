package Me.ChallengeJava.configurations;

import Me.ChallengeJava.models.User;
import Me.ChallengeJava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputEmail -> {
            User user = userRepository.findByEmail(inputEmail);

            if(user != null){
                if(user.getEmail().contains("@admin.com")){
                    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }
                if(user.getEmail().contains("@edu.com")){
                    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                            AuthorityUtils.createAuthorityList("TEACHER"));
                }
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        AuthorityUtils.createAuthorityList("STUDENT"));
            }else{
                throw new UsernameNotFoundException("User unknown:" + inputEmail);
            }
        });
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
