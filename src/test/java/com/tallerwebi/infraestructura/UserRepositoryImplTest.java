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
        String email = "carlitos@hotmail.com";
        String password = "123";

        MobileUser user = new MobileUser();

        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);

        MobileUser saved = userRepository.findUserByMailAndPassword(email, password);

        assertEquals(user, saved);
    }

    @Transactional
    @Rollback
    @Test
    void shouldFindUserByMail() {
        String email = "carlitos@hotmail.com";

        MobileUser user = new MobileUser();

        user.setEmail(email);

        userRepository.save(user);

        MobileUser saved = userRepository.findUserByMail(email);

        assertEquals(user, saved);
    }

}