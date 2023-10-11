package com.tallerwebi.infraestructura;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.model.MobileUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Rollback
    @Test
    void shouldSaveMobileUser() {
        MobileUser user = new MobileUser();

        userRepository.save(user);

        MobileUser saved = userRepository.findUserById(user.getId());

        assertEquals(user, saved);
    }

    @Transactional
    @Rollback
    @Test
    void shouldFindUserByMailAndPassword(){
        MobileUser user = getUser();

        userRepository.save(user);
        MobileUser saved = userRepository.findUserByMailAndPassword(getEmail(), getPassword());

        assertEquals(user, saved);
    }

    @Transactional
    @Rollback
    @Test
    void shouldFindUserByMail() {
        MobileUser user = getUser();

        userRepository.save(user);
        MobileUser saved = userRepository.findUserByMail(getEmail());

        assertEquals(user, saved);
    }

    private MobileUser getUser(){
        String email = "carlitos@hotmail.com";
        String password = "123";

        MobileUser user = new MobileUser();

        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    private String getEmail(){
        return "carlitos@hotmail.com";
    }

    private String getPassword(){
        return "123";
    }
}