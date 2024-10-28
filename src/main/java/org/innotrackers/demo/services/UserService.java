package org.innotrackers.demo.services;

import org.innotrackers.demo.orm.repos.UserRepository;
import org.innotrackers.demo.orm.schemas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DataSource dataSource;

    @Autowired
    private final UserDetailsManager users;

    public UserService(UserRepository userRepository, DataSource dataSource, UserDetailsManager users) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
        this.users = users;
    }

    public void createUser(String username, String displayName, String email, String password, boolean admin) {
        User user1 = new User();
        user1.username = username;
        user1.displayName = displayName;
        user1.email = email;
        userRepository.save(user1);

        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(passwordEncoder.encode(password));
        if (admin) {
            userBuilder.roles("USER", "ADMIN");
        } else {
            userBuilder.roles("USER");
        }
        UserDetails user = userBuilder.build();

        users.createUser(user);
    }
}
